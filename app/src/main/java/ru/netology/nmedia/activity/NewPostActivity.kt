package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editText.requestFocus()
        binding.ok.setOnClickListener{
            val intent = Intent()
            if( binding.editText.text.isBlank()){
                setResult(Activity.RESULT_CANCELED, intent)
            } else{

            val content = binding.editText.text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, content)
            setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}