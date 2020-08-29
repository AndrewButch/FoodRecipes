package com.andrewbutch.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel() : ViewModel() {
    private val recipesRepository = RecipeRepository
    var isViewingRecipes = false

    fun getRecipeList(): LiveData<List<Recipe>> {
        return recipesRepository.getRecipe()
    }

    fun searchRecipes(query: String, page: Int) {
        isViewingRecipes = true
        recipesRepository.searchRecipes(query, page)
    }

    fun onBackPress(): Boolean {
        if (isViewingRecipes) {
            isViewingRecipes = false
            return false
        }
        return true
    }
}