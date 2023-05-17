package br.com.ifood.assessment.domain.usecase

import br.com.ifood.assessment.domain.data.Operator
import br.com.ifood.assessment.domain.repository.HistoryRepositoryContract

class WriteOperatorHistoryUseCase(
    private val repository: HistoryRepositoryContract
) {

    suspend operator fun invoke(operator: Operator) {
        repository.add(operator.display)
    }

}