package fyi.fyw.mc.pluginnonebot.config

import org.bukkit.configuration.file.YamlConfiguration

class MessageConfig(config: YamlConfiguration) {
    var postFormat: PostFormat = PostFormat.TEXT
    var placeholderapi: Boolean = false
    var unicodeEmoji: Boolean = false

    init {
        postFormat = PostFormat.valueOf(
            config.getString("message.format", "TEXT")!!.uppercase(),
        )
        placeholderapi = config.getBoolean("message.placeholderapi", false)
        unicodeEmoji = config.getBoolean("message.unicode-emoji", false)
    }

    enum class PostFormat {
        TEXT,
        BINARY,
    }
}
