package ru.looktv.launcher.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppUpdateDto(
    @SerialName("name")
    val name: String,
    @SerialName("version")
    val version: Int,
    @SerialName("url")
    val url: String,
    @SerialName("icon")
    val icon: String
)
