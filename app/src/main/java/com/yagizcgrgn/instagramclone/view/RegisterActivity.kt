package com.yagizcgrgn.instagramclone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yagizcgrgn.instagramclone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun registerSave(view: View){

    }

    fun cancel(view: View){

    }
}