package com.andrewbutch.foodrecipes.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.ui.BaseActivity
import com.andrewbutch.foodrecipes.viewmodels.RecipeDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlin.math.roundToInt


class RecipeDetailActivity : BaseActivity() {
    private val TAG = "RecipeDetailActivity"
    private lateinit var viewModel: RecipeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        viewModel = ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())
            .get(RecipeDetailViewModel::class.java)

        showProgressBar(true)
        observeRecipe()
        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        val recipe: Recipe? = intent.getParcelableExtra("recipe")
        recipe?.let {
            Log.d(TAG, "getIncomingIntent: $recipe")
            recipe.recipe_id?.let {
                viewModel.searchRecipeById(it)
            }
        }
    }

    private fun observeRecipe() {
        viewModel.getSelectedRecipe().observe(this) { recipe ->
            Log.d(TAG, "Recipe title: ${recipe.title}")
            recipe.recipe_id?.let {
                if (it == viewModel.recipeId) {
                    recipe.ingredients?.let {
                        setupRecipeDetailView(recipe)
                    }
                }
            }
        }
    }

    private fun setupRecipeDetailView(recipe: Recipe) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)

        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(recipe.image_url)
            .into(recipe_image)

        recipe_title.text = recipe.title
        recipe_social_score.text = recipe.social_rank.roundToInt().toString()

        ingredients_container.removeAllViews()
        recipe.ingredients?.let {
            for (ingredient in it) {
                val textView = TextView(this)
                textView.apply {
                    text = ingredient
                    textSize = 15f
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                ingredients_container.addView(textView)
            }
        }
        showParent()
        showProgressBar(false)
    }

    private fun showParent() {
        parent_container.visibility = View.VISIBLE
    }
}