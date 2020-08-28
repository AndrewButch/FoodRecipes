package com.andrewbutch.foodrecipes.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrewbutch.foodrecipes.BaseActivity
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    private lateinit var adapter: RecipeListAdapter
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        )
            .get(RecipeListViewModel::class.java)

        observeRecipeViewModel()
        setupRecyclerView()
        searchRecipes("chicken", 1)
    }

    private fun setupRecyclerView() {
        adapter = RecipeListAdapter()
        recipe_list.layoutManager = LinearLayoutManager(applicationContext)
        recipe_list.adapter = adapter
    }

    private fun searchRecipes(query: String, page: Int) {
        viewModel.searchRecipes(query, page)
    }

    private fun observeRecipeViewModel() {
        viewModel.getRecipeList().observe(this) {
            adapter.setData(it)
        }
    }

    private fun printRecipeList(list: List<Recipe>) {
        Log.d(TAG, "printRecipeList: -------- New list --------")
        for (recipe in list) {
            Log.d(TAG, "printRecipeList: $recipe")
        }
    }
}