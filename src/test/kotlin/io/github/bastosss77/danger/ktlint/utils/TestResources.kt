package io.github.bastosss77.danger.ktlint.utils

import java.io.File

object TestResources {
    private const val FOLDER = "reports"

    object Json {
        private const val FOLDER = "${TestResources.FOLDER}/json"

        val empty = loadFile("$FOLDER/empty.json")
        val notEmpty = loadFile("$FOLDER/not-empty.json")
        val notEmpty2 = loadFile("$FOLDER/not-empty2.json")
        val malformed = loadFile("$FOLDER/malformed.json")
        val emptyIssue = loadFile("$FOLDER/empty-issue.json")
        val duplicated = loadFile("$FOLDER/duplicated.json")
    }
}

internal fun loadFile(fileName: String): File = File(ClassLoader.getSystemResource(fileName).toURI())
