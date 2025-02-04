package com.example.appenote.Repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.appenote.Models.User
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest



// ...

interface UserRepository {
    fun registerUser(name: String, firstname: String, matricule: String,  password: String)
    fun saveUserIdToSharedPreferences(matricule: String)

    companion object {
        fun getUserIdFromSharedPreferences(context: Context): String? {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            return sharedPreferences.getString("matricule", null)
        }
    }
}

class UserRepositoryImpl(private val firestore: FirebaseFirestore,private val application: Application,private val context:Context) : UserRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun saveUserIdToSharedPreferences(matricule: String) {
        editor.putString("matricule", matricule)
        editor.apply()
    }
    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    override fun registerUser(name: String, firstname: String, matricule: String,  password: String) {
        // Enregistrer l'utilisateur dans Firebase Firestore
        val user = User(name, firstname, matricule,  password)
        val userData = hashMapOf(
            "name" to user.nom,
            "firstname" to user.prenom,
            "matricule" to user.matricule,
            "password" to (user.password).toMD5()
        )

        firestore.collection("users")
            .document(matricule)
            .set(userData)
            .addOnSuccessListener {
                Toast.makeText(application, "Enregistrement réussi !", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
             Toast.makeText(application, "Erreur", Toast.LENGTH_LONG).show()


            }
    }
}