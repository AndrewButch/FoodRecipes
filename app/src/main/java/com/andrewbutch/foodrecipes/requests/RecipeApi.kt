package com.andrewbutch.foodrecipes.requests

import com.andrewbutch.foodrecipes.requests.resonses.RecipeResponse
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("api/get")
    fun getRecipe(
        @Query("rId") recipe_id: String
    ): Call<RecipeResponse>

    @GET("api/search")
    fun searchRecipes(
        @Query("q") search_keyword: String,
        @Query("page") page: String
    ): Call<RecipeSearchResponse>
}