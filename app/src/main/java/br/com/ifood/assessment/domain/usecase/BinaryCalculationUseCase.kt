package br.com.ifood.assessment.domain.usecase

import br.com.ifood.assessment.domain.data.Operator
import java.math.BigDecimal

class BinaryCalculationUseCase {

    suspend operator fun invoke(
        operator: Operator,
        leftHandOperand: BigDecimal,
        rightHandOperand: BigDecimal
    ): BigDecimal = when (operator) {
        Operator.DIVISION -> leftHandOperand.divide(rightHandOperand)
        Operator.MULTIPLICATION -> leftHandOperand.multiply(rightHandOperand)
        Operator.SUBTRACTION -> leftHandOperand.subtract(rightHandOperand)
        Operator.ADDITITION -> leftHandOperand.add(rightHandOperand)
        else -> leftHandOperand
    }

}