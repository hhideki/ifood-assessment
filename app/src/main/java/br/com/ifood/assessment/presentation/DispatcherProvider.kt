package br.com.ifood.assessment.presentation

import kotlinx.coroutines.CoroutineDispatcher

class DispatcherProvider(
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val test: CoroutineDispatcher
)