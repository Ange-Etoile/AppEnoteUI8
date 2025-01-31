import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appenote.Repository.UserRepository
import com.example.appenote.ViewModels.UserViewModele

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModele(repository) as T
    }
}
