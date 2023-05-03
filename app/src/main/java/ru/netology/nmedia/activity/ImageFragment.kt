package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentImageBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import util.StringArg

@AndroidEntryPoint
class ImageFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }


    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentImageBinding.inflate(
            inflater,
            container,
            false
        )
        binding.apply {
            val imageUrl = "http://10.0.2.2:9999/media/${arguments?.textArg.toString()}"
            Glide.with(image)
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_load_face_100)
                .error(R.drawable.ic_baseline_error_100)
                .timeout(10_000)
                .into(image)

        }

        return binding.root
    }
}