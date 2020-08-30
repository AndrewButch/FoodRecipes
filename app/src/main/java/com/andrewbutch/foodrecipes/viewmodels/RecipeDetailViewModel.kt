package com.andrewbutch.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.repositories.RecipeRepository

class RecipeDetailViewModel : ViewModel() {
    private val recipesRepository = RecipeRepository
    var recipeId: String? = null

    fun getSelectedRecipe(): LiveData<Recipe> {
        return recipesRepository.getSelectedRecipe()
    }

    fun searchRecipeById(recipeId: String) {
        this.recipeId = recipeId
        recipesRepository.searchRecipeById(recipeId)
    }
}