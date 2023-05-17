package br.com.ifood.assessment.domain.usecase

import br.com.ifood.assessment.domain.repository.HistoryRepositoryContract

class GetHistoryUseCase(
    private val repository: HistoryRepositoryContract
) {

    suspend operator fun invoke(): List<String> = repository.getData()

}