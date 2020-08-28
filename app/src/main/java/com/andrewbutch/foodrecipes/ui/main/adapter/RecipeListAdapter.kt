package com.andrewbutch.foodrecipes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe

class RecipeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = listOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RECIPE_TYPE -> {
                return RecipeViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_item, parent, false)
                )
            }
            LOADING_TYPE -> {
                return LoadingViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_loading, parent, false)
                )
            }
            else -> {
                return RecipeViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.recipe_list_item, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            RECIPE_TYPE -> (holder as RecipeViewHolder).bind(data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size ?: 0
    }

    fun setData(data: List<Recipe>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun displayLoading() {
        if (!isLoading()) {
            val recipe = Recipe(
                "Loading...",
                "",
                "",
                arrayOf(),
                "",
                "",
                0f
            )
            data = listOf(recipe)
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean {
        if (data.isNotEmpty()) {
            return data.last().title.equals("Loading...")
        }
        return false
    }

    override fun getItemViewType(position: Int): Int {
        val recipe = data[position]
        return if (recipe.title.equals("Loading...")) {
            LOADING_TYPE
        } else {
            RECIPE_TYPE
        }
    }


    companion object ViewHolderType {
        const val RECIPE_TYPE = 1
        const val LOADING_TYPE = 2
    }


}