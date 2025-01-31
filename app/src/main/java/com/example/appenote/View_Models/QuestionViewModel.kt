package com.example.appenote.View_Models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appenote.Models.Question
import com.example.appenote.Repository.QuestionRepository
import com.example.appenote.Repository.UserRepository

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel(){
    val user_matriculeValidate = MutableLiveData<String>()
    val first_questionValidate = MutableLiveData<String>()

    fun getmatricule(context: Context): String? {
        return UserRepository.getUserIdFromSharedPreferences(context)
    }
    fun saveAnswer(question:Question) {
        if (question != null) {
            questionRepository.saveQuestion(question)
        }
    }

}