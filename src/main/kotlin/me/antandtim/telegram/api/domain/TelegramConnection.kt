package me.antandtim.telegram.api.domain

import me.antandtim.telegram.api.service.UserStepService
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

data class TelegramConnection<T : BotApiMethod<out Serializable>, R : Any?>(
    val update: Update,
    val send: (T) -> R,
    val step: String = "",
    private val userStepService: UserStepService
) {
    fun setStep(step: String) {
        userStepService.setStep(update.message.chatId, step)
    }
}