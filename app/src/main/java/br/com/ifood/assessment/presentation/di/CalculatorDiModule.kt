package br.com.ifood.assessment.presentation.di

import br.com.ifood.assessment.data.datasource.HistoryDataSourceContract
import br.com.ifood.assessment.data.datasource.PlainTextHistoryDataSource
import br.com.ifood.assessment.data.repository.HistoryRepository
import br.com.ifood.assessment.domain.repository.HistoryRepositoryContract
import br.com.ifood.assessment.domain.usecase.BinaryCalculationUseCase
import br.com.ifood.assessment.domain.usecase.GetHistoryUseCase
import br.com.ifood.assessment.domain.usecase.WriteOperandHistoryUseCase
import br.com.ifood.assessment.domain.usecase.WriteOperatorHistoryUseCase
import br.com.ifood.assessment.presentation.DispatcherProvider
import br.com.ifood.assessment.presentation.viewmodel.CalculatorViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val calculatorDiModule = module {

    factory {
        DispatcherProvider(
            io = Dispatchers.IO,
            main = Dispatchers.Main,
            default = Dispatchers.Default,
            test = Dispatchers.Unconfined
        )
    }

    factory<HistoryDataSourceContract> { PlainTextHistoryDataSource(application = androidApplication()) }

    factory<HistoryRepositoryContract> { HistoryRepository(dataSource = get()) }

    factory { WriteOperandHistoryUseCase(repository = get()) }
    factory { WriteOperatorHistoryUseCase(repository = get()) }
    factory { GetHistoryUseCase(repository = get()) }
    factory { BinaryCalculationUseCase() }

    // region ViewModels
    viewModel {
        CalculatorViewModel(
            dispatcherProvider = get(),
            writeOperandHistoryUseCase = get(),
            writeOperatorHistoryUseCase = get(),
            getHistoryUseCase = get(),
            binaryCalculationUseCase = get()
        )
    }
    // endregion

}