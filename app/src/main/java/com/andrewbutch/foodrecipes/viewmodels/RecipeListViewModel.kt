package com.andrewbutch.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel() : ViewModel() {
    private val recipesRepository = RecipeRepository
    var isViewingRecipes = false
    var isPerformingQuery = false

    fun getRecipeList(): LiveData<List<Recipe>> {
        return recipesRepository.recipes
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
        val isExhausted: Boolean = isQueryExhausted().value?: false
        if (!isPerformingQuery
            && isViewingRecipes
            && !isExhausted) {
            isPerformingQuery = true
            recipesRepository.searchNextPage()
        }
    }

    fun isQueryExhausted(): LiveData<Boolean> {
        return recipesRepository.isQueryExhausted
    }
}