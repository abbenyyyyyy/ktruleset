package com.dsl.ktruleset.config

import org.ec4j.core.PropertyTypeRegistry
import org.ec4j.core.Resource
import org.ec4j.core.model.EditorConfig
import org.ec4j.core.model.Version
import org.ec4j.core.parser.EditorConfigModelHandler
import org.ec4j.core.parser.EditorConfigParser
import org.ec4j.core.parser.ErrorHandler
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class ThreadSafeEditorConfigCache {

    companion object{
        const val disabledRulesName = "disabled_rules"
        const val emitLanguage = "ktlint_language"
    }

    private val editorConfigFilePath = "../.editorconfig"

    private val readWriteLock = ReentrantReadWriteLock()
    private val inMemoryMap = HashMap<String, String>()
    private var editorConfigCache: EditorConfig? = null

    fun get(key: String = disabledRulesName): String {
        readWriteLock.read {
            return inMemoryMap[key]
                ?: readWriteLock.write {
                    val result = getValutFromEditorConfig(key)
                    inMemoryMap[key] = result
                    result
                }
        }
    }

    private fun getValutFromEditorConfig(key: String): String {
        if (checkEditorConfigFileExists()) {
            if (editorConfigCache == null) loaderEditorConfig()
            for (section in editorConfigCache!!.sections) {
                for (property in section.properties.values) {
                    if (property.name == key) {
                        return property.sourceValue
                    }
                }
            }
        }
        return ""
    }

    private fun loaderEditorConfig() {
        val editorConfigFile = Paths.get(editorConfigFilePath)
        val parser = EditorConfigParser.builder().build()
        val handler = EditorConfigModelHandler(PropertyTypeRegistry.default_(), Version.CURRENT)
        parser.parse(
            Resource.Resources.ofPath(editorConfigFile, StandardCharsets.UTF_8),
            handler,
            ErrorHandler.THROW_SYNTAX_ERRORS_IGNORE_OTHERS
        )
        editorConfigCache = handler.editorConfig
    }

    private fun checkEditorConfigFileExists(): Boolean {
        val file = File(editorConfigFilePath)
        return file.exists()
    }
}