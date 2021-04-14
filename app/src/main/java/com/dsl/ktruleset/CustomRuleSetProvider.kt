package com.dsl.ktruleset

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {
    override fun get(): RuleSet = RuleSet(
        "custom-rule-set",
        // 可以提供多个规则，现在只加了一个ktclass没有注释的规则。
        KclassNoteRule(),
    )
}