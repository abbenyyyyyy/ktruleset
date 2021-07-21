package com.dsl.ktruleset.config

object DisabledRulesCache {
    val disabledRulesList = ThreadSafeEditorConfigCache().get().split(",")
}