package ru.korobeynikov.p11junitrules

import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class UseRuleChain {

    @get:Rule
    val chain: RuleChain = RuleChain.outerRule(DataSourceRule("1"))
        .around(DataSourceRule("2")).around(DataSourceRule("3"))

    @Test
    fun example() {
        assertTrue(true)
    }
}