package com.example.appenote.Activity

import UserViewModelFactory
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appenote.Repository.UserRepository
import com.example.appenote.ViewModels.UserViewModele
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.activity.viewModels
import com.example.appenote.R
import com.example.appenote.Utilitaire.Preference

class LoginActivity : AppCompatActivity() {

    //Declaration des view modele
    private val userViewModele: UserViewModele by viewModels {
        UserViewModelFactory(UserRepository())
    }
    // Definition de la classe pour gerer les sharedPreferences
    private lateinit var preference : Preference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //initialise la preference
        preference = Preference(this, "MyAppPreferences")

        //Declaration des variables a utiliser
        val btnlogin = findViewById<Button>(R.id.btn_sign)
        val matriculeED = findViewById<TextInputEditText>(R.id.matriculeED)
        val passwordED = findViewById<TextInputEditText>(R.id.pwED)
        val matriculeL = findViewById<TextInputLayout>(R.id.matriculeL)
        val passwordL = findViewById<TextInputLayout>(R.id.pwL)

        //Partir du code qui donne la couleur bleue au bottons
        btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)




        //validation pour gerer l'email

        matriculeED.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val matricule = s.toString()
                if (matricule.length != 8) {
                    matriculeL.error = null;
                    matriculeL.helperText = "matricule invalide"
                    matriculeL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    matriculeL.boxStrokeColor = Color.RED
                    matriculeL.hintTextColor = ColorStateList.valueOf(Color.RED)
                } else {
                    matriculeL.error = null;
                    matriculeL.helperText = "matricule valide"
                    matriculeL.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                    matriculeL.boxStrokeColor = Color.GREEN
                    matriculeL.hintTextColor = ColorStateList.valueOf(Color.GREEN)
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })

        // validation pour gerer le password
        passwordED.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length < 8) {
                    passwordL.helperText = "mininum 8 caracteres"
                    passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    passwordL.boxStrokeColor = Color.RED
                    passwordL.hintTextColor = ColorStateList.valueOf(Color.RED)
                } else {
                    passwordL.helperText = "passwordvalide"
                    passwordL.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                    passwordL.boxStrokeColor = Color.GREEN
                    passwordL.hintTextColor = ColorStateList.valueOf(Color.GREEN)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        //validation du button

        btnlogin.setOnClickListener {
            val matriculer = matriculeED.text.toString()
            val passwordr = passwordED.text.toString()
            if (matriculer.length == 0 && passwordr.length == 0) {
                matriculeL.error = null
                matriculeL.helperText = "remplir le champ"
                matriculeL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                matriculeL.boxStrokeColor = Color.RED
               matriculeL.hintTextColor = ColorStateList.valueOf(Color.RED)
                //==============================================================
                passwordL.error = null
                passwordL.helperText = "veillez remplir le champ"
                passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                passwordL.boxStrokeColor = Color.RED
                passwordL.hintTextColor = ColorStateList.valueOf(Color.RED)

            } else {
                if (matriculer.length == 0) {
                    matriculeL.error = null
                    matriculeL.helperText = "remplir le champ"
                    matriculeL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    matriculeL.boxStrokeColor = Color.RED
                    matriculeL.hintTextColor = ColorStateList.valueOf(Color.RED)
                } else {
                    if (passwordr.length == 0) {
                        passwordL.error = null
                        passwordL.helperText = "veillez remplir le champ"
                        passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                        passwordL.boxStrokeColor = Color.RED
                        passwordL.hintTextColor = ColorStateList.valueOf(Color.RED)
                    } else {
                        println("donnee valide tout va bien")
                        Toast.makeText(this, "donnee valide", Toast.LENGTH_SHORT).show()
                        // Observer sur le résultat de la validation
                        userViewModele.getUser(matriculer, passwordr).observe(this) { user ->
                            if (user != null) {
                                Toast.makeText(
                                    this,
                                    "Utilisateur valide${user.matricule}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                preference.addPreference("loginuser",user)



                            } else {
                                Toast.makeText(
                                    this,
                                    "Erreur, utilisateur non trouvé",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }


        }

    }
}