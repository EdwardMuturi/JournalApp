package model;

import android.app.Application;
import android.util.Log;

import com.example.edward.journalapp.BuildConfig;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {
    public static final String TAG= "ObjectBox";
    public static final boolean EXTERNAL_DIR= false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        boxStore= MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG){
            new AndroidObjectBrowser(boxStore).start(this);
        }

        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersion() + ")");
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
