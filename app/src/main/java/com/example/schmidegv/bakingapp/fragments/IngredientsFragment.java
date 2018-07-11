package com.example.schmidegv.bakingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.adapter.IngredientsAdapter;
import com.example.schmidegv.bakingapp.model.Ingredients;
import com.example.schmidegv.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class IngredientsFragment extends Fragment implements IngredientsAdapter.IngredientsAdapterOnClickHandler {


    private static final String TAG = IngredientsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

    private ArrayList<Ingredients> ingredient;


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.card_ingredients, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_ingr_fragment);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);



        Recipe recipeObject = getActivity().getIntent().getParcelableExtra("recipeObject");
        if (recipeObject != null) {

            String recipeName =   recipeObject.getName();

            ingredient =  recipeObject.getIngredients();

            mIngredientsAdapter = new IngredientsAdapter(this);
            mIngredientsAdapter.setIngredientData(ingredient);

            mRecyclerView.setAdapter(mIngredientsAdapter);

            getActivity().setTitle(recipeName);

            Log.e(TAG, "ingredient " + ingredient );
        }



        return rootView;
    }

    @Override
    public void onClick(Ingredients ingredientsItem) {
        Log.e(TAG, "click on ingred" );
    }
}

