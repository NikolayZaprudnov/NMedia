package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun data(): LiveData<List<Post>>
    suspend fun likeById(id: Long): Boolean
    fun times()
    suspend fun repostById(id: Long): Boolean
    suspend fun unlikeById(id: Long): Boolean
    suspend fun removeById(id: Long): Boolean
    suspend fun save(postS: Post)
    suspend fun getAllAsynch()
}
