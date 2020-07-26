package me.antandtim.telegram.api.annotation

/**
 * Annotation is used for marking full-text matched commands
 * */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Command(val value: String)