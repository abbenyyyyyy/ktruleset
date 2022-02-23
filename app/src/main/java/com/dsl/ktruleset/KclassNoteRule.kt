package com.dsl.ktruleset

import com.dsl.ktruleset.config.DisabledRulesCache
import com.dsl.ktruleset.config.I18nSupport
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.kdoc.psi.api.KDocElement
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

class KclassNoteRule : Rule("kclass-note-rules") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (DisabledRulesCache.disabledRulesList.contains(id)) return
        if (node.elementType == KtStubElementTypes.CLASS) {
            var isActivity = false
            var isFragment = false
            for (childNode in node.children()) {
                if (childNode.elementType == KtStubElementTypes.SUPER_TYPE_LIST) {
                    if (childNode.text.contains("BaseActivity")) {
                        isActivity = true
                        break
                    }
                    if (childNode.text.contains("BaseFragment")) {
                        isFragment = true
                        break
                    }
                }
            }
            if (isActivity || isFragment) {
                if (node.firstChildNode.psi !is KDocElement) {
                    emit(
                        node.firstChildNode.startOffset,
                        if (isActivity) {
                            I18nSupport.i18nStr("KclassNoteRuleActivityEmit")
                        } else {
                            I18nSupport.i18nStr("KclassNoteRuleFragmentEmit")
                        },
                        false
                    )
                }
            }
        }
    }
}