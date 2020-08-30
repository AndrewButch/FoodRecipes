package com.andrewbutch.foodrecipes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe
import com.andrewbutch.foodrecipes.utils.Constants

class RecipeListAdapter(private val listener: OnRecipeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = arrayListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RECIPE_TYPE -> {
                return RecipeViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_item, parent, false),
                    listener
                )
            }
            LOADING_TYPE -> {
                return LoadingViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_loading, parent, false)
                )
            }
            EXHAUSTED_TYPE -> {
                return SearchExhaustedViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_exhausted, parent, false)
                )
            }
            CATEGORY_TYPE -> {
                return CategoryViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_category, parent, false),
                    listener
                )
            }
            else -> {
                return RecipeViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_item, parent, false),
                    listener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            RECIPE_TYPE -> (holder as RecipeViewHolder).bind(data[position])
            CATEGORY_TYPE -> (holder as CategoryViewHolder).bind(data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Recipe>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun displayLoading() {
        if (!isLoading()) {
            val recipe = Recipe(
                LOADING_KEY,
                "",
                "",
                arrayOf(),
                "",
                "",
                0f
            )
            setData(listOf(recipe))
        }
    }

    private fun isLoading(): Boolean {
        if (data.isNotEmpty()) {
            return data.last().title.equals(LOADING_KEY)
        }
        return false
    }

    fun displaySearchCategory() {
        val recipes = arrayListOf<Recipe>()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            val recipe = Recipe(
                Constants.DEFAULT_SEARCH_CATEGORIES[i],
                "",
                "",
                arrayOf(),
                "",
                Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i],
                -1f
            )
            recipes.add(recipe)
        }
        setData(recipes)
    }

    override fun getItemViewType(position: Int): Int {
        val recipe = data[position]
        return when {
            recipe.social_rank == -1f -> {
                CATEGORY_TYPE
            }
            recipe.title.equals(LOADING_KEY) -> {
                LOADING_TYPE
            }
            recipe.title.equals("Exhausted...") -> {
                EXHAUSTED_TYPE
            }
            position == data.lastIndex &&
                    position != 0 &&
                    !recipe.title.equals("Exhausted...") -> {
                LOADING_TYPE
            }
            else -> {
                RECIPE_TYPE
            }
        }
    }

    fun setQueryExhausted() {
        hideLoading()
        val exhaustedRecipe = Recipe(
            "Exhausted...",
            "",
            "",
            arrayOf(),
            "",
            "",
            0f
        )
        data.add(exhaustedRecipe)
        notifyItemInserted(data.lastIndex)
    }

    private fun hideLoading() {
        if (isLoading()) {
            for (recipe in data) {
                if (recipe.title.equals(LOADING_KEY)) {
                    data.remove(recipe)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun getSelectedRecipe(position: Int): Recipe? {
        if (!data.isNullOrEmpty()) {
            return data[position]
        }
        return null
    }


    companion object ViewHolderType {
        const val RECIPE_TYPE = 1
        const val LOADING_TYPE = 2
        const val CATEGORY_TYPE = 3
        const val EXHAUSTED_TYPE = 4

        const val LOADING_KEY = "Loading..."
    }


}