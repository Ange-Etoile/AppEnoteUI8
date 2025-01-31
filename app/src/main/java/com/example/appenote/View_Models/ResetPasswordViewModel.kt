package com.example.appenote.View_Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appenote.Activities.ResetPassword
import com.example.appenote.Models.PasswordStrength
import com.example.appenote.Models.PasswordValidator
import com.example.appenote.Repository.ResetPasswordRepository

class ResetPasswordViewModel(private val resetPasswordRepository: ResetPasswordRepository) : ViewModel() {

    // LiveData pour chaque critère de validation du mot de passe
    val isUppercaseValid = MutableLiveData<Boolean>()
    val isNumberValid = MutableLiveData<Boolean>()
    val isSpecialValid = MutableLiveData<Boolean>()
    val isLengthValid = MutableLiveData<Boolean>()

    // LiveData pour l'icône générale de la force du mot de passe (si tu veux afficher un indicateur global de force)
    val passwordStrength = MutableLiveData<String>()

    // Méthode pour vérifier les critères de validation du mot de passe
    fun validatePassword(password: String) {
        isUppercaseValid.value = password.any { it.isUpperCase() }
        isNumberValid.value = password.any { it.isDigit() }
        isSpecialValid.value = password.any { !it.isLetterOrDigit() }
        isLengthValid.value = password.length >= 8

        // Indicateur général de la force du mot de passe (optionnel)
        passwordStrength.value = when {
            isLengthValid.value == true && isUppercaseValid.value == true &&
                    isNumberValid.value == true && isSpecialValid.value == true -> "Strong"
            else -> "Weak"
        }

    }
    fun updatepassword(password: String){
        if( isLengthValid.value == true && isUppercaseValid.value == true &&
            isNumberValid.value == true && isSpecialValid.value == true){
            resetPasswordRepository.upadeteUser(password)
        }
    }

}

