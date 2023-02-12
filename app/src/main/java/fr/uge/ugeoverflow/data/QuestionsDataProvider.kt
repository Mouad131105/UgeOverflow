package fr.uge.ugeoverflow.data

import fr.uge.ugeoverflow.model.*
import java.util.*

object QuestionsDataProvider {

    val questionLists = listOf(
        QuestionBuilder.builder().title("How to make a great R reproducible example")
            .tags(mutableSetOf(Tag(UUID.randomUUID(),TAG_TYPE.css),Tag(UUID.randomUUID(),TAG_TYPE.java))).
            answers(mutableSetOf(Answer(mutableSetOf(Vote(VOTE_TYPE.UPVOTE,User(UUID.randomUUID()
                ,"Issam","GRINI","Grinio","issamgrn1@gmail.com",
                AddressBuilder.builder().street("avenue président wilson").city("Montreuil").country("France").build()))),
                ContentBuilder.builder().
                text(" reproduce the problem on another machine by simply copying and pasting it.").build()))).build(),

        QuestionBuilder.builder().title("What is a NullPointerException, and how do I fix it?")
            .tags(mutableSetOf(Tag(UUID.randomUUID(),TAG_TYPE.spring),Tag(UUID.randomUUID(),TAG_TYPE.javascript))).
            answers(mutableSetOf(Answer(mutableSetOf(Vote(VOTE_TYPE.UPVOTE,User(UUID.randomUUID()
                ,"Toto","TATA","toto_dev","toto@gmail.com",
                AddressBuilder.builder().street("avenue président wilson").city("Montreuil").country("France").build()))),
                ContentBuilder.builder().
                text("our code should exactly reproduce the problem on another machine by simply copying and pasting it.").build()))).build(),

        QuestionBuilder.builder().title("removing duplicates of second column of csv via sort")
            .tags(mutableSetOf(Tag(UUID.randomUUID(),TAG_TYPE.dev),Tag(UUID.randomUUID(),TAG_TYPE.linux))).
            answers(mutableSetOf(Answer(mutableSetOf(Vote(VOTE_TYPE.DOWNVOTE,User(UUID.randomUUID()
                ,"Feri","Gabriel","gab","gab@gmail.com",
            AddressBuilder.builder().street("avenue président wilson").city("Montreuil").country("France").build()))),
            ContentBuilder.builder().
            text("Combined with the minimal data (see above)").build()))).build()

    )


}