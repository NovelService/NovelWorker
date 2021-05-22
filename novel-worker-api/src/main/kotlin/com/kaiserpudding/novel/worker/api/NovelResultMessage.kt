package com.kaiserpudding.novel.worker.api

import java.io.Serializable

data class NovelResultMessage(
    val novelId: String,
    val status: NovelResultStatus,
    val fileKey: String?
): Serializable

enum class NovelResultStatus: Serializable {
    SUCCESS, FAILURE
}