package com.andrewbutch.foodrecipes

import android.os.Bundle
import android.util.Log
import com.andrewbutch.foodrecipes.requests.ApiBuilder
import com.andrewbutch.foodrecipes.requests.RecipeApi
import com.andrewbutch.foodrecipes.requests.resonses.RecipeResponse
import com.andrewbutch.foodrecipes.requests.resonses.RecipeSearchResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        button_start.setOnClickListener {
//            if (progress_bar.visibility == View.GONE) {
//                showProgressBar(true)
//            } else {
//                showProgressBar(false)
//            }
//        }
        button_start.setOnClickListener {
            testApiDetailRequest()

        }
        button_start1.setOnClickListener {
            testApiSearchRequest()
        }
    }

    private fun testApiDetailRequest() {
        val api: RecipeApi = ApiBuilder.getRecipeApi()
        val call = api
            .getRecipe("41470")
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                Log.d(TAG, "onResponse: $response")
                if (response.isSuccessful) {
                    val recipe = (response.body() as RecipeResponse).recipe
                    Log.d(TAG, "Recipes: $recipe")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: url ${call.request().url()}")
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    private fun testApiSearchRequest() {
        val api: RecipeApi = ApiBuilder.getRecipeApi()
        val call = api
            .searchRecipes("chicken", "1")
        call.enqueue(object : Callback<RecipeSearchResponse> {
            override fun onResponse(
                call: Call<RecipeSearchResponse>,
                response: Response<RecipeSearchResponse>
            ) {
                Log.d(TAG, "onResponse: $response")
                if (response.isSuccessful) {
                    val recipes = (response.body() as RecipeSearchResponse).recipes
                    Log.d(TAG, "onResponse: $recipes")
                    for (recipe in recipes) {
                        Log.d(TAG, "$recipe: ")
                    }
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: url ${call.request().url()}")
                Log.d(TAG, "onFailure: $t")
            }

        })
    }
}