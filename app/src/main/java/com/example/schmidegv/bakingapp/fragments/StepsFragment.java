package com.example.schmidegv.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.activities.SingleStepActivity;
import com.example.schmidegv.bakingapp.adapter.StepsAdapter;
import com.example.schmidegv.bakingapp.model.Recipe;
import com.example.schmidegv.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {
    private static final String TAG = StepsFragment.class.getSimpleName();
    private static boolean isTwoPane;
    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Step> stepsList;

    private Recipe recipeObject;
    public StepsFragment() {
        // Required empty public constructor
    }

    public static void setTwoPane(Boolean flag) {
        isTwoPane = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_steps_fragment);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        recipeObject = getActivity().getIntent().getParcelableExtra("recipeObject");
        if (recipeObject != null) {

            stepsList = recipeObject.getSteps();
            mStepsAdapter = new StepsAdapter(this);
            mStepsAdapter.setStepsData(stepsList);
            mRecyclerView.setAdapter(mStepsAdapter);

            Log.e(TAG, "step " + stepsList);
        }
        return rootView;
    }

    @Override
    public void onClick(Step stepItem) {
        if (!isTwoPane) {
            Log.e(TAG, "Phone Screen ");

            Intent intent = new Intent(getActivity(), SingleStepActivity.class);
            intent.putExtra("stepObject", stepItem);
            intent.putExtra("recipeObject", this.recipeObject);
            startActivity(intent);
        } else {
            Log.e(TAG, "Tablet Screen ");

            SingleStepFragment stepsOverviewFragment = new SingleStepFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_single_step, stepsOverviewFragment);
            transaction.addToBackStack(null);

            Bundle stepBundle = new Bundle();
            stepBundle.putParcelable("bundleStep", stepItem);
            SingleStepFragment.putItemT(stepBundle);

            transaction.commit();
        }
    }
}

