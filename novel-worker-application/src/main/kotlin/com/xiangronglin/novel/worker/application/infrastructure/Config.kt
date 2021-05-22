package com.xiangronglin.novel.worker.application.infrastructure

import com.sksamuel.hoplite.Masked

data class Config(
    val amq: AmqConfig
)

data class AmqConfig(
    val url: String,
    val username: String,
    val password: Masked
)