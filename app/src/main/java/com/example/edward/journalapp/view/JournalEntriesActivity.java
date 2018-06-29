package com.example.edward.journalapp.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.edward.journalapp.R;

public class JournalEntriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries);
        setTitle(getString(R.string.title_journal_entries));

    }
    //action after fab clicked
    public void newEntry(View view) {
        Intent intentNewEntry= new Intent(getApplicationContext(),NewJournalEntryActivity.class);
        startActivity(intentNewEntry);
    }

}
