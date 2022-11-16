package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit
typealias OnRootListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    val onRepostListener: OnRepostListener,
    val onRootListener: OnRootListener
) : ListAdapter<Post,PostViewHolder>(PostDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onRepostListener, onRootListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
class PostDiffCallBack: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return  oldItem == newItem
    }
}

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
            amounLikes.text = post.likesAmount.toString()
            amountReposts.text = post.repostAmount.toString()
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
}
