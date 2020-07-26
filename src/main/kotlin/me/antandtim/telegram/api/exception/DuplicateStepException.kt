package me.antandtim.telegram.api.exception

class DuplicateStepException(stepName: String) :
  RuntimeException("There must be only one step with name: $stepName")