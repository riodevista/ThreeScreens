package ru.looktv.launcher.data

import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.looktv.launcher.core.network.createHttpClient
import ru.looktv.launcher.data.models.AppUpdate
import ru.looktv.launcher.data.responses.AppUpdateDto

private const val BASE_URL = "https://tv-look.ru/apps"

class RemoteAppsStorageRepository {

    private val httpClient = createHttpClient(BASE_URL)

    suspend fun getAppsUpdateList(): List<AppUpdate> {
        val appsVersionsList = httpClient.get("update.json")
            .body<List<AppUpdateDto>>().map {
                AppUpdate(it.name, it.version, it.url, it.icon)
            }
        return appsVersionsList
    }
}
