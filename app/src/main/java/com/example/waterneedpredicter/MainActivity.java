package com.example.waterneedpredicter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText nameEditText;
    private EditText weightEditText;
    private Button dayOfBirthButton;
    private Button createPersonButton;
    private Spinner weightUnitSpinner;
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
        weightUnitSpinner = findViewById(R.id.main_weight_unit_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightUnitSpinner.setAdapter(adapter);
        weightUnitSpinner.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_create_person_button:
                createHumanPerson();
                break;
            case R.id.main_day_of_birth_button:
                pickDayOfBirth();
                break;
        }
    }

    private void goToInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    private void showAllPeople() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    private void pickDayOfBirth() {
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
        // We do not yet know whether the user choose kg or g as weight unit!
        int weightInput = Integer.parseInt(weightEditText.getText().toString());
        boolean isWeightUnitInKg = weightUnitSpinner.getSelectedItemPosition() == 0;
        // If the weight unit is already in grams we have to do nothing, otherwise we have to multiply the kg by one thousand.
        int weightInGrams = isWeightUnitInKg ? weightInput * 1_000 : weightInput;
        HumanPerson createdPerson = new HumanPerson(name, weightInGrams, yearBorn, monthBorn, dayOfMonthBorn);
        new SaveHumanPersonTask().execute(createdPerson);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.yearBorn = year;
        // January is represented as 0 in android. I don't know why.
        this.monthBorn = month + 1;
        this.dayOfMonthBorn = dayOfMonth;
        dayOfBirthButton.setText(yearBorn + "-" + monthBorn + "-" + dayOfMonthBorn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_info:
                goToInfo();
                break;
            case R.id.go_to_overview:
                showAllPeople();
                break;
        }
        return true;
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
