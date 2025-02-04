package com.example.appenote.Activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appenote.Models.Question
import com.example.appenote.R
import com.example.appenote.Repository.QuestionRepositoryImpl
import com.example.appenote.View_Models.QuestionViewModel
import com.example.appenote.View_Models.RegisterViewModel
import com.example.appenote.databinding.ActivityQuestionRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRegister : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionRegisterBinding
    private lateinit var questionViewModel: QuestionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityQuestionRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val firestore = FirebaseFirestore.getInstance()
        val application = this.application
        val questionRepository = QuestionRepositoryImpl(firestore,application)
        questionViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuestionViewModel(questionRepository) as T
            }
        })[QuestionViewModel::class.java]

        binding.btnSend.setOnClickListener {
            val matricule = questionViewModel.getmatricule(applicationContext)!!
            val first_question = binding.editFirstQuestion.text.toString()
            val second_question = binding.editSndQuestion.text.toString()
            val last_question = binding.editLastQuestion.text.toString()
            val progress_bar = binding.progressBar
            questionViewModel.isProgressBarVisible.observe(this, Observer {
                isVisible->
                run {
                    progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
                }
            })
            val question = Question(matricule,first_question,second_question,last_question)
            if(binding.editFirstQuestion.text.toString().isEmpty()){
                binding.editFirstQuestion.error = "veuillez repondre a cette question"
            }
            if(binding.editSndQuestion.text.toString().isEmpty()){
                binding.editSndQuestion.error = "veuillez repondre a cette question"
            }
            if(binding.editLastQuestion.text.toString().isEmpty()){
                binding.editLastQuestion.error = "veuillez repondre a cette question"
            }else{
                questionViewModel.saveAnswer(question)
            }
        }
    }
}