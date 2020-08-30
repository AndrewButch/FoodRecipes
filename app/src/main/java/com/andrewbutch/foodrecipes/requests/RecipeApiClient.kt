package com.andrewbutch.foodrecipes.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.resonses.RecipeResponse
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeApiClient {
    private const val TAG = "RecipeApiClient"
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe

    private val recipeApi = ApiBuilder.getRecipeApi()
    private var searchRecipesJob: Job? = null
    private var searchRecipeByIdJob: Job? = null
    private var isJobCanceled = false

    fun searchRecipes(query: String, page: Int) {
        isJobCanceled = false
        searchRecipesJob = CoroutineScope(Dispatchers.IO).launch {
            delay(2000L)
            val responseCall = getRecipes(query, page)
            responseCall.enqueue(object : Callback<RecipeSearchResponse> {
                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: Response<RecipeSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        if (!isJobCanceled) {
                            response.body()?.let {
                                if (page == 1) {
                                    _recipes.postValue(it.recipes)
                                } else {
                                    val currentRecipes = recipes.value as List<Recipe>
                                    val expandedRecipesList = arrayListOf<Recipe>()
                                    expandedRecipesList.addAll(currentRecipes)
                                    expandedRecipesList.addAll(it.recipes)
                                    _recipes.postValue(expandedRecipesList)
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "onResponse: Error while loading recipes ${call.request()}")
                        _recipes.postValue(null)
                    }
                }

                override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun searchRecipeById(recipeId: String) {
        searchRecipeByIdJob = CoroutineScope(Dispatchers.IO).launch {
            delay(2000L)
            val responseCall = getRecipe(recipeId)
            responseCall.enqueue(object : Callback<RecipeResponse> {
                override fun onResponse(
                    call: Call<RecipeResponse>,
                    response: Response<RecipeResponse>
                ) {
                    if (response.isSuccessful) {
                        if (!isJobCanceled) {
                            response.body()?.let {
                                _recipe.postValue(it.recipe)
                            }
                        }
                    } else {
                        Log.d(TAG, "onResponse: Error while loading recipe ${call.request()}")
                        _recipe.postValue(null)
                    }
                }

                override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun cancelRequest() {
        searchRecipesJob?.let {
            isJobCanceled = true
            it.cancel()
        }
    }

    private fun getRecipes(query: String, page: Int): Call<RecipeSearchResponse> {
        return recipeApi.searchRecipes(query, page.toString())
    }

    private fun getRecipe(recipeId: String): Call<RecipeResponse> {
        return recipeApi.getRecipe(recipeId)
    }
}




