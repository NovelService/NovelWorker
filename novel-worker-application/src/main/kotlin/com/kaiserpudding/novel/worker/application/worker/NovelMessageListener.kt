package com.kaiserpudding.novel.worker.application.worker

import com.kaiserpudding.novel.worker.api.NovelConfigMessage
import com.kaiserpudding.novel.worker.api.NovelResultMessage
import com.kaiserpudding.novel.worker.api.NovelResultStatus
import com.kaiserpudding.novel.worker.application.infrastructure.AmqConfigurator
import com.kaiserpudding.novel.worker.application.infrastructure.FileManager
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermission
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.setPosixFilePermissions

@ExperimentalPathApi
@Component
class NovelMessageListener(
    @Autowired private val fileManager: FileManager,
    @Autowired private val jmsTemplate: JmsTemplate
) {

    companion object {
        private val LOG = LoggerFactory.getLogger(NovelMessageListener::class.java)
    }

    private val extractor = Extractor()

    @JmsListener(destination = AmqConfigurator.NOVEL_QUEUE)
    fun processNovel(config: NovelConfigMessage) {
        LOG.info("Received novel job with config: '$config'")

        try {
            runBlocking {
                if (config.tableOfContents) {
                    LOG.info("Multi download not implemented yet")
                } else {
                    LOG.info("Single download starting")
                    val tmpDir = Files.createTempDirectory("novel")
                    val tmpFile = Files.createTempFile(tmpDir, "novel", ".html")
                    val permissions = PosixFilePermission.values().toSet()
                    tmpDir.setPosixFilePermissions(permissions)
                    tmpFile.setPosixFilePermissions(permissions)

                    extractor.extractSingle(config.url, tmpFile.toFile())
                    val resultFile = extractor.convert(tmpFile.nameWithoutExtension, "epub", tmpDir.toFile())

                    val fileId = fileManager.saveNovel(resultFile)

                    val message = NovelResultMessage(config.novelId, NovelResultStatus.SUCCESS, fileId)
                    LOG.info("Notifying success of novel '${config.novelId} with file '$fileId'")
                    jmsTemplate.convertAndSend(message)
                }
            }
        } catch (e: Exception) {
            val message = NovelResultMessage(config.novelId, NovelResultStatus.FAILURE, null)
            LOG.error("Failed novel job with config: '$config'")
            jmsTemplate.convertAndSend(message)
        }

    }

}