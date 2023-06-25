package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardAdBinding
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Ad
import ru.netology.nmedia.dto.FeedItem
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.AdViewHolder
import ru.netology.nmedia.viewmodel.PostViewHolder

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onRepost(post: Post) {}
    fun onRoot(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
    fun onPlay(post: Post)
    fun onOpen(post: Post)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<FeedItem, RecyclerView.ViewHolder>(PostDiffCallBack()) {



    override fun getItemViewType(position: Int): Int =
        when (getItem(position)){
            is Ad -> R.layout.card_ad
            is Post -> R.layout.post_card
            null -> error("unknown item type")
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType){
            R.layout.card_ad -> {
                val binding = CardAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return AdViewHolder(binding)
            }
            R.layout.post_card -> {
                val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostViewHolder(binding, onInteractionListener)
            }
            else -> error("unknown view type: $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position)){
            is Ad -> (holder as? AdViewHolder)?.bind(item)
            is Post -> (holder as? PostViewHolder)?.bind(item)
            null -> error("unknown item type")
        }
    }

}

data class PayLoad (
    val likedByMe: Boolean? = null,
    val content: String? = null,
        )


class PostDiffCallBack : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        if (oldItem::class != newItem::class){
            return false
        }
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }

//    override fun getChangePayload(oldItem: Post, newItem: Post): Any =
//        PayLoad(
//            likedByMe = newItem.likedByMe.takeIf{ it!= oldItem.likedByMe},
//            content = newItem.content.takeIf { it != oldItem.content },
//        )


}


