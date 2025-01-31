package com.example.appenote.View_Models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.appenote.Repository.UserRepository


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(name: String, firstname: String, matricule: String,  password: String) {
        // Validation des données (à implémenter)
        if (name.isNotEmpty() && firstname.isNotEmpty() && matricule.isNotEmpty() && password.isNotEmpty()){


            userRepository.registerUser(name, firstname, matricule, password)
        }
    }
}