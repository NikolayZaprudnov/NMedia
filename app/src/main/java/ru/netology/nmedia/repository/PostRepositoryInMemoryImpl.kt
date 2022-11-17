package ru.netology.nmedia.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import java.time.Instant
import java.time.LocalDateTime

class PostRepositoryInMemoryImpl : PostRepository {
    @RequiresApi(Build.VERSION_CODES.O)
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
        ),
        Post(
            3,
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
            0

        ))
    @RequiresApi(Build.VERSION_CODES.O)
    private val data = MutableLiveData(post)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAll(): LiveData<List<Post>> = data
    @RequiresApi(Build.VERSION_CODES.O)
    override fun likeById(id: Int) {
        post = post.map {
            if (it.id != id) it else it.copy(
                likesAmount = (if (it.likedByMe) it.likesAmount - 1 else it.likesAmount + 1),
                likedByMe = !it.likedByMe)
        }
        data.value = post
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun repostById(id: Int) {
        post = post.map { if (it.id != id) it else it.copy(repostAmount = it.repostAmount + 1) }
        data.value = post
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun times() {
        post = post.map { it.copy(time = LocalDateTime.now().toString()) }
        data.value = post
    }
}