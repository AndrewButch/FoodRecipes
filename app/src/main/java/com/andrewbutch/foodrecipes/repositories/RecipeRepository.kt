package com.andrewbutch.foodrecipes.repositories

import androidx.lifecycle.LiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.RecipeApiClient

object RecipeRepository {
    private val recipeApiClient = RecipeApiClient
    private var prevQuery = ""
    private var prevPage: Int = 0

    fun getRecipe(): LiveData<List<Recipe>> {
        return recipeApiClient.recipe
    }

    fun searchRecipes(query: String, page: Int) {
        if (page == 0) {
            recipeApiClient.searchRecipes(query, 1)
        }
        prevQuery = query
        prevPage = page
        recipeApiClient.searchRecipes(query, page)
    }

    fun searchNextPage() {
        searchRecipes(prevQuery, prevPage + 1)
    }

    fun cancelRequest() {
        recipeApiClient.cancelRequest()
    }
}