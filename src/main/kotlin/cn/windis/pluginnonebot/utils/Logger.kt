package cn.windis.pluginnonebot.utils

class Logger(private val bLogger: java.util.logging.Logger) {

    private val logger = bLogger

    private fun getBLogger(): java.util.logging.Logger {
        return logger
    }

    fun info(msg: String) {
        getBLogger().info("[INFO] $msg")
    }

    fun warning(msg: String) {
        getBLogger().warning("[WARNING] $msg")
    }
}