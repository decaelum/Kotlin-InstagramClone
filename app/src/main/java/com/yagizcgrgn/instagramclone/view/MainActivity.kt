package com.yagizcgrgn.instagramclone.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yagizcgrgn.instagramclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    fun login(view: View) {

    }

    fun register(view: View){

    }
}