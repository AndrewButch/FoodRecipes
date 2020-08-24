package com.andrewbutch.foodrecipes.requests.resonses

import com.andrewbutch.foodrecipes.model.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipe")
    @Expose
    val recipe: Recipe
)