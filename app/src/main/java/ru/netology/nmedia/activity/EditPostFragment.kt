package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import util.StringArg

class EditPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        binding.not.visibility = View.VISIBLE

        binding.editText.setText(binding.editText.getText().toString())

        arguments?.textArg?.let(binding.editText::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.editText.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        binding.not.setOnClickListener{
            findNavController().navigateUp()
        }
        return binding.root
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityNewPostBinding.inflate(layoutInflater)
//        binding.not.visibility = View.VISIBLE
//        setContentView(binding.root)
//        binding.editText.setText(binding.editText.getText().toString() + intent.getStringExtra(Intent.EXTRA_TEXT))
//        binding.editText.requestFocus()
//        binding.ok.setOnClickListener {
//            val intent = Intent()
//            if (binding.editText.text.isBlank()) {
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else {
//
//                val content = binding.editText.text.toString()
//                intent.putExtra(Intent.EXTRA_TEXT, content)
//                setResult(Activity.RESULT_OK, intent)
//            }
//            finish()
//        }
//        binding.not.setOnClickListener{
//            finish()
//        }
//    }
}

