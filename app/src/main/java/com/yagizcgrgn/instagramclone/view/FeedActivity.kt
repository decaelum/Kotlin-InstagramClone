package com.yagizcgrgn.instagramclone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yagizcgrgn.instagramclone.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}