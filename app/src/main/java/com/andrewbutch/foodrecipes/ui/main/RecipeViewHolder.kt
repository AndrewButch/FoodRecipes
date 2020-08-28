package com.andrewbutch.foodrecipes.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recipe_list_item.view.*
import kotlin.math.roundToInt


open class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title = itemView.recipe_title
    val publisher = itemView.recipe_publisher
    val socialScore = itemView.recipe_social_score
    val image = itemView.recipe_image


    fun bind(recipe: Recipe) {
        title.text = recipe.title
        publisher.text = recipe.publisher
        socialScore.text = (recipe.social_rank.roundToInt()).toString()

        val requestOptions = RequestOptions()
            .centerCrop()
            .error(R.drawable.ic_launcher_background)
        Glide.with(itemView)
            .setDefaultRequestOptions(requestOptions)
            .load(recipe.image_url)
            .into(image)
    }

}

