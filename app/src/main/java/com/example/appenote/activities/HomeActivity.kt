package com.example.appenote.activities

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appenote.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var inputPet: TextInputLayout
    private lateinit var inputBorn: TextInputLayout
    private lateinit var inputColor: TextInputLayout
    private lateinit var inputMatricule: TextInputLayout
    private lateinit var verifyButton: Button
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        changeStatusBarColorToPrimary()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputMatricule = findViewById(R.id.input_matricule)
        inputPet = findViewById(R.id.input_pet)
        inputBorn = findViewById(R.id.input_born)
        inputColor = findViewById(R.id.input_color)
        verifyButton = findViewById(R.id.verifyButton)

        verifyButton.setOnClickListener {
            validateInput()
        }

    }


    private fun validateInput() {
        var isValid = true

        val matricule = inputMatricule.editText?.text.toString().trim()
        if (matricule.isEmpty()) {
            inputMatricule.error = "Please enter your pet's name"
            isValid = false
        } else {
            inputMatricule.error = null
        }
        val petName = inputPet.editText?.text.toString().trim()
        if (petName.isEmpty()) {
            inputPet.error = "Please enter your pet's name"
            isValid = false
        } else {
            inputPet.error = null
        }

        val bornCity = inputBorn.editText?.text.toString().trim()
        if (bornCity.isEmpty()) {
            inputBorn.error = "Please enter your city of birth"
            isValid = false
        } else {
            inputBorn.error = null
        }

        val favoriteColor = inputColor.editText?.text.toString().trim()
        if (favoriteColor.isEmpty()) {
            inputColor.error = "Please enter your favorite color"
            isValid = false
        } else {
            inputColor.error = null
        }

        if (isValid) {
            showLoadingDialog()
            verifySecurityQuestions(matricule,petName,bornCity,favoriteColor)
        } else {
        Toast.makeText(this, "Veuillez répondre à toutes les questions", Toast.LENGTH_SHORT).show()
    }
    }

    private fun verifySecurityQuestions(matricule: String,petName: String, bornCity: String, favoriteColor: String) {

        val db = Firebase.firestore

        db.collection("securityQuestions")
            .document("matricule")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val correctPetName = document.getString("petName")
                    val correctBornCity = document.getString("bornCity")
                    val correctFavoriteColor = document.getString("favoriteColor")

                    if (petName == correctPetName && bornCity == correctBornCity && favoriteColor == correctFavoriteColor) {
                        Toast.makeText(this, "Les réponses sont correctes", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Les réponses sont incorrectes", Toast.LENGTH_SHORT).show()
                    }
                    savePreferences("matricule",matricule)

                } else {
                    Toast.makeText(this, "Utilisateur introuvable", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erreur: ${exception.message}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
    }


    private fun savePreferences (key: String , data: String){
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(key,data)
        editor.apply()
    }

    private fun showLoadingDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Vérification en cours...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun changeStatusBarColorToPrimary() {
        val color = ContextCompat.getColor(this, R.color.primary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = (color)
            window.navigationBarColor = (color)
        }
    }

}