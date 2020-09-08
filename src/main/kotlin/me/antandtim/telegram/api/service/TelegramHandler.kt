package me.antandtim.telegram.api.service

import me.antandtim.telegram.api.annotation.Command
import me.antandtim.telegram.api.annotation.Step
import me.antandtim.telegram.api.annotation.UpdateHandler
import me.antandtim.telegram.api.container.CommandContainer
import me.antandtim.telegram.api.container.StepContainer
import me.antandtim.telegram.api.domain.TelegramConnection
import me.antandtim.telegram.api.util.aopClass
import org.springframework.context.ApplicationContext
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

class TelegramHandler(
    context: ApplicationContext? = null,
    private val userStepService: UserStepService,
    private val commandContainer: CommandContainer = CommandContainer(),
    private val stepContainer: StepContainer = StepContainer()
) {
    
    init {
        context?.let { bindContextHandlers(it) }
    }
    
    fun <T : BotApiMethod<out Serializable>, R : Any?> handle(update: Update, send: (T) -> R) {
        update.message?.text?.let {
            commandContainer[update.message.text]?.let {
                it.aopClass().methods
                    .first { method -> method.isAnnotationPresent(UpdateHandler::class.java) }
                    .invoke(
                        it,
                        TelegramConnection(update, send, userStepService = userStepService)
                    )
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
                                TelegramConnection(
                                    update,
                                    send,
                                    step,
                                    userStepService = userStepService
                                )
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