package com.example.schmidegv.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.model.Ingredients;
import com.example.schmidegv.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class BakingWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BakingWidgetProvider.class.getSimpleName();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        // update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        if (intent.hasExtra("recipeObject")) {

            Recipe recipeObject = intent.getParcelableExtra("recipeObject");

            ArrayList<Ingredients> ingredients = recipeObject.getIngredients();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < ingredients.size(); i++) {
                Ingredients item = ingredients.get(i);
                String ingredientName = item.getIngredient();
                String ingredientUpper = ingredientName.substring(0,1).toUpperCase() + ingredientName.substring(1);

                String ingredientQuantity = String.valueOf(item.getQuantity());
                String ingredientMeasure = item.getMeasure();

                builder.append(ingredientUpper).append(" ").append(ingredientQuantity).append(" ").append(ingredientMeasure).append("\n");
            }

            Log.e(TAG, "listString " + builder);

            String name = recipeObject.getName();
            views.setTextViewText(R.id.widget_title, name);
            views.setTextViewText(R.id.widget_list, builder);

            updateWidget(context, views);
        }
    }

    public void updateWidget(Context context, RemoteViews remoteViews) {
        ComponentName widgetComponent = new ComponentName(context, BakingWidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetComponent, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

