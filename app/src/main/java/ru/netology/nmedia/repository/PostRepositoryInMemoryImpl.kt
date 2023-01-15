package ru.netology.nmedia.repository

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import java.time.Instant
import java.time.LocalDateTime

class PostRepositoryInMemoryImpl : PostRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    private var nextId = 1L

    @RequiresApi(Build.VERSION_CODES.O)
    private var post = listOf(
        Post(
            id = nextId++,
            "Запруднов Николай",
            "10.11.2022 21:22",
            "Внезапно, действия представителей оппозиции неоднозначны и будут описаны максимально подробно. Но граница обучения кадров, а также свежий взгляд на привычные вещи — безусловно открывает новые горизонты для поэтапного и последовательного развития общества. Повседневная практика показывает, что глубокий уровень погружения влечет за собой процесс внедрения и модернизации глубокомысленных рассуждений. В рамках спецификации современных стандартов, ключевые особенности структуры проекта объединены в целые кластеры себе подобных. В рамках спецификации современных стандартов, реплицированные с зарубежных источников, современные исследования заблокированы в рамках своих собственных рациональных ограничений.\n",
            false,
            999,
            999999,
            "https://youtu.be/dQw4w9WgXcQ"
    ),
        Post(
            id = nextId++,
            "Некто",
            "Какое то",
            "Какой то,",
            false,
            0,
            0,
            null
        ),
        Post(
            id = nextId++,
            "Таинственный Тестовый Человечек",
            time = Instant.now().toString(),
            "Редуцио — уменьшает объект.\n" +
                    "\n" +
                    "Релашио — освобождение объекта от чего-то удерживающего.\n" +
                    "\n" +
                    "Реннервейт — приводит в сознание.\n" +
                    "\n" +
                    "Репаро — восстанавливает разбитое.\n" +
                    "\n" +
                    "Ридикулус — заклинание против боггарта.\n" +
                    "\n" +
                    "Риктусемпра — заклинание щекотки.\n" +
                    "\n" +
                    "Сектумсемпра — ранит как мечом.\n" +
                    "\n" +
                    "Серпенсортиа — создаёт змею.\n" +
                    "\n" +
                    "Силенцио — заклятие немоты.\n" +
                    "\n" +
                    "Сонорус — увеличивает громкость голоса.\n" +
                    "\n" +
                    "Ступефай — парализует противника.\n" +
                    "\n" +
                    "Таранталлегра — ноги противника пускаются в безудержный пляс.\n" +
                    "\n" +
                    "Тергео, эскуро — очищающие заклятия.\n" +
                    "\n" +
                    "Ферула — накладывает шину на поврежденную конечность.\n" +
                    "\n" +
                    "Финита, фините инкантатем — прекращает действия заклинаний.\n" +
                    "\n" +
                    "Флагрейт — помечает предмет светящимся крестом.\n" +
                    "\n" +
                    "Фурункулюс — вызывает на теле противника нарывы.\n" +
                    "\n" +
                    "Эванеско — исчезновение объекта.\n" +
                    "\n" +
                    "Экспекто патронум — создаёт патронус.\n" +
                    "\n" +
                    "Экспеллиармус — разоружает противника.\n" +
                    "\n" +
                    "Экспеллимеллиус, инсендио — поджигает объект.\n" +
                    "\n" +
                    "Экспульсо, конфринго — взрывает объект.\n" +
                    "\n" +
                    "Энгоргио — увеличивает предмет.\n" +
                    "\n" +
                    "Эпискей — прекращает кровотечение, вправляет кости.\n" +
                    "\n" +
                    "Эскуро, тергео — очищающие заклятия.",
            false,
            0,
            0,
            null

        ))

    @RequiresApi(Build.VERSION_CODES.O)
    private val data = MutableLiveData(post)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAll(): List<Post> = data.value!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun likeById(id: Long) {
        post = post.map {
            if (it.id != id) it else it.copy(
                likesAmount = (if (it.likedByMe) it.likesAmount - 1 else it.likesAmount + 1),
                likedByMe = !it.likedByMe)
        }
        data.value = post
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun repostById(id: Long) {
        post = post.map { if (it.id != id) it else it.copy(repostAmount = it.repostAmount + 1) }
        data.value = post
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun times() {
        post = post.map { it.copy(time = LocalDateTime.now().toString()) }
        data.value = post
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
            return
        }
        post = post.map {
            if (it.id != postS.id) it else it.copy(content = postS.content)
        }
        data.value = post
    }
}