package com.example.edward.journalapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.edward.journalapp.R;

public class JournalEntriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries);
        setTitle(getString(R.string.title_journal_entries));
    }
}
