package me.antandtim.telegram.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import me.antandtim.telegram.api.TelegramApiScan
import me.antandtim.telegram.api.service.TelegramHandler
import me.antandtim.telegram.api.service.UserStepService

@Configuration
@EntityScan(basePackageClasses = [TelegramApiScan::class])
@ComponentScan(basePackageClasses = [TelegramApiScan::class])
@EnableJpaRepositories(basePackageClasses = [TelegramApiScan::class])
class TelegramApiConfig(
  private val context: ApplicationContext,
  private val userStepService: UserStepService
) {
  @Bean
  fun telegramHandler() = TelegramHandler(context, userStepService)
}