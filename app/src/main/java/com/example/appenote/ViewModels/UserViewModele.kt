package com.example.appenote.ViewModels


import androidx.lifecycle.ViewModel
import com.example.appenote.Repository.UserRepository
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class UserViewModele(private val repository: UserRepository) : ViewModel() {

    // Cette fonction va exposer un LiveData qui contient un objet User ou null
    fun getUser(matricule: String, password: String) = liveData(Dispatchers.IO) {
        // Ici, on appelle la fonction du repository pour récupérer l'utilisateur
        val user = repository.checkUser(matricule, password)
        // On envoie le résultat à l'UI
        emit(user)

    }
}