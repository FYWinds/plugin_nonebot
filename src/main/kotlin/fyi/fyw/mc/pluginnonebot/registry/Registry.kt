package fyi.fyw.mc.pluginnonebot.registry

interface Registry<T> {
    val values: MutableMap<String, T>

    fun register(value: T)

    fun remove(value: T)

    fun get(id: String): T?
}
