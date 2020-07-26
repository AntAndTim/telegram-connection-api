package me.antandtim.telegram.api.util

import org.springframework.aop.framework.AopProxyUtils
import kotlin.reflect.KClass

fun Any.hasAnnotation(clazz: KClass<*>) =
  this.aopClass().isAnnotationPresent(clazz.java.asSubclass(Annotation::class.java))

fun Any.noAnnotation(clazz: KClass<*>) = !this.hasAnnotation(clazz)

fun Any.aopClass() = AopProxyUtils.ultimateTargetClass(this)
