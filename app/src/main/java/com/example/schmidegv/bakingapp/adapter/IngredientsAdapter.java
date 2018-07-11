package com.example.schmidegv.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.model.Ingredients;

import java.util.ArrayList;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    private ArrayList<Ingredients> mIngredientsData;

    public TextView mName;
    private final IngredientsAdapterOnClickHandler mClickHandler;
    private TextView mMeasure;
    private TextView mQuantity;
    private TextView mDot;

    public interface IngredientsAdapterOnClickHandler {
        void onClick(Ingredients ingredientsItem);
    }


    public IngredientsAdapter(IngredientsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mIngredientsData = new ArrayList<>();
    }


    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public IngredientsAdapterViewHolder(View view) {
            super(view);

            mName = view.findViewById(R.id.tv_ingr_name);
            mMeasure = view.findViewById(R.id.tv_ingr_measurement);
            mQuantity = view.findViewById(R.id.tv_ingr_quantity);
            mDot = view.findViewById(R.id.tv_ingr_dot);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Ingredients ingredientsItem = mIngredientsData.get(adapterPosition);
            mClickHandler.onClick(ingredientsItem);
        }
    }


    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new IngredientsAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        String ingredient = mIngredientsData.get(position).getIngredient();
        String ingredientUpper = ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1);
        String measure = mIngredientsData.get(position).getMeasure();
        String quantity = String.valueOf(mIngredientsData.get(position).getQuantity());

        mName.setText(ingredientUpper);
        mMeasure.setText(measure);
        mQuantity.setText(quantity);
        mDot.setText("\u2022");

        Log.e(TAG, "name: " + ingredient);
    }


    @Override
    public int getItemCount() {
        if (null == mIngredientsData) return 0;
        return mIngredientsData.size();
    }


    public void setIngredientData(ArrayList<Ingredients> ingredientsData) {
        mIngredientsData = ingredientsData;
        notifyDataSetChanged();
    }

}
