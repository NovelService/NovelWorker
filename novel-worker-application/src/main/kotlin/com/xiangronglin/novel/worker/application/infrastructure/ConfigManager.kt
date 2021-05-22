package com.xiangronglin.novel.worker.application.infrastructure

import com.sksamuel.hoplite.ConfigLoader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.nio.file.Path

@Configuration
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
class ConfigManager {

    companion object {
        const val NOVEL_CONFIG_FILE_KEY = "NOVEL_SERVICE_CONFIG_FILE"
        private const val DEFAULT_NOVEL_CONFIG_FILE = "/config/config.yml"

        private val LOG = LoggerFactory.getLogger(ConfigManager::class.java)
    }

    final val config: Config

    init {
        val file = Path.of(System.getProperty(NOVEL_CONFIG_FILE_KEY, DEFAULT_NOVEL_CONFIG_FILE))
        LOG.info("Loading config from ${file.toAbsolutePath()}")
        config = ConfigLoader().loadConfigOrThrow(file)
        LOG.info("Config loaded successfully")
    }
}