package br.com.ifood.assessment.data.datasource

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class PlainTextHistoryDataSource(
    private val application: Application
) : HistoryDataSourceContract {

    private val writer = OutputStreamWriter(application.openFileOutput(FILE_NAME, Context.MODE_APPEND))
    private val reader = InputStreamReader(application.openFileInput(FILE_NAME))

    override fun addEntry(entry: String) {
        Log.d("IFOOD", "Writing entry: [$entry]")
        writer.write(entry)
        writer.flush()
    }

    override fun getEntries(): List<String> {
        return listOf()
    }

    private companion object {
        const val FILE_NAME = "history.log"
    }

}