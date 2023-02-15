package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*
import util.SingleLiveEvent

private val empty = Post(
    0,
    "",
    "netology.jpg",
    "",
    "",
    false,
    0,
    0,
    null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(application).postDao())
    val data: LiveData<FeedModel> = repository.data().map { FeedModel(it, it.isEmpty()) }
    private val _state = MutableLiveData(FeedModelState())
    val state: LiveData<FeedModelState>
        get() = _state

    val edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {
        try {
            _state.value = FeedModelState(loading = true)
            repository.getAllAsynch()
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }
    }
//    fun refreshPosts() = viewModelScope.launch {
//        try {
//            _state.value = FeedModelState(refreshing = true)
//            repository.getAllAsynch()
//            _state.value = FeedModelState()
//        }catch (e:Exception){
//            _state.value = FeedModelState(error = true)
//        }
//    }

    fun likeById(id: Long) = viewModelScope.launch {
        val errorLike = data.value?.posts.orEmpty()
        data.value?.copy(data.value?.posts.orEmpty().map {
            if (it.id != id) it else it.copy(
                likesAmount = (it.likesAmount + 1),
                likedByMe = !it.likedByMe)
        })
        val result = repository.likeById(id)
        if (result == false) {
            data.value?.copy(posts = errorLike)
        }
    }


    fun unlikeById(id: Long) = viewModelScope.launch {
        val errorLike = data.value?.posts.orEmpty()
        data.value?.copy(data.value?.posts.orEmpty().map {
            if (it.id != id) it else it.copy(
                likesAmount = (it.likesAmount - 1),
                likedByMe = !it.likedByMe)
        })
        val result = repository.unlikeById(id)
        if (result == false) {
            data.value?.copy(posts = errorLike)
        }
    }

    fun times() = repository.times()
    fun repostById(id: Long) = viewModelScope.launch {
        val errorRepost = data.value?.posts.orEmpty()
        data.value?.copy(data.value?.posts.orEmpty().map {
            if (it.id != id) it else it.copy(
                repostAmount = (it.repostAmount + 1)
            )
        })
        val result = repository.repostById(id)
        if (result == false) {
            data.value?.copy(posts = errorRepost)
        }
    }

    fun removeById(id: Long) = viewModelScope.launch {

        val old = data.value?.posts.orEmpty()
        data.value?.copy(posts = data.value?.posts.orEmpty()
            .filter { it.id != id }
        )
        val result = repository.removeById(id)
        if (result == false) {
            data.value?.copy(posts = old)
        }
    }

    fun save() = edited.value?.let {
        _postCreated.value = Unit
        viewModelScope.launch {
                repository.save(it)
                _state.value = FeedModelState()
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
}