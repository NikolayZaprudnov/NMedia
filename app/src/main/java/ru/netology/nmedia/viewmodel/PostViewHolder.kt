package ru.netology.nmedia.viewmodel

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit
typealias OnRootListener = (post: Post) -> Unit
class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListener: OnLikeListener,
    private val onRootListener: OnRootListener,
    private val onRepostListener: OnRepostListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorName.text = post.authorName
            time.text = post.time
            content.text = post.content
            likes.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_likes)
            amounLikes.text = converter(post.likesAmount)
            amountReposts.text = converter(post.repostAmount)
            likes.setOnClickListener {
                onLikeListener(post)
            }
            root.setOnClickListener {
                onRootListener(post)
            }
            reposts.setOnClickListener {
                onRepostListener(post)

            }
        }
    }
     fun converter(amount: Int): String {
        var convert =  when(amount){
            in 0..999 -> amount.toString()
            in 1000..999999 -> ((amount/1000).toString() + "k")
            else -> ((amount/1000000).toString() + "M")
        }
        return convert
    }
}