package br.com.ifood.assessment.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.ifood.assessment.databinding.FragmentCalculatorBinding
import br.com.ifood.assessment.presentation.viewaction.ViewAction
import br.com.ifood.assessment.presentation.viewmodel.CalculatorViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding
    private val viewModel: CalculatorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        setupEventListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    renderDisplay(state.display)
                }
            }
        }
    }

    private fun setupEventListeners() = with(binding) {
        btnNum1.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("1")) }
        btnNum2.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("2")) }
        btnNum3.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("3")) }
        btnNum4.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("4")) }
        btnNum5.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("5")) }
        btnNum6.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("6")) }
        btnNum7.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("7")) }
        btnNum8.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("8")) }
        btnNum9.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("9")) }
        btnNum0.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DigitTapped("0")) }

        btnDot.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DotTapped) }
        btnBackspace.setOnClickListener { viewModel.dispatchViewAction(ViewAction.BackspaceTapped) }
        btnClear.setOnClickListener { viewModel.dispatchViewAction(ViewAction.ClearTapped) }

        btnOperatorDivision.setOnClickListener { viewModel.dispatchViewAction(ViewAction.DivisionTapped) }
        btnOperatorMultiplication.setOnClickListener { viewModel.dispatchViewAction(ViewAction.MultiplicationTapped) }
        btnOperatorSubtraction.setOnClickListener { viewModel.dispatchViewAction(ViewAction.SubtractionTapped) }
        btnOperatorAddition.setOnClickListener { viewModel.dispatchViewAction(ViewAction.AdditionTapped) }
        btnOperatorEqual.setOnClickListener { viewModel.dispatchViewAction(ViewAction.EqualTapped) }
    }

    private fun renderDisplay(display: String) {
        binding.txtDisplay.text = display
    }

}