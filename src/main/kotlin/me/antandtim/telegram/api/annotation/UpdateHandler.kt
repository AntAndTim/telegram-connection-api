package me.antandtim.telegram.api.annotation

/**
 * Annotation marks function that will handle the incoming message
 * */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class UpdateHandler