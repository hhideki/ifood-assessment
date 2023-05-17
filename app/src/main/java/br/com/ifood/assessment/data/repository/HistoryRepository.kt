package br.com.ifood.assessment.data.repository

import br.com.ifood.assessment.data.datasource.HistoryDataSourceContract
import br.com.ifood.assessment.domain.repository.HistoryRepositoryContract

class HistoryRepository(
    private val dataSource: HistoryDataSourceContract
) : HistoryRepositoryContract {

    override fun add(entry: String) {
        dataSource.addEntry(entry)
    }

    override fun getData(): List<String> = dataSource.getEntries()

}