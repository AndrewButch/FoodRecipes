package com.andrewbutch.foodrecipes.utils

import android.util.Log
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import retrofit2.Call

object Testing {

    fun printRecipes(recipes: List<Recipe>, tag: String) {
        for (recipe in recipes) {
            Log.d(tag, "$recipe")
        }
    }

    fun printQuery(message: String, query: Call<RecipeSearchResponse>, tag: String) {
        Log.d(tag, "$message: ${query.request().url()} ")
    }
}