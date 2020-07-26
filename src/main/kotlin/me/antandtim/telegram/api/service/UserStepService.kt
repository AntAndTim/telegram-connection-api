package me.antandtim.telegram.api.service

import org.springframework.stereotype.Service
import me.antandtim.telegram.api.domain.UserStep
import me.antandtim.telegram.api.repository.UserStepRepository

@Service
class UserStepService(private val userStepRepository: UserStepRepository) {
  fun getStep(chatId: Long) = userStepRepository.findByChatId(chatId)
  
  fun setStep(chatId: Long, step: String): UserStep? =
    userStepRepository.findByChatId(chatId)
      ?.let { userStepRepository.save(it.copy(step = step)) } // Update if exists
    ?: userStepRepository.save(UserStep(chatId = chatId, step = step)) // Otherwise, create new
}