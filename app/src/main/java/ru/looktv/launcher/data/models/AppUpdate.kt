package ru.looktv.launcher.data.models

data class AppUpdate(
    val name: String,
    val version: Int,
    val url: String,
    val icon: String?
)