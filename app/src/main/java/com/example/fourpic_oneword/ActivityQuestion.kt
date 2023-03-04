package com.example.fourpic_oneword

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.fourpic_oneword.databinding.ActivityQuestionBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class ActivityQuestion : AppCompatActivity() {
    private var answersList = mutableListOf<TextView>()
    private var optionsList = mutableListOf<TextView>()
    private var currentIndex = 0
    private lateinit var currentQuestion: QuestionsData
    private var index = -1
    private var repeatedCount = 0
    private var questions = Constants.getQuestions()
    private lateinit var binding: ActivityQuestionBinding
    private var userAnswerList = mutableListOf<UserAnswer>()
    private lateinit var empty: UserAnswer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        empty = UserAnswer("", binding.tvLevel)


        var touch_img: Int = -1
        binding.ivPic1.setOnClickListener {
            touch_img = 1
            animationScaleUpImageView(1)
        }

        binding.ivPic2.setOnClickListener {
            touch_img = 2
            animationScaleUpImageView(2)
        }
        binding.ivPic3.setOnClickListener {
            touch_img = 3
            animationScaleUpImageView(3)
        }
        binding.ivPic4.setOnClickListener {
            touch_img = 4
            animationScaleUpImageView(4)
        }
        binding.bigImage.setOnClickListener {
            animationScaleDownImageView(touch_img)
        }


        binding.apply {

            btnBack.setOnClickListener {
                finish()
            }
            fillAnswerList()
            fillOptionList()


            tvOption1.setOnClickListener { setLetter(tvOption1) }
            tvOption2.setOnClickListener { setLetter(tvOption2) }
            tvOption3.setOnClickListener { setLetter(tvOption3) }
            tvOption4.setOnClickListener { setLetter(tvOption4) }
            tvOption5.setOnClickListener { setLetter(tvOption5) }
            tvOption6.setOnClickListener { setLetter(tvOption6) }
            tvOption7.setOnClickListener { setLetter(tvOption7) }
            tvOption8.setOnClickListener { setLetter(tvOption8) }
            tvOption9.setOnClickListener { setLetter(tvOption9) }
            tvOption10.setOnClickListener { setLetter(tvOption10) }
            tvOption11.setOnClickListener { setLetter(tvOption11) }
            tvOption12.setOnClickListener { setLetter(tvOption12) }


            tvAnswer1.setOnClickListener { removeLetter(tvAnswer1) }
            tvAnswer2.setOnClickListener { removeLetter(tvAnswer2) }
            tvAnswer3.setOnClickListener { removeLetter(tvAnswer3) }
            tvAnswer4.setOnClickListener { removeLetter(tvAnswer4) }
            tvAnswer5.setOnClickListener { removeLetter(tvAnswer5) }
            tvAnswer6.setOnClickListener { removeLetter(tvAnswer6) }
            tvAnswer7.setOnClickListener { removeLetter(tvAnswer7) }
            tvAnswer8.setOnClickListener { removeLetter(tvAnswer8) }

            setQuestions()


        }


    }

    @SuppressLint("SetTextI18n")
    private fun setQuestions() {
        binding.tvLevel.text = "${currentIndex + 1}"
        binding.ivPic1.setImageResource(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].images[0])
        binding.ivPic2.setImageResource(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].images[1])
        binding.ivPic3.setImageResource(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].images[2])
        binding.ivPic4.setImageResource(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].images[3])

        //mustLetters()

        answersList.forEach {
            it.text = ""
            it.visibility = View.GONE
        }
        repeat(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].answer.length) {
            answersList[it].visibility = View.VISIBLE
        }
        setOptionsLetters()


    }


    private fun setOptionsLetters() {
        binding.apply {
            val currentQuestion = Constants.getQuestions()[currentIndex % Constants.getQuestions().size]
            val optionLetters = mutableListOf<Char>()
            optionLetters.addAll(currentQuestion.answer.toList())

            repeat(12 - optionLetters.size) {
                optionLetters.add(Random.nextInt(65, 90).toChar())
            }
            optionLetters.shuffle()


            optionsList.forEachIndexed { index, textView ->
                textView.text = optionLetters[index].toString()
            }
//            for (i in 0 until optionLetters.size) {
//                answersList[i].text = optionLetters[i].toString()
//            }


        }
    }

    private fun setLetter(textView: TextView) {
        val currentQuestion = Constants.getQuestions()[currentIndex % Constants.getQuestions().size]
        val letter = textView.text.toString()
        if (letter.isNotEmpty() && userAnswerList.filter { it.letter != "" }.size != currentQuestion.answer.length) {
            val pair = UserAnswer(letter, textView)
            val emptyIndex = userAnswerList.indexOf(empty)
            if (emptyIndex == -1) {
                userAnswerList.add(pair)
            } else {
                userAnswerList[emptyIndex] = pair
            }
            textView.text = ""
            answersList[userAnswerList.indexOf(pair)].text = letter
        }

        if (userAnswerList.filter { it.letter != "" }.size == currentQuestion.answer.length) {
            var answer = ""
            userAnswerList.forEach {
                answer += it.letter
            }
            if (answer == Constants.getQuestions()[currentIndex % Constants.getQuestions().size].answer) {
                answersList.forEach {
                    it.isClickable = false
                }
                currentIndex ++
                binding.apply {
                    nextScreen.hideOrShow(true)
                    submitBtn.setOnClickListener {
                        it.isClickable = false
                        alphaAnimation(binding.nextScreen)

                    }

                }

            }
        }


    }

    private fun removeLetter(textView: TextView) {
        val letter = textView.text.toString()
        if (letter.isNotEmpty()) {
            val index = answersList.indexOf(textView)
            val pair = userAnswerList[index]
            textView.text = ""
            pair.textView.text = pair.letter
            userAnswerList[index] = empty
        }
    }

    private fun alphaAnimation(layoutt: View) {
        val animation1 = AlphaAnimation(1.0f, 0f)
        animation1.duration = 1000
        layoutt.startAnimation(animation1)
        submitFunc()
        Handler().postDelayed({
            binding.submitBtn.isClickable = true
        }, 5000)


    }


    private fun animationScaleUpImageView(id: Int) {
        val currentQuestion = Constants.getQuestions()[currentIndex % Constants.getQuestions().size]
        when (id) {
            1 -> {
                binding.bigImage.setImageResource(currentQuestion.images[id - 1])
                binding.bigImage.visibility = View.VISIBLE
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this, R.anim.scale_anim_pic1
                    )
                )

            }
            2 -> {
                binding.bigImage.setImageResource(currentQuestion.images[id - 1])
                binding.bigImage.visibility = View.VISIBLE
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this, R.anim.scale_anim_pic2
                    )
                )

            }
            3 -> {
                binding.bigImage.setImageResource(currentQuestion.images[id - 1])
                binding.bigImage.visibility = View.VISIBLE
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this, R.anim.scale_anim_pic3
                    )
                )

            }
            4 -> {
                binding.bigImage.setImageResource(currentQuestion.images[id - 1])
                binding.bigImage.visibility = View.VISIBLE
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this, R.anim.scale_anim_pic4
                    )
                )

            }
        }


    }

    private fun animationScaleDownImageView(id: Int) {
        when (id) {
            1 -> {
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.scale_down_pic1)

                )

            }
            2 -> {
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.scale_down_pic2)

                )

            }
            3 -> {
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.scale_down_pic3)

                )

            }
            4 -> {
                binding.bigImage.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.scale_down_pic4)

                )

            }

        }
        Handler().postDelayed({
            binding.bigImage.visibility = View.GONE
        }, 180)


    }

    private fun fillAnswerList() {
        binding.apply {
            answersList.add(tvAnswer1)
            answersList.add(tvAnswer2)
            answersList.add(tvAnswer3)
            answersList.add(tvAnswer4)
            answersList.add(tvAnswer5)
            answersList.add(tvAnswer6)
            answersList.add(tvAnswer7)
            answersList.add(tvAnswer8)
        }


    }

    private fun fillOptionList() {
        binding.apply {
            optionsList.add(tvOption1)
            optionsList.add(tvOption2)
            optionsList.add(tvOption3)
            optionsList.add(tvOption4)
            optionsList.add(tvOption5)
            optionsList.add(tvOption6)
            optionsList.add(tvOption7)
            optionsList.add(tvOption8)
            optionsList.add(tvOption9)
            optionsList.add(tvOption10)
            optionsList.add(tvOption11)
            optionsList.add(tvOption12)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun submitFunc() {
        binding.nextScreen.hideOrShow(false)
        when {
            index < questions.size - 1 -> {
                index++
            }
            else -> {
                index = 0
                repeatedCount++
            }
        }
        answersList.forEach {
            it.isClickable = true
        }

        currentQuestion = questions[index]
        userAnswerList.clear()
        setQuestions()
        fillAnswerList()


    }

    fun View.hideOrShow(boolean: Boolean) {
        if (boolean) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

    fun mustLetters() {
        answersList.forEach {
            it.visibility = View.GONE
        }

        repeat(Constants.getQuestions()[currentIndex % Constants.getQuestions().size].answer.length){
            answersList[it].visibility = View.VISIBLE
        }
    }
}