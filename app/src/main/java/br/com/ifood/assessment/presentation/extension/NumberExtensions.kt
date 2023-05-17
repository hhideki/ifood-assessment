package br.com.ifood.assessment.presentation.extension

internal fun Double.hasDecimals(): Boolean = (this % 1 != 0.0)