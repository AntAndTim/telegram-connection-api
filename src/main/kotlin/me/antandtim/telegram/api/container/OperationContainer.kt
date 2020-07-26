package me.antandtim.telegram.api.container

import me.antandtim.telegram.api.annotation.UpdateHandler
import me.antandtim.telegram.api.exception.NoHandlerException
import me.antandtim.telegram.api.util.aopClass
import java.util.logging.Level
import java.util.logging.Logger

abstract class OperationContainer {
  private val serviceName = OperationContainer::class.simpleName
  private val logger = Logger.getLogger(serviceName)
  
  fun add(handler: Any) {
    logger.log(Level.INFO, "Adding $handler to $serviceName")
    
    if (!handler.aopClass().methods.any { it.isAnnotationPresent(UpdateHandler::class.java) }) {
      throw NoHandlerException()
    }
    
    saveInternal(handler)
    
    logger.log(Level.INFO, "Successfully added $handler to $serviceName")
  }
  
  abstract fun saveInternal(handler: Any)
  
  abstract operator fun get(operation: String): Any?
}