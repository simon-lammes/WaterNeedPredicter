package com.example.waterneedpredicter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class HumanPersonRecyclerAdapter extends RecyclerView.Adapter<HumanPersonViewHolder> {

    private List<HumanPerson> humanPeople = Collections.emptyList();
    private final HumanPersonDao humanPersonDao;

    HumanPersonRecyclerAdapter(HumanPersonDao humanPersonDao) {
        this.humanPersonDao = humanPersonDao;
    }

    @NonNull
    @Override
    public HumanPersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_human_person, parent, false);
        return new HumanPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HumanPersonViewHolder holder, int position) {
        TextView nameTextView = holder.itemView.findViewById(R.id.human_person_name_text_view);
        TextView weightTextView = holder.itemView.findViewById(R.id.human_person_weight_view);
        TextView dayOfBirthTextView = holder.itemView.findViewById(R.id.human_person_day_of_birth_view);
        Button removeButton = holder.itemView.findViewById(R.id.human_person_remove_button);
        HumanPerson humanPerson = humanPeople.get(position);
        nameTextView.setText(humanPerson.getName());
        weightTextView.setText(String.valueOf(humanPerson.getWeightInGrams()));
        dayOfBirthTextView.setText(String.valueOf(humanPerson.calculateTimePassedSinceBirthdayInMonths()));
        removeButton.setOnClickListener(event ->
                new DeletePersonTask(humanPersonDao, this).execute(humanPerson));
    }

    @Override
    public int getItemCount() {
        return humanPeople.size();
    }

    void setHumanPeople(List<HumanPerson> humanPeople) {
        this.humanPeople = humanPeople;
        notifyDataSetChanged();
    }

    static class DeletePersonTask extends AsyncTask<HumanPerson, Void, List<HumanPerson>> {

        private final HumanPersonDao humanPersonDao;
        private HumanPersonRecyclerAdapter humanPersonRecyclerAdapter;

        DeletePersonTask(HumanPersonDao humanPersonDao, HumanPersonRecyclerAdapter humanPersonRecyclerAdapter) {
            this.humanPersonDao = humanPersonDao;
            this.humanPersonRecyclerAdapter = humanPersonRecyclerAdapter;
        }

        @Override
        protected List<HumanPerson> doInBackground(HumanPerson... humanPeople) {
            HumanPerson humanPersonToDelete = humanPeople[0];
            humanPersonDao.delete(humanPersonToDelete);
            return humanPersonDao.getAll();
        }

        @Override
        protected void onPostExecute(List<HumanPerson> humanPeople) {
            super.onPostExecute(humanPeople);
            humanPersonRecyclerAdapter.setHumanPeople(humanPeople);
        }
    }
}
