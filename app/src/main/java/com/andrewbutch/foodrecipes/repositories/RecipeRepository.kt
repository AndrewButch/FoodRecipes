package com.andrewbutch.foodrecipes.repositories

import androidx.lifecycle.LiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.RecipeApiClient

object RecipeRepository {
    private val recipeApiClient = RecipeApiClient

    fun getRecipe(): LiveData<List<Recipe>> {
        return recipeApiClient.recipe
    }

    fun searchRecipes(query: String, page: Int) {
        if (page == 0) {
            recipeApiClient.searchRecipes(query, 1)
        }
        recipeApiClient.searchRecipes(query, page)
    }
}