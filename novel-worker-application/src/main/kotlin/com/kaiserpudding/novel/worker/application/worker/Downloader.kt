package com.kaiserpudding.novelservice.worker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class Downloader {
    suspend fun download(url: String): String {
        @Suppress("BlockingMethodInNonBlockingContext")
        return withContext(Dispatchers.IO) {
            val isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows")
            val process = if (isWindows) {
                ProcessBuilder("cmd.exe", "/c", "readable \"$url\"")
                    .start()
            } else {
                ProcessBuilder("sh", "-c", "readable \"$url\"")
                    .start()
            }
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            return@withContext reader.readText()
        }
    }
}