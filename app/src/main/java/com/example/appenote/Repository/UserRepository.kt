package com.example.appenote.Repository

import com.example.appenote.Modele.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository() {
    private val db = Firebase.firestore

    suspend fun checkUser(matricule:String,password:String): User?{

        return try {
            val userpresent = db.collection("users")
                .whereEqualTo("matricule",matricule)
                .whereEqualTo("mot_De_Passe",password)
                .get()
                .await()

            if(!userpresent.isEmpty){
                val document = userpresent.documents[0]
                User(document.getString("matricule")?:"",document.getString("mot_De_Passe")?:"",document.getString("nom")?:"",document.getString("prenom")?:"")
            }
            else{
                null
            }
        } catch (e: Exception) {
            null
        }

    }
}