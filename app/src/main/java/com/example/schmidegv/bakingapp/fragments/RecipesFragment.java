package com.example.schmidegv.bakingapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.activities.DetailsActivity;
import com.example.schmidegv.bakingapp.adapter.RecipesAdapter;
import com.example.schmidegv.bakingapp.model.Recipe;
import com.example.schmidegv.bakingapp.networking.ApiClient;
import com.example.schmidegv.bakingapp.networking.RecipeParsing;
import com.example.schmidegv.bakingapp.widget.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class RecipesFragment extends Fragment implements RecipesAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = RecipesFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private ArrayList<Recipe> mRecipes;
    public static RecipeParsing mRecipeParsing;

    public RecipesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecipeParsing = ApiClient.getApiClient().create(RecipeParsing.class);

        mRecyclerView = rootView.findViewById(R.id.rv_recipe);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this,mRecipes);
        mRecyclerView.setAdapter(mRecipesAdapter);

        loadRecipeData();

        return rootView;
    }


    private void loadRecipeData() {
        if (!isOnline()) return;

        Call<List<Recipe>> call = mRecipeParsing.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes = (ArrayList<Recipe>) response.body();
                Log.e(TAG, "loading "+ mRecipes );
                mRecipesAdapter.setRecipeData(mRecipes);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        getActivity().setTitle(R.string.choose_one);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(Recipe recipeItem) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("recipeObject", recipeItem);
        startActivity(intent);
        Log.e(TAG, "click ");


        Intent intentWidget = new Intent(getActivity(), BakingWidgetProvider.class);
        intentWidget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
        intentWidget.putExtra("recipeObject", recipeItem);
        getActivity().sendBroadcast(intentWidget);
    }


}

