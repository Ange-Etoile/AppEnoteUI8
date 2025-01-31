package com.example.appenote.Repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.appenote.Models.User
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

// ...

interface UserRepository {
    fun registerUser(name: String, firstname: String, matricule: String,  password: String)
}

class UserRepositoryImpl(private val firestore: FirebaseFirestore,private val application: Application) : UserRepository {
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