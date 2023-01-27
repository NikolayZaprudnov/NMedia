package ru.netology.nmedia.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import java.time.LocalDateTime
//
//class PostRepositorySQLiteImpl(
//    private val dao: PostDao
//) : PostRepository {
////    private var posts = emptyList<Post>()
////    private val data = MutableLiveData(posts)
////
////    init {
////        posts = dao.getAll()
////        data.value = posts
////    }
////
////    override fun getAll(): LiveData<List<Post>> = data
////
////    override fun save(post: Post) {
////        val id = post.id
////        val saved = dao.save(post)
////        posts = if (id == 0L) {
////            listOf(saved) + posts
////        } else {
////            posts.map {
////                if (it.id != id) it else saved
////            }
////        }
////        data.value = posts
////    }
////
////    override fun likeById(id: Long) {
////        dao.likeById(id)
////        posts = posts.map {
////            if (it.id != id) it else it.copy(
////                likedByMe = !it.likedByMe,
////                likesAmount = if (it.likedByMe) it.likesAmount - 1 else it.likesAmount + 1
////            )
////        }
////        data.value = posts
////    }
////
////    override fun times() {
////            posts = posts.map { it.copy(time = LocalDateTime.now().toString()) }
////            data.value = posts
////        }
////
////    override fun repostById(id: Long) {
////        posts = posts.map { if (it.id != id) it else it.copy(repostAmount = it.repostAmount + 1) }
////        data.value = posts
////    }
////
////    override fun removeById(id: Long) {
////        dao.removeById(id)
////        posts = posts.filter { it.id != id }
////        data.value = posts
////    }
//}