package me.antandtim.telegram.api.domain

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import me.antandtim.telegram.api.service.UserStepService

data class TelegramConnection(
  val update: Update,
  val sendMessage: (SendMessage) -> Unit,
  val step: String = "",
  private val userStepService: UserStepService
) {
  fun setStep(step: String) {
    userStepService.setStep(update.message.chatId, step)
  }
}