package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import repository.AuthAppRepository

class AuthAppViewModel : ViewModel() {
    private val repository: AuthAppRepository = AuthAppRepository()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val loggedOutData  : MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)

    fun register(email: String, password: String){
        repository.register(email,password)
        userLiveData.value = repository.userLiveData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }
    fun signIn(email: String, password: String){
        repository.signIn(email,password)
        userLiveData.value = repository.userLiveData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }
    fun signOut(){
        repository.signOut()
        loggedOutData.value = repository.loggedOutData.value
    }
}