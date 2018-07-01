package com.example.edward.journalapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.edward.journalapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import adapter.JournalEntriesAdapter;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import model.App;
import model.JournalEntity;
import model.JournalEntity_;

public class JournalEntriesActivity extends AppCompatActivity implements JournalEntriesAdapter.EntryClickListener,
        JournalEntriesAdapter.LongEntryClickListener{
    RecyclerView recyclerView;
    JournalEntriesAdapter journalEntriesAdapter;
    List<JournalEntity> journalEntityList= new ArrayList<>();

    private Box<JournalEntity> journalEntityBox;
    private Query<JournalEntity> journalEntityQuery;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries);
        setTitle(getString(R.string.title_journal_entries));

        BoxStore boxStore= ((App) getApplication()).getBoxStore();
        journalEntityBox= boxStore.boxFor(JournalEntity.class);
        journalEntityQuery= journalEntityBox.query().order(JournalEntity_.title).build();
        journalEntityList= journalEntityQuery.find();

        recyclerView= findViewById(R.id.rv_journal_entries);
        journalEntriesAdapter= new JournalEntriesAdapter(journalEntityList, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(journalEntriesAdapter);
        recyclerView.setHasFixedSize(true);

        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build(); //[END] configure sign in

        googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth= FirebaseAuth.getInstance();


//        journalEntriesAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out){
            signOut();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        //firebase sign out
        firebaseAuth.signOut();

        Intent signOuIntent= new Intent(this, SignInActivity.class);
        startActivity(signOuIntent);

        //Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(R.id.layout_journal_entries), "You have successfully signed out", Snackbar.LENGTH_LONG).show();
                Log.d("Sign Out", "Api call failed");
            }
        });
        finish();
    }

    //action after fab clicked
    public void newEntry(View view) {
        Intent intentNewEntry= new Intent(getApplicationContext(),NewJournalEntryActivity.class);
        startActivity(intentNewEntry);
    }

    @Override
    public void onEntryClickListener(int position) {
        Intent editIntent= new Intent(this, NewJournalEntryActivity.class);

        JournalEntity entity= journalEntityList.get(position);

        Bundle extras= new Bundle();
        extras.putString("Title", entity.getTitle());
        extras.putString("Body", entity.getMessage());

        editIntent.putExtras(extras);
        startActivity(editIntent);


    }

    @Override
    public void onLongEntryClickListener(int position) {
        JournalEntity journalEntity= journalEntityList.get(position);
        journalEntityBox.remove(journalEntity);
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show();

        journalEntriesAdapter.notifyDataSetChanged();
    }
}
