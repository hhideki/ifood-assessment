package br.com.ifood.assessment

import android.app.Application
import br.com.ifood.assessment.presentation.di.calculatorDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class CalculatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CalculatorApplication)
            loadKoinModules(calculatorDiModule)
        }
    }
}