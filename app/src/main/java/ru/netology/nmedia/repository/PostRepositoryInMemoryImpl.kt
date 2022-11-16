package ru.netology.nmedia.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import java.time.LocalDateTime

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = listOf(
        Post(
        1,
        "Запруднов Николай",
        "10.11.2022 21:22",
        "Внезапно, действия представителей оппозиции неоднозначны и будут описаны максимально подробно. Но граница обучения кадров, а также свежий взгляд на привычные вещи — безусловно открывает новые горизонты для поэтапного и последовательного развития общества. Повседневная практика показывает, что глубокий уровень погружения влечет за собой процесс внедрения и модернизации глубокомысленных рассуждений. В рамках спецификации современных стандартов, ключевые особенности структуры проекта объединены в целые кластеры себе подобных. В рамках спецификации современных стандартов, реплицированные с зарубежных источников, современные исследования заблокированы в рамках своих собственных рациональных ограничений.\n",
        false,
        999,
        999999),
        Post(
            2,
            "Некто",
            "Какое то",
        "Какой то,",
            false,
            0,
            0
        ))
    private val data = MutableLiveData(post)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Int) {
        post = post.map{
            if(it.id != id) it else it.copy(
            likesAmount = (if(it.likedByMe) it.likesAmount ++ else it.likesAmount--),
            likedByMe = !it.likedByMe)}
        data.value = post
    }
    override fun repost(){
        post = post.map { it.copy(repostAmount = it.repostAmount++)}
            data.value = post
    }

    override fun converter(amount: Int): String {
        var convert =  when(amount){
            in 0..999 -> amount.toString()
            in 1000..999999 -> ((amount/1000).toString() + "k")
            else -> ((amount/1000000).toString() + "M")
        }
        return convert
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun times() {
        post = post.map { it.copy(time = LocalDateTime.now().toString()) }
        data.value = post
    }
}