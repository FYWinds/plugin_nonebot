package cn.windis.pluginnonebot.utils

class MessageBuilder(type: String) {
    private val msg = HashMap<String, Any>()

    init {
        this.msg["type"] = type
    }

    fun id(id: String): MessageBuilder {
        this.msg["id"] = id
        return this
    }

    fun detailType(detail_type: String): MessageBuilder {
        this.msg["detail_type"] = detail_type
        return this
    }

    fun subType(sub_type: String): MessageBuilder {
        this.msg["sub_type"] = sub_type
        return this
    }

    fun data(data: Map<Any, Any>): MessageBuilder {
        this.msg["data"] = data
        return this
    }

    fun data(data: String): MessageBuilder {
        this.msg["data"] = data
        return this
    }

    fun time(time: Long): MessageBuilder {
        this.msg["time"] = time
        return this
    }

    fun time(): MessageBuilder {
        this.msg["time"] = (System.currentTimeMillis() / 1000).toInt()
        return this
    }

    fun custom(field: String, value: Any): MessageBuilder {
        this.msg[field] = value
        return this
    }

    fun build(): Map<String, Any> {
        if (this.msg["time"] == null) {
            this.time()
        }
        return this.msg
    }

    override fun toString(): String {
        return Serializer.serialize(this.msg)
    }
}