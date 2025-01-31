package com.example.appenote.Repository

import android.app.Application
import android.widget.Toast
import com.example.appenote.Models.Question
import com.google.firebase.firestore.FirebaseFirestore

interface QuestionRepository{
    fun saveQuestion(question: Question)
}
class QuestionRepositoryImpl(private val firestore: FirebaseFirestore, private val application: Application) : QuestionRepository {
    override fun saveQuestion(question: Question) {

        val questiondata = hashMapOf(
            "user_matricule" to question.user_matricule,
            "first_question" to question.first_question,
            "second_question" to question.second_question,
            "last_question" to question.last_question

        )
        firestore.collection("Questions").document(question.user_matricule)
            .set(questiondata)
            .addOnSuccessListener {
                Toast.makeText(application, "Question bien enregistrer", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(application, "Erreur d'enregistrement", Toast.LENGTH_LONG).show()
            }
    }
}