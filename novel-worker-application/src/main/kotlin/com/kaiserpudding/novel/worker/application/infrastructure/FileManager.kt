package com.kaiserpudding.novel.worker.application.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolutePathString
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile

@ExperimentalPathApi
@Service
class FileManager {

    companion object {
        private const val NOVEL_FOLDER_KEY = "NOVEL_FOLDER_KEY"
        private const val DEFAULT_NOVEL_FOLDER = "/novel"

        private val LOG = LoggerFactory.getLogger(FileManager::class.java)
    }

    init {
        resolveNovelFolder().createDirectories()
    }

    fun saveNovel(file: Path): String {
        LOG.info("Saving novel from path '${file.absolutePathString()}'")
        if (!file.isRegularFile()) {
            throw IllegalArgumentException("Not a file")
        }
        val filename = UUID.randomUUID().toString() + "." + file.extension
        val destination = resolveNovelFolder().resolve(filename)
        file.copyTo(destination, false)

        LOG.info("Novel from path '${file.absolutePathString()}' saved to ${destination.absolutePathString()}")
        return filename
    }

    private fun resolveNovelFolder(): Path {
        return Path.of(System.getProperty(NOVEL_FOLDER_KEY, DEFAULT_NOVEL_FOLDER))
    }
}