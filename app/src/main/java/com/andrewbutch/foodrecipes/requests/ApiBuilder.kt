package com.andrewbutch.foodrecipes.requests

import com.andrewbutch.foodrecipes.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {
    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRecipeApi(): RecipeApi {
        return buildRetrofit().create(RecipeApi::class.java)
    }
}