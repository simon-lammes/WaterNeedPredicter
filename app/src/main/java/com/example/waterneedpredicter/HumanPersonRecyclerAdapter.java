package com.example.waterneedpredicter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class HumanPersonRecyclerAdapter extends RecyclerView.Adapter<HumanPersonViewHolder> {

    private List<HumanPerson> humanPeople = Collections.emptyList();

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
        HumanPerson humanPerson = humanPeople.get(position);
        nameTextView.setText(humanPerson.getName());
        weightTextView.setText(String.valueOf(humanPerson.getWeightInGrams()));
        dayOfBirthTextView.setText(String.valueOf(humanPerson.calculateTimePassedSinceBirthdayInMonths()));
    }

    @Override
    public int getItemCount() {
        return humanPeople.size();
    }

    void setHumanPeople(List<HumanPerson> humanPeople) {
        this.humanPeople = humanPeople;
        notifyDataSetChanged();
    }
}
