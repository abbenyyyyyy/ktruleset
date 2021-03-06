package com.dsl.ktruleset

import com.dsl.ktruleset.config.DisabledRulesCache
import com.dsl.ktruleset.config.I18nSupport
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

/**
 * Activity必须要继承BaseActivity或者Fragment必须要继承BaseFragment
 */
class ExtendBaseRule : Rule("kclass-extend-base-rules") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (DisabledRulesCache.disabledRulesList.contains(id)) return
        if (node.elementType == KtStubElementTypes.CLASS) {
            var isExtendActivity = false
            var isExtendFragment = false
            var isExtendDialog = false

            for (childNode in node.children()) {
                if (childNode.elementType == KtStubElementTypes.SUPER_TYPE_LIST) {
                    //继承与实现的类
                    for (minChild in childNode.children()) {
                        if (minChild.elementType == KtStubElementTypes.SUPER_TYPE_CALL_ENTRY) {
                            //继承的类
                            if (minChild.text == "Activity()") {
                                isExtendActivity = true
                            }
                            if (minChild.text == "Fragment()") {
                                isExtendFragment = true
                            }
                            if (minChild.text == "DialogFragment()" || minChild.text == "Dialog()") {
                                isExtendDialog = true
                            }
                            break
                        }
                    }
                }
            }
            if (isExtendActivity || isExtendFragment || isExtendDialog) {
                for (childNode in node.children()) {
                    if (childNode.elementType == ElementType.IDENTIFIER) {
                        //第一个标识符，是类名
                        if (isExtendActivity && childNode.text != "BaseActivity") {
                            emit(
                                childNode.startOffset,
                                I18nSupport.i18nStr("ExtendBaseRuleActivityEmit"),
                                false
                            )
                            break
                        }
                        if (isExtendFragment && childNode.text != "BaseFragment") {
                            emit(
                                childNode.startOffset,
                                I18nSupport.i18nStr("ExtendBaseRuleFragmentEmit"),
                                false
                            )
                            break
                        }
                        if (isExtendDialog && childNode.text != "BaseDialogFragment") {
                            emit(
                                childNode.startOffset,
                                I18nSupport.i18nStr("ExtendBaseRuleDialogFragmentEmit"),
                                false
                            )
                            break
                        }
                        break
                    }
                }
            }
        }
    }
}