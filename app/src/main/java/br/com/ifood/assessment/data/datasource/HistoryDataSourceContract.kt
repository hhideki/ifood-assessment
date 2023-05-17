package br.com.ifood.assessment.data.datasource

interface HistoryDataSourceContract {

    fun addEntry(entry: String)
    fun getEntries(): List<String>

}