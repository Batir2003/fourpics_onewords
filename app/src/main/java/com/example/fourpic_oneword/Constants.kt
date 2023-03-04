package com.example.fourpic_oneword

object Constants {
    fun getQuestions(): List<QuestionsData> {
        val question = mutableListOf<QuestionsData>()
        question.add(
            QuestionsData(
                id = 0,
                images = listOf(
                    R.drawable.photo1_1,
                    R.drawable.photo1_2,
                    R.drawable.photo1_3,
                    R.drawable.photo1_4
                ),
                answer = "COLD"
            )
        )

        question.add(
            QuestionsData(
                id = 1,
                images = listOf(
                    R.drawable.photo2_1,
                    R.drawable.photo2_2,
                    R.drawable.photo2_3,
                    R.drawable.photo2_4
                ),
                answer = "ICE"
            )
        )

        question.add(
            QuestionsData(
                id = 2,
                images = listOf(
                    R.drawable.photo3_1,
                    R.drawable.photo3_2,
                    R.drawable.photo3_3,
                    R.drawable.photo3_4
                ),
                answer = "ISLAMBEK"
            )
        )

        question.add(
            QuestionsData(
                id = 3,
                images = listOf(
                    R.drawable.photo4_1,
                    R.drawable.photo4_2,
                    R.drawable.photo4_3,
                    R.drawable.photo4_4
                ),
                answer = "DAMIR"
            )
        )

        question.add(
            QuestionsData(
                id = 4,
                images = listOf(
                    R.drawable.photo5_1,
                    R.drawable.photo5_2,
                    R.drawable.photo5_3,
                    R.drawable.photo5_4
                ),
                answer = "BATIRBAY"
            )
        )
        return question
    }

}