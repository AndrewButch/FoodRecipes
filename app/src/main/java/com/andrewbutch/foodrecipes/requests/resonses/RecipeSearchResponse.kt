package com.andrewbutch.foodrecipes.requests.resonses

import com.andrewbutch.foodrecipes.model.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse(
    @SerializedName("count")
    @Expose
    val count: Int,

    @SerializedName("recipes")
    @Expose
    val recipes: List<Recipe>
)