package fyi.fyw.mc.pluginnonebot.utils

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

class NLogger(private val name: String) : Logger(name, null) {

    init {
        parent = PluginNonebot.pluginLogger
        level = PluginNonebot.loadedConfig.output.level
        // TODO: implement the logging to file
    }

    override fun log(logRecord: LogRecord) {
        logRecord.message = logRecord.message
        super.log(logRecord)
    }

    fun trace(message: String) {
        this.finest(message)
    }

    fun debug(message: String) {
        this.finer(message)
    }

    override fun info(message: String) {
        super.info(message)
    }

    fun infoDebug(infoMessage: String, debugMessage: String) {
        if (this.level.intValue() > Level.FINER.intValue()) {
            info(infoMessage)
        } else {
            debug(debugMessage)
        }
    }

    fun warn(message: String) {
        this.warning(message)
    }

    fun error(message: String) {
        this.severe(message)
    }

    fun error(message: String, exception: Exception) {
        debug(exception.stackTraceToString())
        this.severe(message)
    }

    fun error(exception: Exception?) {
        exception?.stackTraceToString()?.let { debug(it) }
        this.severe(exception?.message ?: "Unknown internal error!!")
    }
}
