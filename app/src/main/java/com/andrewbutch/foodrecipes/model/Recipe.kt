package com.andrewbutch.foodrecipes.model

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val title: String?,
    val publisher: String?,
    val publisher_url: String?,
    val ingredients: Array<String>?,
    val recipe_id: String?,
    val image_url: String?,
    val social_rank: Float,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.apply {
                writeString(title)
                writeString(publisher)
                writeString(publisher_url)
                writeStringArray(ingredients)
                writeString(recipe_id)
                writeString(image_url)
                writeFloat(social_rank)
            }
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (title != other.title) return false
        if (publisher != other.publisher) return false
        if (publisher_url != other.publisher_url) return false
        if (ingredients != null) {
            if (other.ingredients == null) return false
            if (!ingredients.contentEquals(other.ingredients)) return false
        } else if (other.ingredients != null) return false
        if (recipe_id != other.recipe_id) return false
        if (image_url != other.image_url) return false
        if (social_rank != other.social_rank) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (publisher?.hashCode() ?: 0)
        result = 31 * result + (publisher_url?.hashCode() ?: 0)
        result = 31 * result + (ingredients?.contentHashCode() ?: 0)
        result = 31 * result + (recipe_id?.hashCode() ?: 0)
        result = 31 * result + (image_url?.hashCode() ?: 0)
        result = 31 * result + social_rank.hashCode()
        return result
    }


    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

}

