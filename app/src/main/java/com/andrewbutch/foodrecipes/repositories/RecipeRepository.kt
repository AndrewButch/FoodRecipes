package com.andrewbutch.foodrecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.RecipeApiClient

object RecipeRepository {
    private val recipeApiClient = RecipeApiClient
    private var prevQuery = ""
    private var prevPage: Int = 0

    private val _recipes = MediatorLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    val isQueryExhausted: MutableLiveData<Boolean> = MutableLiveData()
    init {
        initMediators()
    }

    private fun initMediators() {
        val recipes = recipeApiClient.recipes
        _recipes.addSource(recipes) { recipeList ->
            if (recipeList != null) {
                _recipes.value = recipeList
                doneQuery(recipeList)
            } else {
                doneQuery(null)
            }

        }
    }

    private fun doneQuery(recipes: List<Recipe>?) {
        if (recipes == null || recipes.size % 30 != 0) {
            isQueryExhausted.value = true
        }
    }

    fun searchRecipes(query: String, page: Int) {
        if (page == 0) {
            recipeApiClient.searchRecipes(query, 1)
        }
        prevQuery = query
        prevPage = page
        isQueryExhausted.value = false
        recipeApiClient.searchRecipes(query, page)
    }

    fun searchNextPage() {
        searchRecipes(prevQuery, prevPage + 1)
    }

    fun getSelectedRecipe(): LiveData<Recipe> {
        return recipeApiClient.recipe
    }

    fun searchRecipeById(recipeId: String) {
        recipeApiClient.searchRecipeById(recipeId)
    }

    fun cancelRequest() {
        recipeApiClient.cancelRequest()
    }
}