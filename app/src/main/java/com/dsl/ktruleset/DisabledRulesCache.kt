package com.dsl.ktruleset

object DisabledRulesCache {
    val disabledRulesList = ThreadSafeEditorConfigCache().get().split(",")
}