package ru.korobeynikov.p11junitrules

import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class UseRuleChain {

    @Rule
    @JvmField
    val chain: RuleChain = RuleChain.outerRule(LoggingRule("outer rule"))
        .around(LoggingRule("middle rule")).around(LoggingRule("inner rule"))

    @Test
    fun example() {
        assertTrue(true)
    }
}