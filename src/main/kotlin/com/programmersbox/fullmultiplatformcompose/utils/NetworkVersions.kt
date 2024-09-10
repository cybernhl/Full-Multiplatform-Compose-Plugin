package com.programmersbox.fullmultiplatformcompose.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NetworkVersions {

    companion object {
        const val githubRepoUrl = "https://github.com/jakepurple13/Full-Multiplatform-Compose-Plugin"
    }

    private val json = Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json(json) }
    }

    suspend fun getVersions(remoteVersions: Boolean) = runCatching {
        check(remoteVersions)
        client
            .get("https://raw.githubusercontent.com/jakepurple13/Full-Multiplatform-Compose-Plugin/main/versions.json")
            .bodyAsText()
            .let { json.decodeFromString<ProjectVersions>(it) }
    }.getOrElse {
        it.printStackTrace()
        ProjectVersions()
    }
}

@Serializable
data class ProjectVersions(
    val kotlinVersion: String = "2.0.20",
    val agpVersion: String = "8.5.0",
    val composeVersion: String = "1.6.11",
    val androidxAppCompat: String = "1.7.0",
    val androidxCore: String = "1.13.1",
    val ktor: String = "2.3.12",
    val koin: String = "3.5.3",
    val ktorfit: String = "1.12.0",
    val napier: String = "2.6.1",
)