package com.example.appenote.Activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appenote.R
import com.example.appenote.Repository.ResetPasswordRepositoryImpl
import com.example.appenote.Repository.UserRepositoryImpl
import com.example.appenote.View_Models.RegisterViewModel
import com.example.appenote.View_Models.ResetPasswordViewModel
import com.example.appenote.databinding.ActivityResetPasswordBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class ResetPassword : AppCompatActivity() {
    enum class StrenghtLevel{
        WEAK,
        MEDIUM,
        STRONG,
        VERY_STRONG
    }
    private lateinit var mainBinding: ActivityResetPasswordBinding
    private lateinit var  viewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        mainBinding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val firestore = FirebaseFirestore.getInstance()
        val application = this.application

        val resetRepository = ResetPasswordRepositoryImpl(firestore, application)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ResetPasswordViewModel(resetRepository) as T
            }
        })[ResetPasswordViewModel::class.java]
        viewModel.isUppercaseValid.observe(this, Observer { isValid ->
            updateStatusUI(isValid,mainBinding.imgUppercase,mainBinding.textUppercase)
        })
        viewModel.isNumberValid.observe(this, Observer { isValid ->
            updateStatusUI(isValid,mainBinding.imgNumber,mainBinding.textNumber)
        })
        viewModel.isSpecialValid.observe(this, Observer { isValid ->
            updateStatusUI(isValid,mainBinding.imgSpecialChart,mainBinding.specialCharacter)
        })
        viewModel.isLengthValid.observe(this, Observer { isValid ->
            updateStatusUI(isValid,mainBinding.characters,mainBinding.textCharacters)
        })
        mainBinding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(password: CharSequence?, start: Int, before: Int, count: Int) {
                mainBinding.passwordRules.visibility = if(password.isNullOrEmpty()) View.GONE else View.VISIBLE
                password?.let { viewModel.validatePassword(it.toString()) }
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                // Demande au ViewModel de valider le mot de passe
                viewModel.validatePassword(password)
            }
        })
        mainBinding.confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainBinding.passwordRules.visibility = View.GONE
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        mainBinding.btnResetPwd.setOnClickListener {
            val password = mainBinding.editPassword.text.toString()
            val confirmPassword = mainBinding.confirmPassword.text.toString()

            if (password != confirmPassword) {
                mainBinding.confirmPassword.error  = "le mot de passe ne corespond pas"
            }
            if(password.isEmpty()){
                mainBinding.editPassword.error = "ce champs ne peux pas etre vide"
            }
            if(confirmPassword.isEmpty()){
                mainBinding.confirmPassword.error = "ce champs ne peux pas etre vide"
            }
            else {
                viewModel.isProgressBarVisible.observe(this, Observer {
                    isVisible->
                    run {
                        mainBinding.progressBar.visibility =
                            if (isVisible) View.VISIBLE else View.GONE
                    }
                })
                viewModel.updatepassword(password)
            }


        }
    }

    private fun validatePasswordConfirmation() {
        val password = mainBinding.editPassword.text.toString()
        val confirmPassword = mainBinding.confirmPassword.text.toString()

        if (password != confirmPassword) {
            mainBinding.confirmPassword.error  = "le mot de passe ne corespond pas"
        } else {
            mainBinding.btnResetPwd.isEnabled = true
        }
    }
    private fun updateStatusUI(
        condition:Boolean,
        imageView: ImageView,
        textView: TextView
    ){
        if(condition){
            imageView.setImageResource(R.drawable.baseline_check_24)
            imageView.setColorFilter(ContextCompat.getColor(this,R.color.very_strong))
            textView.setTextColor(ContextCompat.getColor(this,R.color.very_strong))
        }else{
            imageView.setImageResource(R.drawable.baseline_cancel_24)
            imageView.setColorFilter(ContextCompat.getColor(this,R.color.weak))
            textView.setTextColor(ContextCompat.getColor(this,R.color.weak))
        }
    }
}