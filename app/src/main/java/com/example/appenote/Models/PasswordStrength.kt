package com.example.appenote.Models

import com.example.appenote.Activities.ResetPassword
import com.example.appenote.R

data class PasswordStrength(val hasUpperCase: Boolean, val hasDigit: Boolean, val hasSpecialChar: Boolean, val hasCharacter: Boolean)

class PasswordValidator {
    fun validateStrength(password: String): PasswordStrength {
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.contains(Regex("[!@\$%^&*()_\\-=\\[\\]{};':\",./<>?\\\\|]"))
        val hasCharacter = password.length >= 8

        return PasswordStrength(hasUpperCase, hasDigit, hasSpecialChar, hasCharacter)
    }

    fun calculateStrengthLevel(passwordStrength: PasswordStrength, password: String): Pair<ResetPassword.StrenghtLevel, Int> {
        val (hasUpperCase, hasDigit, hasSpecialChar, hasCharacter) = passwordStrength
        val strengthLevel: ResetPassword.StrenghtLevel
        val strengthColor: Int

        when (password.length) {
            in 0..7 -> {
                strengthLevel = ResetPassword.StrenghtLevel.WEAK
                strengthColor = R.color.weak
            }
            in 8..10 -> {
                strengthLevel = if (hasUpperCase || hasDigit || hasCharacter || hasSpecialChar) {
                    ResetPassword.StrenghtLevel.MEDIUM
                } else {
                    ResetPassword.StrenghtLevel.WEAK
                }
                strengthColor = R.color.medium
            }
            in 11..16 -> {
                strengthLevel = if (hasCharacter && hasUpperCase) {
                    ResetPassword.StrenghtLevel.STRONG
                } else if (hasUpperCase || hasDigit || hasCharacter || hasSpecialChar) {
                    ResetPassword.StrenghtLevel.MEDIUM
                } else {
                    ResetPassword.StrenghtLevel.WEAK
                }
                strengthColor = R.color.strong
            }
            else -> {
                strengthLevel = if (hasUpperCase && hasDigit && hasCharacter && hasSpecialChar) {
                    ResetPassword.StrenghtLevel.VERY_STRONG
                } else {
                    ResetPassword.StrenghtLevel.MEDIUM
                }
                strengthColor = R.color.very_strong
            }
        }

        return strengthLevel to strengthColor
    }
}
