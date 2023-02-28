package com.yagizcgrgn.instagramclone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yagizcgrgn.instagramclone.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun share(){

    }

    fun selectImage(){

    }
}