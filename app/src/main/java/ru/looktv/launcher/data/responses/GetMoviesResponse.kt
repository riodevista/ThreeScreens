package ru.looktv.launcher.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("_id")
    val id: String,
    @SerialName("alias")
    val alias: String,
    @SerialName("background")
    val background: BackgroundDto
)

@Serializable
data class BackgroundDto(
    @SerialName("image_1x")
    val image1x: String,
    @SerialName("image_15x")
    val image15x: String
)
