package com.example.schmidegv.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private static final String TAG = StepsAdapter.class.getSimpleName();
    private ArrayList<Step> mStepsData;

    public TextView mName;
    public TextView mId;
    private final StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onClick(Step stepItem);
    }


    public StepsAdapter(StepsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mStepsData = new ArrayList<>();
    }


    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public StepsAdapterViewHolder(View view) {
            super(view);

            mName = view.findViewById(R.id.tv_step_descr);
            mId = view.findViewById(R.id.tv_step_id);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step stepItem = mStepsData.get(adapterPosition);
            mClickHandler.onClick(stepItem);
        }
    }


    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_step_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new StepsAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        String shortDescription = mStepsData.get(position).getShortDescription();
        String id = String.valueOf(mStepsData.get(position).getId());

        mName.setText(shortDescription);
        mId.setText(id + ". ");

        Log.e(TAG, "shortDescription: " + shortDescription);
        Log.e(TAG, "id: " + id);


    }


    @Override
    public int getItemCount() {
        if (null == mStepsData) return 0;
        return mStepsData.size();
    }


    public void setStepsData(ArrayList<Step> stepsData) {
        mStepsData = stepsData;
        notifyDataSetChanged();
    }
}

