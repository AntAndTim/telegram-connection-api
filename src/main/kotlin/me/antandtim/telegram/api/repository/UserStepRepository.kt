package me.antandtim.telegram.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import me.antandtim.telegram.api.domain.UserStep

@Repository
interface UserStepRepository : JpaRepository<UserStep, Long> {
  fun findByChatId(chatId: Long): UserStep?
}