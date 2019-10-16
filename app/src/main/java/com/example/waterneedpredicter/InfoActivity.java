package com.example.waterneedpredicter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private Button backButton;
    TextView link;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        backButton = findViewById(R.id.info_back_button);
        link = findViewById(R.id.info_help2_textView);
        actionBar = getSupportActionBar();

        backButton.setOnClickListener(view -> finish());
        // with this method the hyperlink in the string 'info_help2_textView' works
        link.setMovementMethod(LinkMovementMethod.getInstance());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}
