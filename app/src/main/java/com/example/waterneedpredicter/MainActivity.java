package com.example.waterneedpredicter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText nameEditText;
    private EditText weightEditText;
    private Button dayOfBirthButton;
    private Button createPersonButton;
    private Button showAllPeopleButton;
    private int yearBorn;
    private int monthBorn;
    private int dayOfMonthBorn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.main_name_edit_text);
        weightEditText = findViewById(R.id.main_weight_edit_text);
        dayOfBirthButton = findViewById(R.id.main_day_of_birth_button);
        dayOfBirthButton.setOnClickListener(this);
        createPersonButton = findViewById(R.id.main_create_person_button);
        createPersonButton.setOnClickListener(this);
        showAllPeopleButton = findViewById(R.id.main_show_all_people_button);
        showAllPeopleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_create_person_button:
                createHumanPerson();
                break;
            case R.id.main_day_of_birth_button:
                pickDayOfBith();
                break;
            case R.id.main_show_all_people_button:
                showAllPeople();
                break;
        }
    }

    private void showAllPeople() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    private void pickDayOfBith() {
        // The picker dialog should be set to current day as a default.
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                this,
                year, month, day
        );
        // You are not allowed to choose a day in the future as birthday.
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private void createHumanPerson() {
        if (weightEditText.getText().toString().isEmpty()) {
            return;
        }
        // When the "yearBorn" is still set to 0, the user has not picked a birthday yet.
        if (yearBorn == 0) {
            return;
        }
        String name = nameEditText.getText().toString().trim();
        int weightInGrams = Integer.parseInt(weightEditText.getText().toString());
        HumanPerson createdPerson = new HumanPerson(name, weightInGrams, yearBorn, monthBorn, dayOfMonthBorn);
        new SaveHumanPersonTask().execute(createdPerson);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.yearBorn = year;
        // January is represented as 0 in android. I don't know why.
        this.monthBorn = month + 1;
        this.dayOfMonthBorn = dayOfMonth;
    }

    class SaveHumanPersonTask extends AsyncTask<HumanPerson, Void, Long> {
        @Override
        protected Long doInBackground(HumanPerson... humanPeople) {
            HumanPerson humanPerson = humanPeople[0];
            return MyRoomDatabase.getInstance(MainActivity.this).humanPersonDao().insert(humanPerson);
        }

        @Override
        protected void onPostExecute(Long humanPersonId) {
            super.onPostExecute(humanPersonId);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("id", humanPersonId.intValue());
            startActivity(intent);
        }
    }
}
