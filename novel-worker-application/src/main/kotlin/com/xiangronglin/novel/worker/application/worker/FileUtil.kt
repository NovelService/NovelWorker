package com.xiangronglin.novel.worker.application.worker

fun String.replaceInvalidFilenameChar(): String {
    return this.replace("[\\\\/:*?\"<>|]".toRegex(), "_")
}