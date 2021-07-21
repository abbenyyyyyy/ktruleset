package com.dsl.ktruleset.config

object EmitLanguageCache {
    val emit_zh_CN = ThreadSafeEditorConfigCache().get(ThreadSafeEditorConfigCache.emitLanguage) == "zh_CN"
}