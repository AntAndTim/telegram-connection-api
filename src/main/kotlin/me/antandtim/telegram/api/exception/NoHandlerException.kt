package me.antandtim.telegram.api.exception

import me.antandtim.telegram.api.annotation.UpdateHandler
import java.lang.RuntimeException

class NoHandlerException :
  RuntimeException("The handler must have a method annotated with ${UpdateHandler::class.simpleName}")