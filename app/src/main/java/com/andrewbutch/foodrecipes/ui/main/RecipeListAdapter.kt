package com.andrewbutch.foodrecipes.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe

class RecipeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = listOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recipe_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecipeViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size ?: 0
    }

    fun setData(data: List<Recipe>) {
        this.data = data
        notifyDataSetChanged()
    }


}