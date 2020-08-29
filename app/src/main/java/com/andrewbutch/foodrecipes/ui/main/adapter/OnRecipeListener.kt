package com.andrewbutch.foodrecipes.ui.main.adapter

interface OnRecipeListener {
    fun onRecipeClick(position: Int)

    fun onCategoryClick(title: String)
}