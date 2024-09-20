package com.example.moviessearch.model.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Objects

data class Movie(
    var description: String?,
    val genres: List<String>,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("localized_name")
    val localizedName: String,
    val name: String,
    val rating: Double,
    val year: Int
): Serializable{
    override fun hashCode(): Int {
        return Objects.hash(description, genres, id, imageUrl, localizedName, name, rating, year)
    }
}