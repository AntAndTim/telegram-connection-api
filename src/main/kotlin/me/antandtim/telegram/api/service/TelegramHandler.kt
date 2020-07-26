package me.antandtim.telegram.api.service

import org.springframework.context.ApplicationContext
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import me.antandtim.telegram.api.annotation.Command
import me.antandtim.telegram.api.annotation.Step
import me.antandtim.telegram.api.annotation.UpdateHandler
import me.antandtim.telegram.api.container.CommandContainer
import me.antandtim.telegram.api.container.StepContainer
import me.antandtim.telegram.api.domain.TelegramConnection
import me.antandtim.telegram.api.util.aopClass

class TelegramHandler(
  context: ApplicationContext? = null,
  private val userStepService: UserStepService,
  private val commandContainer: CommandContainer = CommandContainer(),
  private val stepContainer: StepContainer = StepContainer()
) {
  
  init {
    context?.let { bindContextHandlers(it) }
  }
  
  fun handle(update: Update, sendMessage: (SendMessage) -> Unit) {
    update.message?.text?.let {
      commandContainer[update.message.text]?.let {
        it.aopClass().methods
          .first { method -> method.isAnnotationPresent(UpdateHandler::class.java) }
          .invoke(it, TelegramConnection(update, sendMessage, userStepService = userStepService))
        return
      }
    }
    
    update.message?.chatId?.let {
      userStepService.getStep(it)?.let { userStep ->
        userStep.step?.let { step ->
          stepContainer[step]?.let { stepHandler ->
            stepHandler.aopClass().methods
              .first { method -> method.isAnnotationPresent(UpdateHandler::class.java) }
              .invoke(
                stepHandler,
                TelegramConnection(update, sendMessage, step, userStepService = userStepService)
              )
            return
          }
        }
      }
    }
  }
  
  /**
   * Method is used for binding commands to handler
   * */
  private fun bindContextHandlers(context: ApplicationContext) {
    val commands = context
      .getBeansWithAnnotation(Command::class.java)
      .map(Map.Entry<String, Any>::value)
    
    val steps = context
      .getBeansWithAnnotation(Step::class.java)
      .map(Map.Entry<String, Any>::value)
    
    commands.forEach { commandContainer.add(it) }
    steps.forEach { stepContainer.add(it) }
  }
}