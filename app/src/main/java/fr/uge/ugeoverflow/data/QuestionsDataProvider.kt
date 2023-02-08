package fr.uge.ugeoverflow.data

import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.model.*
import java.util.*

object QuestionsDataProvider {

    val questionLists = listOf(
        QuestionBuilder.builder().title("How to make a great R reproducible example")
            .tags(setOf(Tag(UUID.randomUUID(),TAG_TYPE.spring),Tag(UUID.randomUUID(),TAG_TYPE.javascript))).build(),
        QuestionBuilder.builder().title("What is a NullPointerException, and how do I fix it?")
            .tags(mutableSetOf(Tag(UUID.randomUUID(),TAG_TYPE.spring),Tag(UUID.randomUUID(),TAG_TYPE.java))).build()

    )


}