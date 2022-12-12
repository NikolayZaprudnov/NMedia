package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.viewmodel.PostViewModel
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils


class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()


        val editPostLauncher = registerForActivityResult(EditPostResultContract()){
                result -> result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        val adapter = PostsAdapter(object : OnInteractionListener {


            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRepost(post: Post) {
                viewModel.repostById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "Repost post")
                startActivity(shareIntent)
            }



            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onPlay(post: Post) {
                val startVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
               startActivity(startVideo)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            editPostLauncher.launch(post)
        }
//        binding.notEdit.setOnClickListener {
//            with(binding.content){
////                if (text.isNotEmpty()){
////                    Toast.makeText(
////                        this@FeedFragment,
////                        context.getString(R.string.notEdit),
////                        Toast.LENGTH_SHORT
////                    ).show()
//                    text.clear()
//                    binding.editGroup.visibility = View.GONE
//                    AndroidUtils.hideKeyboard(this)
//                }
//            }

        binding.newpost.setOnClickListener{
           findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

//        binding.save.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//
//                setText("")
//                clearFocus()
//                binding.editGroup.visibility = View.GONE
//                AndroidUtils.hideKeyboard(this)
//            }
//        }

   return binding.root }
}

