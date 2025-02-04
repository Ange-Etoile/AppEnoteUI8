package com.example.appenote.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appenote.R
import com.example.appenote.Repository.UserRepositoryImpl
import com.example.appenote.View_Models.RegisterViewModel
import com.example.appenote.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.initialize


class Register : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val firestore = FirebaseFirestore.getInstance()
        val application = this.application

        // Créer l'instance de UserRepositoryImpl
        val userRepository = UserRepositoryImpl(firestore, application,applicationContext)

        //  Créer le ViewModel en lui passant le UserRepository
        registerViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(userRepository) as T
            }
        })[RegisterViewModel::class.java]
        binding.btnSignUp.setOnClickListener {
            val nom = binding.editTextNom.text.toString()
            val prenom = binding.editTextPrenom.text.toString()
            val matricule = binding.editTextMatricule.text.toString()
            val password = binding.editTextPassword.text.toString()
            if(nom.isEmpty()){
                binding.editTextNom.error = "veuillez entrer votre nom"
            }
            if(prenom.isEmpty()){
                binding.editTextPrenom.error = "veuillez entrer votre prenom"
            }
            if(matricule.isEmpty()){
                binding.editTextMatricule.error = "veuillez entrer votre matricule"
            }
            if(password.isEmpty()){
                binding.editTextPassword.error = "veuillez entrer votre password"
            }
            else{
                registerViewModel.registerUser(nom,prenom,matricule,password)
                val intent = Intent(this, QuestionRegister::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}