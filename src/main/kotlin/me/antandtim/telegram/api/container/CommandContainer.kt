package me.antandtim.telegram.api.container

import me.antandtim.telegram.api.annotation.Command
import me.antandtim.telegram.api.util.aopClass
import me.antandtim.telegram.api.util.noAnnotation


class CommandContainer : OperationContainer() {
  private val container = mutableMapOf<String, Any>()
  
  override fun saveInternal(handler: Any) {
    if (handler.noAnnotation(Command::class)) {
      return
    }
    
    container[handler.aopClass().getAnnotation(Command::class.java).value] = handler
  }
  
  
  override fun get(operation: String) = container[operation]
}