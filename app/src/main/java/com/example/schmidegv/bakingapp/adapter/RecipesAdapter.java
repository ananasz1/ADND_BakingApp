package com.example.schmidegv.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeAdapterViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();
    private List<Recipe> mRecipeData;
    private TextView mName;
    private TextView mServings;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipeItem);
    }


    public RecipesAdapter(RecipeAdapterOnClickHandler clickHandler, ArrayList<Recipe> mRecipes) {
        mClickHandler = clickHandler;
        mRecipeData = mRecipes;

    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public RecipeAdapterViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.tv_name);
            mServings = view.findViewById(R.id.tv_servings);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipeItem = mRecipeData.get(adapterPosition);
            mClickHandler.onClick(recipeItem);
        }
    }


    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new RecipeAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        String name = mRecipeData.get(position).getName();
        String servings = String.valueOf(mRecipeData.get(position).getServings());
        String image = mRecipeData.get(position).getImage();
        String steps = String.valueOf(mRecipeData.get(position).getSteps());

        mName.setText(name);
        mServings.setText("Servings: " + servings);

        Log.e(TAG, "name: " + name);

    }


    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.size();
    }


    public void setRecipeData(List<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }

}
