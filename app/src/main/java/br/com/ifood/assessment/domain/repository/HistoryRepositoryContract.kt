package br.com.ifood.assessment.domain.repository

interface HistoryRepositoryContract {

    fun add(entry: String)
    fun getData(): List<String>

}