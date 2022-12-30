package ru.korobeynikov.p11junitrules

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class LoggingRule(val typeRule: String) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                println("starting $typeRule")
                base.evaluate()
                println("finished $typeRule")
            }
        }
    }
}