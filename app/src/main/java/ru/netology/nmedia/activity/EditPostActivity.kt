package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import kotlin.contracts.contract

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        binding.not.visibility = View.VISIBLE
        setContentView(binding.root)
        binding.editText.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.editText.text.isBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {

                val content = binding.editText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
        binding.not.setOnClickListener{
            finish()
        }
    }
}

