package com.example.waterneedpredicter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView ageTextView;
    private TextView predictedWaterNeedTextView;
    private Button backButton;
    private CheckBox pregnantCheckBox;
    private CheckBox breastfeedingCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findAllViews();
        this.backButton.setOnClickListener(view -> finish());

    }

    @Override
    protected void onResume() {
        super.onResume();
        int humanPersonId = getIntent().getIntExtra("id", 0);
        new LoadHumanPersonTask().execute(humanPersonId);
    }

    private void findAllViews() {
        nameTextView = findViewById(R.id.detail_name_text_view);
        ageTextView = findViewById(R.id.detail_age_text_view);
        predictedWaterNeedTextView = findViewById(R.id.detail_predicted_water_need_text_view);
        backButton = findViewById(R.id.detail_back_button);
        pregnantCheckBox = findViewById(R.id.detail_pregnant_check_box);
        breastfeedingCheckBox = findViewById(R.id.detail_breastfeeding_check_box);
    }

    class LoadHumanPersonTask extends AsyncTask<Integer, Void, HumanPerson> {

        @Override
        protected HumanPerson doInBackground(Integer... integers) {
            int humanPersonId = integers[0];
            return MyRoomDatabase.getInstance(DetailActivity.this).humanPersonDao().findById(humanPersonId);
        }

        @Override
        protected void onPostExecute(HumanPerson humanPerson) {
            super.onPostExecute(humanPerson);
            nameTextView.setText(humanPerson.getName());
            ageTextView.setText(humanPerson.getAgeRepresentation());
            predictedWaterNeedTextView.setText("" + humanPerson.predictWaterNeedInMl() + " ml");
            pregnantCheckBox.setChecked(humanPerson.isPregnant());
            breastfeedingCheckBox.setChecked(humanPerson.isBreastfeeding());
        }
    }
}
