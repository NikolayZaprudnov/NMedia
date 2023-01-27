package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*
import util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

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
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsynch(object : PostRepository.Callback<List<Post>> {
            override fun onSucces(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun likeById(id: Long) {
        val errorLike = _data.value?.posts.orEmpty()
        _data.postValue( _data.value?.copy(_data.value?.posts.orEmpty().map {     if (it.id != id) it else it.copy(
            likesAmount = (it.likesAmount + 1),
            likedByMe = !it.likedByMe) }))
            repository.likeById(id, object: PostRepository.CallbackFoPost{
                override fun onError(e: Exception) {
                    _data.postValue(_data.value?.copy(posts = errorLike))
                }
        })}


    fun unlikeById(id: Long) {
        val errorLike = _data.value?.posts.orEmpty()
        _data.postValue( _data.value?.copy(_data.value?.posts.orEmpty().map {     if (it.id != id) it else it.copy(
            likesAmount = (it.likesAmount - 1),
            likedByMe = !it.likedByMe) }))
        repository.unlikeById(id, object: PostRepository.CallbackFoPost{
            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = errorLike))
            }
        })
    }

    fun times() = repository.times()
    fun repostById(id: Long) {
    val errorRepost = _data.value?.posts.orEmpty()
    _data.postValue( _data.value?.copy(_data.value?.posts.orEmpty().map {     if (it.id != id) it else it.copy(
        repostAmount = (it.repostAmount + 1)
        ) }))
    repository.repostById(id, object: PostRepository.CallbackFoPost{
        override fun onError(e: Exception) {
            _data.postValue(_data.value?.copy(posts = errorRepost))
        }
    })}
    fun removeById(id: Long) {

        val old = _data.value?.posts.orEmpty()
        _data.postValue(
            _data.value?.copy(posts = _data.value?.posts.orEmpty()
                .filter { it.id != id }
            )
        )
        repository.removeById(id, object: PostRepository.CallbackFoPost{
            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
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