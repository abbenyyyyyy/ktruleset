package com.dsl.ktruleset

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {
    override fun get(): RuleSet = RuleSet(
            "custom-rule-set",
            KclassNoteRule(),
            ExtendBaseRule(),
            AndroidLogRule()
    )
}