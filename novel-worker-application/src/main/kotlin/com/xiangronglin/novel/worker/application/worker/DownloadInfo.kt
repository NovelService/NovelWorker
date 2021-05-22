package com.xiangronglin.novel.worker.application.worker
/**
 * Information about downloaded chapter for toc use.
 *
 * @property url
 * @property name
 * @property isChapter
 */
data class DownloadInfo(
    var isChapter: Boolean = false,
    val name: String,
    val url: String
) {

}