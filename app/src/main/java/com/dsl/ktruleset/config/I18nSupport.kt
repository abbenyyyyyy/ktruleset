package com.dsl.ktruleset.config

import org.jetbrains.annotations.PropertyKey
import java.util.ResourceBundle
import java.util.Locale

/**
 * 国际化，参考 jetbrains 的做法
 * https://www.jetbrains.com/help/idea/hard-coded-string-literals.html
 */
object I18nSupport {
    private const val resourceBundleName = "ktlint"
    private val editorConfigEmitLanguage = ThreadSafeEditorConfigCache().get(ThreadSafeEditorConfigCache.emitLanguage)

    private val bundle = ResourceBundle.getBundle(
        resourceBundleName,
        if (editorConfigEmitLanguage.isNotEmpty()) Locale(editorConfigEmitLanguage) else Locale.getDefault()
    )

    fun i18nStr(@PropertyKey(resourceBundle = resourceBundleName) key: String): String {
        return bundle.getString(key)
    }
}