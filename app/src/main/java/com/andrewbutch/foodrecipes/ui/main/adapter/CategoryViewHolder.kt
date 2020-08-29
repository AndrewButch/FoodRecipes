package com.andrewbutch.foodrecipes.ui.main.adapter

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewbutch.foodrecipes.R
import com.andrewbutch.foodrecipes.model.Recipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recipe_list_category.view.*


class CategoryViewHolder(itemView: View, private val listener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    private val categoryImage = itemView.category_image
    private val categoryTitle = itemView.category_title

    fun bind(recipe: Recipe) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .error(R.drawable.ic_launcher_background)

        val path: Uri = Uri.parse(
            "android.resource://com.andrewbutch.foodrecipes/drawable/${recipe.image_url}"
        )
        Glide.with(itemView)
            .setDefaultRequestOptions(options)
            .load(path)
            .into(categoryImage)

        categoryTitle.text = recipe.title
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onCategoryClick(categoryTitle.text.toString())
    }
}