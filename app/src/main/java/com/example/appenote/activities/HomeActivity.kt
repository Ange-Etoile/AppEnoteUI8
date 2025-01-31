package com.example.appenote.activities

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            savePrefs(matricule,petName,bornCity,favoriteColor)
//            getPrefs()
        }
    }

    private fun savePrefs(matricule:String,petName: String, bornCity: String, favoriteColor: String) {


        val db = Firebase.firestore

//        val documentSnapshot = db.collection("users").document(matricule).get().await()
//
//        if (documentSnapshot.exists()) {
//        }

        val user = hashMapOf(
            "petName" to petName,
            "bornCity" to bornCity,
            "favoriteColor" to favoriteColor
        )

        db.collection("users").document(matricule)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
            }
//        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        editor.putString("petName",petName)
//        editor.putString("bornCity",bornCity)
//        editor.putString("favoriteColor",favoriteColor)
//        editor.apply()
    }

    private fun getPrefs(){
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val petName = sharedPreferences.getString("petName", null)
        val bornCity = sharedPreferences.getString("bornCity", null)
        val favoriteColor = sharedPreferences.getString("favoriteColor", null)


        if (petName != null && bornCity != null && favoriteColor != null) {
            Toast.makeText(this, "SharedPreferences : Pet Name: $petName, Born City: $bornCity, Favorite Color: $favoriteColor", Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(this,  "erroooooroooorororororo", Toast.LENGTH_SHORT).show()
    }
}