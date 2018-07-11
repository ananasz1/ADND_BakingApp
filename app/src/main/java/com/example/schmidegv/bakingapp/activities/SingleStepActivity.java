package com.example.schmidegv.bakingapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.fragments.SingleStepFragment;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class SingleStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_step);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // if no saved state, create new fragment
        if (savedInstanceState == null) {

            SingleStepFragment stepsFragment = new SingleStepFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_single_step, stepsFragment)
                    .commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }
}
