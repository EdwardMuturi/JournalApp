package com.example.edward.journalapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.edward.journalapp.R;

public class NewJournalEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal_entry);
        setTitle(getString(R.string.title_new_entry));
    }
}
