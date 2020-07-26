package me.antandtim.telegram.api.container

import org.springframework.stereotype.Component
import me.antandtim.telegram.api.annotation.Step
import me.antandtim.telegram.api.exception.DuplicateStepException
import me.antandtim.telegram.api.util.aopClass
import me.antandtim.telegram.api.util.noAnnotation

@Component class StepContainer : OperationContainer() {
  private val container = mutableMapOf<String, Any>()
  
  override fun saveInternal(handler: Any) {
    if (handler.noAnnotation(Step::class)) {
      return
    }
    
    val stepName = handler.aopClass().getAnnotation(Step::class.java).value
    if (container[stepName] == null) {
      container[stepName] = handler
    } else {
      throw DuplicateStepException(stepName)
    }
  }
  
  override fun get(operation: String) = container[operation]
}