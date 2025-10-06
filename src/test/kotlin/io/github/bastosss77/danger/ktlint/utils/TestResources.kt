package io.github.bastosss77.danger.ktlint.utils

import java.io.File

object TestResources {
    private const val FOLDER = "reports"

    object Json {
        private const val FOLDER = "${TestResources.FOLDER}/json"

        val empty = loadFile("$FOLDER/empty.json")
        val notEmpty = loadFile("$FOLDER/not-empty.json")
        val malformed = loadFile("$FOLDER/malformed.json")
        val emptyIssue = loadFile("$FOLDER/empty-issue.json")
        val duplicated = loadFile("$FOLDER/duplicated.json")
    }

    object Xml {
        private const val FOLDER = "${TestResources.FOLDER}/xml"

        val notEmpty = loadFile("${FOLDER}/not-empty.xml")
        val empty = loadFile("${FOLDER}/empty.xml")
        val duplicated = loadFile("${FOLDER}/duplicated.xml")
        val malformed = loadFile("${FOLDER}/malformed.xml")
        val emptyIssue = loadFile("${FOLDER}/empty-issue.xml")
    }
}

internal fun loadFile(fileName: String): File = File(ClassLoader.getSystemResource(fileName).toURI())
