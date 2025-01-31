package com.example.appenote.Repository

import android.app.Application
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

interface ResetPasswordRepository{
    fun upadeteUser(password:String)

}

class ResetPasswordRepositoryImpl(private val firestore: FirebaseFirestore,private val application: Application):ResetPasswordRepository {
    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    override fun upadeteUser(password: String) {
        val userRef = firestore.collection("users").document("5lWa7nKZME5iAkovwVfZ")
        val updates = mapOf(
            "password" to password  // Nouveau mot pour l'utilisateur
        )
        userRef.update(updates)
            .addOnSuccessListener {
                Toast.makeText(application, "Mot de passe Modifier !", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(application, "Erreur", Toast.LENGTH_LONG).show()

            }
    }
}