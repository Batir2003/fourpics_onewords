package com.example.fourpic_oneword

import androidx.annotation.DrawableRes

data class QuestionsData(
 val id:Int,
 @DrawableRes val images: List<Int>,
 val answer:String

)
