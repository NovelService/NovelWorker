package com.kaiserpudding.novel.worker.api

import java.io.Serializable

data class NovelConfigMessage(
    val novelId: String,
    val url: String,
    val tableOfContents: Boolean
) : Serializable