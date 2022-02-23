package com.dsl.ktruleset

import com.dsl.ktruleset.config.DisabledRulesCache
import com.dsl.ktruleset.config.I18nSupport
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

class AndroidLogRule : Rule("kclass-android-log-rules") {
    @Suppress("UNREACHABLE_CODE")
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (DisabledRulesCache.disabledRulesList.contains(id)) return
        var usedAndroidLog = false
        if (node.elementType == ElementType.IMPORT_LIST) {
            val children = node.getChildren(null)
            if (children.isNotEmpty()) {
                val imports = children.filter {
                    it.elementType == ElementType.IMPORT_DIRECTIVE ||
                            it.psi is PsiWhiteSpace && it.textLength > 1 // also collect empty lines, that are represented as "\n\n"
                }
                val sortedImports = imports
                    .asSequence()
                    .filter { it.psi !is PsiWhiteSpace } // sorter expects KtImportDirective, whitespaces are inserted afterwards
                    .map { it.psi as KtImportDirective }
                    .distinctBy { if (it.aliasName != null) it.text.substringBeforeLast(it.aliasName!!) else it.text } // distinguish by import path w/o aliases
                    .map { it.node } // transform back to ASTNode in order to operate over its method (addChild)
                for (inportNode in sortedImports) {
                    if (inportNode.text == "import android.util.Log") {
                        usedAndroidLog = true
                        break
                    }
                }
            }
            if (usedAndroidLog) {
                val rootNode = node.treeParent
                for (childRootNode in rootNode.children()) {
                    if (childRootNode.elementType == KtStubElementTypes.OBJECT_DECLARATION || childRootNode.elementType == KtStubElementTypes.CLASS) {
                        for (childNode in childRootNode.children()) {
                            if (childNode.elementType == ElementType.IDENTIFIER) {
                                //第一个标识符，是类名
                                if (childNode.text != "DebugLog") {
                                    emit(
                                        childNode.startOffset,
                                        I18nSupport.i18nStr("AndroidLogRuleEmit"),
                                        false
                                    )
                                    break
                                }
                                break
                            }
                        }
                        break
                    }
                }
            }
        }
    }
}