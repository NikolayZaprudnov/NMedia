package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            1,
            "Запруднов Николай",
            "10.11.2022 21:22",
            "Внезапно, действия представителей оппозиции неоднозначны и будут описаны максимально подробно. Но граница обучения кадров, а также свежий взгляд на привычные вещи — безусловно открывает новые горизонты для поэтапного и последовательного развития общества. Повседневная практика показывает, что глубокий уровень погружения влечет за собой процесс внедрения и модернизации глубокомысленных рассуждений. В рамках спецификации современных стандартов, ключевые особенности структуры проекта объединены в целые кластеры себе подобных. В рамках спецификации современных стандартов, реплицированные с зарубежных источников, современные исследования заблокированы в рамках своих собственных рациональных ограничений.\n",
            false,
            999,
            999999
        )
        fun converter(amount: Int): String {
          var convert =  when(amount){
               in 0..999 -> amount.toString()
                in 1000..999999 -> ((amount/1000).toString() + "k")
               else -> ((amount/1000000).toString() + "M")
            }
            return convert
        }
        with(binding) {
            authorName.text = post.authorName
            time.text = post.time
            content.text = post.content
            if (post.likedByMe) {
                binding.likes.setImageResource(R.drawable.ic_baseline_star_24)
            }
            amounLikes.text = converter(post.likesAmount)
            amountReposts.text = converter(post.repostAmount)
            likes.setOnClickListener {
                if(post.likedByMe) post.likesAmount -- else post.likesAmount ++
                post.likedByMe = !post.likedByMe
                likes.setImageResource(if(post.likedByMe)R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_likes)
                amounLikes.text = converter(post.likesAmount)
            }
            reposts.setOnClickListener {
                post.repostAmount++
                amountReposts.text = converter(post.repostAmount)

            }
        }
    }
}