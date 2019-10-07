package com.example.waterneedpredicter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView humanPeopleRecyclerView;
    private HumanPersonRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        backButton = findViewById(R.id.overview_back_button);
        backButton.setOnClickListener(event -> finish());
        humanPeopleRecyclerView = findViewById(R.id.overview_human_people_recycler_view);
        humanPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HumanPersonRecyclerAdapter(MyRoomDatabase.getInstance(this).humanPersonDao());
        humanPeopleRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadHumanPeopleTask(MyRoomDatabase.getInstance(this).humanPersonDao(), adapter).execute();
    }

    static class LoadHumanPeopleTask extends AsyncTask<Void, Void, List<HumanPerson>> {

        private HumanPersonDao humanPersonDao;
        private HumanPersonRecyclerAdapter humanPersonRecyclerAdapter;

        LoadHumanPeopleTask(HumanPersonDao humanPersonDao, HumanPersonRecyclerAdapter humanPersonRecyclerAdapter) {
            this.humanPersonDao = humanPersonDao;
            this.humanPersonRecyclerAdapter = humanPersonRecyclerAdapter;
        }

        @Override
        protected List<HumanPerson> doInBackground(Void... voids) {
            return humanPersonDao.getAll();
        }

        @Override
        protected void onPostExecute(List<HumanPerson> humanPeople) {
            super.onPostExecute(humanPeople);
            humanPersonRecyclerAdapter.setHumanPeople(humanPeople);
        }
    }
}
