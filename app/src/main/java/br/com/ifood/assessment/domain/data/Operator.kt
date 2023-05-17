package br.com.ifood.assessment.domain.data

enum class Operator(val display: String) {
    NONE(""),
    DIVISION("/"),
    MULTIPLICATION("x"),
    SUBTRACTION("-"),
    ADDITITION("+"),
    EQUAL("="),
}