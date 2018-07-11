package com.example.schmidegv.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.fragments.IngredientsFragment;
import com.example.schmidegv.bakingapp.fragments.StepsFragment;


/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();


    // phone or tablet
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

         if (findViewById(R.id.twoPanels) != null) {
            Log.e(TAG, "Two Pane TRUE ");

            mTwoPane = true;

        } else {
            mTwoPane = false;
        }
        StepsFragment.setTwoPane(mTwoPane);

        // if no saved state, create new fragment
        if (savedInstanceState == null) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            StepsFragment stepsFragment = new StepsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_ingredients, ingredientsFragment)
                    .add(R.id.container_steps, stepsFragment)
                    .commit();

        }

    }


}

