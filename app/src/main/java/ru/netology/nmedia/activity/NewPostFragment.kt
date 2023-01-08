package ru.netology.nmedia.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import util.StringArg

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    val SAVE_DRAFT_FILENAME = "Draft"
    val SETTING = "settings"
    var savedDraft: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        var draft: String?
        savedDraft = context?.getSharedPreferences(SAVE_DRAFT_FILENAME, 0)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            draft = binding.editText.text.toString()
            val editor = savedDraft!!.edit()
            editor.putString(SETTING, draft)
            editor.apply()
            Toast.makeText(context,R.string.draftText, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.feedFragment)
        }
        draft = savedDraft!!.getString(SETTING, "").toString()
        binding.editText.setText(draft)
        binding.editText.requestFocus()
        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.editText.text.toString())
            viewModel.save()
            val editor = savedDraft!!.edit()
            draft = null
            editor.putString(SETTING, draft)
            editor.apply()
            findNavController().navigateUp()
        }
        return binding.root
    }
}
//            val intent = Intent()
//            if( binding.editText.text.isBlank()){
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else{
//
//            val content = binding.editText.text.toString()
//            intent.putExtra(Intent.EXTRA_TEXT, content)
//            setResult(Activity.RESULT_OK, intent)
//            }
////            finish()
//        }
//    }
//}