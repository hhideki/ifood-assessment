package br.com.ifood.assessment.presentation.viewaction

sealed class ViewAction {

    data class DigitTapped(
        val digit: String
    ) : ViewAction()

    object DotTapped : ViewAction()
    object BackspaceTapped : ViewAction()
    object ClearTapped : ViewAction()

    object DivisionTapped : ViewAction()
    object MultiplicationTapped : ViewAction()
    object SubtractionTapped : ViewAction()
    object AdditionTapped : ViewAction()
    object EqualTapped : ViewAction()

}
