package ru.netology.nmedia.repository


import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PhotoModel

interface PostRepository {
    val data: Flow<PagingData<Post>>
    fun getNewer(id: Long): Flow<Int>
    suspend fun likeById(id: Long)
    fun times()
    suspend fun repostById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(postS: Post)
    suspend fun saveWithAttachment(postS: Post, photoModel: PhotoModel)
    suspend fun getAllAsynch()
    suspend fun showAll()
    suspend fun updateUser(login: String, pass:String)
    suspend fun registerUser(login: String, pass:String, name: String)
    suspend fun registerWithPhoto(login: String, pass:String, name: String, avatar: PhotoModel)
}
