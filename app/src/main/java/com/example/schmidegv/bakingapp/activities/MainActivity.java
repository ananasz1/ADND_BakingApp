package com.example.schmidegv.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.fragments.RecipesFragment;


// I've used many sources and examples from Udacity toy apps
// from github:
    // https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz/tree/TMED.04-Solution-AddMediaSession
    // https://github.com/chiuki/espresso-samples/tree/master/idling-resource-okhttp
// from stackoverflow:
    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    // https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed/45482017#45482017
//from androhive
    // https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if no saved state, create new fragment
        if (savedInstanceState == null) {

            RecipesFragment recipesFragment = new RecipesFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_recipes, recipesFragment)
                    .commit();
        }
    }
}
