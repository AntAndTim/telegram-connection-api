package me.antandtim.telegram.api.annotation

/**
 * Annotation is used for marking steps requiring additional processing
 * */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Step(val value: String)