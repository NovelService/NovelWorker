package com.kaiserpudding.novel.worker.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NovelWorkerApplication

fun main(args: Array<String>) {
    runApplication<NovelWorkerApplication>(*args)
}
