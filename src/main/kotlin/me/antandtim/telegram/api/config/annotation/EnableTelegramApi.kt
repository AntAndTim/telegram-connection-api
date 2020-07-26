package me.antandtim.telegram.api.config.annotation

import org.springframework.context.annotation.Import
import me.antandtim.telegram.api.config.TelegramApiConfig

/**
 * Annotation enables telegram handlers
 * */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(TelegramApiConfig::class)
annotation class EnableTelegramApi