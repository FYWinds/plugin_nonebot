package cn.windis.pluginnonebot.api

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import java.util.*

class ConsoleSender : ConsoleCommandSender {
    private var op: Boolean = true

    private var messages: MutableList<String> = ArrayList()

    fun getMessages(): List<String> {
        return messages
    }

    override fun isOp(): Boolean {
        return op
    }

    override fun setOp(value: Boolean) {
        op = value
    }

    override fun isPermissionSet(name: String): Boolean {
        return true
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        return true
    }

    override fun hasPermission(name: String): Boolean {
        return true
    }

    override fun hasPermission(perm: Permission): Boolean {
        return true
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun removeAttachment(attachment: PermissionAttachment) {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissions() {
        TODO("Not yet implemented")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: String) {
        messages.add(message)
    }

    override fun sendMessage(vararg messages: String?) {
        for (message in messages) {
            this.messages.add(message ?: continue)
        }
    }

    override fun sendMessage(sender: UUID?, message: String) {
        sendMessage("[$sender] $message")
    }

    override fun sendMessage(sender: UUID?, vararg messages: String?) {
        for (message in messages) {
            sendMessage(sender, message ?: continue)
        }
    }

    override fun getServer(): Server {
        return Bukkit.getServer()
    }

    override fun getName(): String {
        return "Console"
    }

    override fun spigot(): CommandSender.Spigot {
        TODO("Not yet implemented")
    }

    override fun isConversing(): Boolean {
        return false
    }

    override fun acceptConversationInput(input: String) {
        TODO("Not yet implemented")
    }

    override fun beginConversation(conversation: Conversation): Boolean {
        return false
    }

    override fun abandonConversation(conversation: Conversation) {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(conversation: Conversation, details: ConversationAbandonedEvent) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(message: String) {
        sendMessage(message)
    }

    override fun sendRawMessage(sender: UUID?, message: String) {
        sendMessage(sender, message)
    }

}