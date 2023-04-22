package ru.netology.nmedia.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class AuthViewModel( application: Application) : AndroidViewModel(application) {
    val state = AppAuth.getInstance().authStateFlow.asLiveData()
    val authorized: Boolean
        get() = state.value?.id != 0L
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(application).postDao())

    private val _photoState = MutableLiveData<PhotoModel?>()
    val photoState: LiveData<PhotoModel?>
        get() = _photoState

    fun updateUser(login: String, pass:String) = viewModelScope.launch {
        repository.updateUser(login, pass)
    }
    fun changePhoto(photoModel: PhotoModel?) {
        _photoState.value = photoModel
    }
    fun reggistrationUser(login: String, pass:String, name: String)= viewModelScope.launch {
        repository.registerUser(login, pass, name)
    }
    fun registrationUserWithPhoto(login: String, pass:String, name: String, avatar: PhotoModel)= viewModelScope.launch {
        repository.registerWithPhoto(login, pass, name, avatar)
    }
//    fun checkRegistration{
//        if(authorized == false){
//            Toast.makeText(requireContext(), "Необходимо пройти регистрацию/авторизацию", Toast.LENGTH_SHORT)
//        }
//    }

}