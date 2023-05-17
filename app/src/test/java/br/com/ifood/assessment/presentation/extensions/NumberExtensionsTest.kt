package br.com.ifood.assessment.presentation.extensions

import br.com.ifood.assessment.presentation.extension.hasDecimals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class NumberExtensionsTest {

    @Test
    fun `Double hasDecimals should return true when the number has decimal digits`() {
        assertThat(123.456.hasDecimals(), `is`(true))
        assertThat(0.1.hasDecimals(), `is`(true))
        assertThat((-0.1).hasDecimals(), `is`(true))
        assertThat((-123.456).hasDecimals(), `is`(true))
        assertThat(0.00000000000000001.hasDecimals(), `is`(true))
        assertThat((-0.00000000000000001).hasDecimals(), `is`(true))
    }

    @Test
    fun `Double hasDecimals should return false when the number has no decimal digits`() {
        assertThat(123.0.hasDecimals(), `is`(false))
        assertThat(0.000000.hasDecimals(), `is`(false))
        assertThat(0.toDouble().hasDecimals(), `is`(false))
        assertThat(1.toDouble().hasDecimals(), `is`(false))
    }

}