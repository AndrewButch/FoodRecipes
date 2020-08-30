package com.andrewbutch.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.repositories.RecipeRepository

private const val TAG = "RecipeListViewModel"

class RecipeListViewModel() : ViewModel() {
    private val recipesRepository = RecipeRepository
    var isViewingRecipes = false
    var isPerformingQuery = false

    fun getRecipeList(): LiveData<List<Recipe>> {
        return recipesRepository.getRecipe()
    }

    fun searchRecipes(query: String, page: Int) {
        isViewingRecipes = true
        isPerformingQuery = true
        recipesRepository.searchRecipes(query, page)
    }

    fun onBackPress(): Boolean {
        if (isPerformingQuery) {
            recipesRepository.cancelRequest()
            isPerformingQuery = false
        }
        return when {
            isViewingRecipes -> {
                isViewingRecipes = false
                false
            }
            else -> true
        }
    }

    fun searchNextPage() {
        if (!isPerformingQuery && isViewingRecipes) {
            isPerformingQuery = true
            recipesRepository.searchNextPage()
        }
    }
}