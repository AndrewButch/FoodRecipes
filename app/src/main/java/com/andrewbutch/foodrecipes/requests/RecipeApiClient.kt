package com.andrewbutch.foodrecipes.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeApiClient {
    private val _recipe = MutableLiveData<List<Recipe>>()
    val recipe: LiveData<List<Recipe>>
        get() {
            return _recipe
        }
    private val recipeApi = ApiBuilder.getRecipeApi()

    fun searchRecipes(query: String, page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val responseCall = getRecipes(query, page)
            responseCall.enqueue(object : Callback<RecipeSearchResponse> {
                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: Response<RecipeSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _recipe.postValue(it.recipes)
                        }
                    }
                }

                override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    fun getRecipeDetail(id: String) {

    }

    private fun getRecipes(query: String, page: Int): Call<RecipeSearchResponse> {
        return recipeApi.searchRecipes(query, page.toString())
    }
}