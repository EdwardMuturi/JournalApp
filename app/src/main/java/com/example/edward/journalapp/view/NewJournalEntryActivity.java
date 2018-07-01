package com.example.edward.journalapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edward.journalapp.R;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import model.JournalEntity;

public class NewJournalEntryActivity extends AppCompatActivity implements View.OnClickListener {
    private Button doneButton;
    private EditText etTitle;
    private EditText etBody;

    private Box<JournalEntity> journalEntityBox;
    private Query<JournalEntity> journalEntityQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal_entry);
        setTitle(getString(R.string.title_new_entry));

        componentSetUp();

    }

    private void componentSetUp(){
        // set up view components
        etTitle= findViewById(R.id.et_entry_title);
        etBody= findViewById(R.id.et_entry_body);
        doneButton= findViewById(R.id.btn_done);
        doneButton.setOnClickListener(this);

        //Objectbox initialization
        BoxStore boxStore= ((model.App) getApplication()).getBoxStore();
        journalEntityBox= boxStore.boxFor(JournalEntity.class);

//        [START] Set fields for update
        Bundle bundle= getIntent().getExtras();
        if (bundle != null){
            String passedTitle= bundle.getString("Title");
            String passedBody= bundle.getString("Body");

            etTitle.setText(passedTitle);
            etBody.setText(passedBody);
            doneButton.setText(R.string.text_save);

            addJournal();

        } //else
            //clearFields(); //[END] set fields for update
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if (id == R.id.btn_done){
            Toast.makeText(getApplicationContext(), "Note Added", Toast.LENGTH_LONG).show();
            addJournal();

            Intent intent= new Intent(this, JournalEntriesActivity.class);
            startActivity(intent);
        }
    }

    private void clearFields(){
        etTitle.setText("");
        etBody.setText("");
    }

    private void addJournal(){
        String newEntryTitle= etTitle.getText().toString();
        String newEntryBody= etBody.getText().toString();

        JournalEntity journalEntity= new JournalEntity();
        journalEntity.setTitle(newEntryTitle);
        journalEntity.setMessage(newEntryBody);

        journalEntityBox.put(journalEntity);
    }
}
