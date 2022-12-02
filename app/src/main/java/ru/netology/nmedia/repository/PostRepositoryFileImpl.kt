package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import java.time.LocalDateTime


class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "post.json"
    private var nextId = 1L

    private var post = emptyList<Post>()
    private val data = MutableLiveData(post)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                post = gson.fromJson(it, type)
                data.value = post
            }
        } else {
            sync()
        }}

    override fun getAll(): LiveData<List<Post>> = data

    override fun times() {
        post = post.map { it.copy(time = LocalDateTime.now().toString()) }
        data.value = post
        sync()
    }

    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
        sync()
    }

    override fun likeById(id: Long) {
        post = post.map {
            if (it.id != id) it else it.copy(
                likesAmount = (if (it.likedByMe) it.likesAmount - 1 else it.likesAmount + 1),
                likedByMe = !it.likedByMe)
        }
        data.value = post
        sync()
    }

    override fun repostById(id: Long) {
        post = post.map { if (it.id != id) it else it.copy(repostAmount = it.repostAmount + 1) }
        data.value = post
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(post))
        }
    }

    override fun save(postS: Post) {
        if (postS.id == 0L) {
            post = listOf(
                postS.copy(
                    id = nextId++,
                    authorName = "Me",
                    likedByMe = false,
                    time = "now"
                )
            ) + post
            data.value = post
            sync()
            return
        }

}}
