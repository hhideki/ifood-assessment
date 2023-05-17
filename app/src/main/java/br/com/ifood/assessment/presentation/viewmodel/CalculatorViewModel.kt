package br.com.ifood.assessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ifood.assessment.domain.data.Operator
import br.com.ifood.assessment.domain.usecase.BinaryCalculationUseCase
import br.com.ifood.assessment.domain.usecase.GetHistoryUseCase
import br.com.ifood.assessment.domain.usecase.WriteOperandHistoryUseCase
import br.com.ifood.assessment.domain.usecase.WriteOperatorHistoryUseCase
import br.com.ifood.assessment.presentation.DispatcherProvider
import br.com.ifood.assessment.presentation.viewaction.ViewAction
import br.com.ifood.assessment.presentation.viewstate.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.LinkedList
import java.util.Queue

class CalculatorViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val writeOperandHistoryUseCase: WriteOperandHistoryUseCase,
    private val writeOperatorHistoryUseCase: WriteOperatorHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val binaryCalculationUseCase: BinaryCalculationUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var integerDigits = StringBuilder(MAX_INTEGER_DIGITS)
    private var decimalDigits = StringBuilder(MAX_PRECISION)
    private var isTypingDecimals: Boolean = false

    private val operandQueue: Queue<BigDecimal> = LinkedList()
    private val operatorQueue: Queue<Operator> = LinkedList(listOf(Operator.NONE))

    fun dispatchViewAction(action: ViewAction) {
        when (action) {
            is ViewAction.DigitTapped -> handleDigitTapped(action.digit)
            ViewAction.DotTapped -> handleDotTapped()
            ViewAction.BackspaceTapped -> handleBackspaceTapped()
            ViewAction.ClearTapped -> handleClearTapped()
            ViewAction.DivisionTapped -> handleOperatorTapped(Operator.DIVISION)
            ViewAction.MultiplicationTapped -> handleOperatorTapped(Operator.MULTIPLICATION)
            ViewAction.SubtractionTapped -> handleOperatorTapped(Operator.SUBTRACTION)
            ViewAction.AdditionTapped -> handleOperatorTapped(Operator.ADDITITION)
            ViewAction.EqualTapped -> handleEqualTapped()
        }
    }

    private fun handleDigitTapped(digit: String) = runOnIo {
        if (canAddIntegerDigit()) {
            integerDigits.append(digit)
        } else if (canAddDecimalDigit()) {
            decimalDigits.append(digit)
        }

        postUpdatedDisplay()
    }

    private fun canAddIntegerDigit(): Boolean =
        (!isTypingDecimals && integerDigits.length < MAX_INTEGER_DIGITS)

    private fun canAddDecimalDigit(): Boolean =
        (isTypingDecimals && decimalDigits.length < MAX_PRECISION)

    private fun handleDotTapped() = runOnIo {
        isTypingDecimals = true
        postUpdatedDisplay()
    }

    private fun handleBackspaceTapped() = runOnIo {
        if (isTypingDecimals) {
            if (decimalDigits.isEmpty()) {
                isTypingDecimals = false
            } else {
                decimalDigits = decimalDigits.deleteAt(decimalDigits.length - 1)
            }
        } else if (!integerDigits.isEmpty()) {
            integerDigits = integerDigits.deleteAt(integerDigits.length - 1)
        }

        postUpdatedDisplay()
    }

    private fun handleClearTapped() = runOnIo {
        reset()
        postUpdatedDisplay()
    }

    private fun reset() {
        integerDigits.clear()
        decimalDigits.clear()
    }

    private fun getFormattedDisplayValue(): String =
        if (decimalDigits.isNotEmpty()) {
            "$integerDigits.$decimalDigits"
        } else if (integerDigits.isNotEmpty()) {
            if (isTypingDecimals) {
                "$integerDigits."
            } else {
                "$integerDigits"
            }
        } else {
            "0"
        }

    private fun getCurrentOperand(): BigDecimal = getFormattedDisplayValue().toBigDecimal()

    private fun handleOperatorTapped(operator: Operator) = runOnIo {
        val operand = getCurrentOperand()
        operandQueue.add(operand)
        operatorQueue.add(operator)
        writeOperatorHistoryUseCase(operator)
        writeOperandHistoryUseCase(operand.toString())
        reset()
        postUpdatedDisplay()
    }

    private fun handleEqualTapped() = runOnIo {
        val lastOperand = getCurrentOperand()
        operandQueue.add(lastOperand)
        writeOperandHistoryUseCase(lastOperand.toString())

        var result = BigDecimal.ZERO

        (0.until(operandQueue.size)).forEach { _ ->
            val operand = operandQueue.remove()
            val operator = operatorQueue.remove()
            result = binaryCalculationUseCase(operator, result, operand)
        }

        writeOperatorHistoryUseCase(Operator.EQUAL)
        writeOperandHistoryUseCase(result.toString())
        postUpdatedDisplay(result)
    }

    // region Post values
    private fun postUpdatedDisplay() {
        _viewState.update { state ->
            state.copy(
                display = getFormattedDisplayValue()
            )
        }
    }

    private fun postUpdatedDisplay(value: BigDecimal) {
        val formatter = NumberFormat.getNumberInstance()
        _viewState.update { state ->
            state.copy(
                display = value.toString()
            )
        }
    }
    // endregion

    private fun runOnIo(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(dispatcherProvider.io) { block() }

    companion object {
        private const val MAX_INTEGER_DIGITS = 8
        private const val MAX_PRECISION = 5
    }

}