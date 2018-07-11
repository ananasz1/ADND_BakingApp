package com.example.schmidegv.bakingapp.networking;

import com.example.schmidegv.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public interface RecipeParsing {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}

