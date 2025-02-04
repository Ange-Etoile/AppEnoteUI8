package com.example.appenote.View_Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appenote.Models.Question
import com.example.appenote.Repository.QuestionRepository
import com.example.appenote.Repository.UserRepository

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel(){
    private val _isProgressBarVisible = MutableLiveData<Boolean>(false)
    val isProgressBarVisible: LiveData<Boolean> get() = _isProgressBarVisible

    fun getmatricule(context: Context): String? {
        return UserRepository.getUserIdFromSharedPreferences(context)
    }
    fun saveAnswer(question:Question) {
        _isProgressBarVisible.value = true
        if (question != null) {
            questionRepository.saveQuestion(question,onResult={
                run {
                    _isProgressBarVisible.value = false
                }

            })
        }
    }

}