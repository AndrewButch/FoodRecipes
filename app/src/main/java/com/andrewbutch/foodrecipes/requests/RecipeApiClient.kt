package com.andrewbutch.foodrecipes.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import com.andrewbutch.foodrecipes.utils.Testing
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeApiClient {
    private const val TAG = "RecipeApiClient"
    private val _recipe = MutableLiveData<List<Recipe>>()
    val recipe: LiveData<List<Recipe>>
        get() {
            return _recipe
        }
    private val recipeApi = ApiBuilder.getRecipeApi()
    private var job: Job? = null
    private var isJobCanceled = false

    fun searchRecipes(query: String, page: Int) {
        isJobCanceled = false
        job = CoroutineScope(Dispatchers.IO).launch {
            delay(2000L)
            val responseCall = getRecipes(query, page)
            Testing.printQuery("Try to request",responseCall, TAG)
            responseCall.enqueue(object : Callback<RecipeSearchResponse> {
                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: Response<RecipeSearchResponse>
                ) {
                    if (response.isSuccessful && !isJobCanceled) {
                        response.body()?.let {
                            if (page == 1) {
                                _recipe.postValue(it.recipes)
                            } else {
                                val currentRecipes = recipe.value as List<Recipe>
                                val expandedRecipesList = arrayListOf<Recipe>()
                                expandedRecipesList.addAll(currentRecipes)
                                expandedRecipesList.addAll(it.recipes)
                                _recipe.postValue(expandedRecipesList)
                            }
                            Testing.printQuery("Resuest success", call, TAG)
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

    fun cancelRequest() {
        job?.let {
            isJobCanceled = true
            it.cancel()
        }
    }

    private fun getRecipes(query: String, page: Int): Call<RecipeSearchResponse> {
        return recipeApi.searchRecipes(query, page.toString())
    }



}