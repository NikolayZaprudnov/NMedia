package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun likeById(id: Long, callback: CallbackFoPost)
    fun times()
    fun repostById(id: Long, callback: CallbackFoPost)
    fun unlikeById(id: Long, callback: CallbackFoPost)
    fun removeById(id: Long, callback: CallbackFoPost)
    fun save(postS: Post)
    fun getAllAsynch(callback: Callback<List<Post>>)
    interface Callback <T>{
        fun onSucces(data: T) {}
        fun onError(e: Exception) {}
    }
    interface CallbackFoPost{
        fun onSucces() {}
        fun onError(e: Exception) {}
    }
}
