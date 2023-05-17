package br.com.ifood.assessment.domain.usecase

import br.com.ifood.assessment.domain.repository.HistoryRepositoryContract

class WriteOperandHistoryUseCase(
    private val repository: HistoryRepositoryContract
) {

    operator fun invoke(operand: String) {
        repository.add(operand)
    }

}