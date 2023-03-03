package com.yagizcgrgn.instagramclone.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.yagizcgrgn.instagramclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        auth = Firebase.auth
        setContentView(view)

        val currentUser = auth.currentUser
        println(currentUser.toString())

        if(currentUser != null){
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        binding.edtEmail.addTextChangedListener(getTextWatcher(binding.edtEmail, binding.btLogin))
        binding.edtPassword.addTextChangedListener(getTextWatcher(binding.edtPassword, binding.btLogin))
    }


    fun login(view: View) {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("LOGIN", "Login successful")
                        val intent = Intent(this@MainActivity, FeedActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("LOGIN", "Login Failed")
                        Toast.makeText(this@MainActivity, "Try Again", Toast.LENGTH_LONG).show()
                        binding.edtPassword.setText("")
                    }
                }
        } catch (e: Exception) {
            print(e.stackTrace)
        }

    }

    fun register(view: View) {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun buttonCheck(emailEDT : EditText, passEDT : EditText, button : Button) {

       val email = emailEDT.text.toString().trim()
       val password = passEDT.text.toString().trim()

       button.isEnabled = email.isNotEmpty() && password.isNotEmpty()

    }
    private fun getTextWatcher(emailEdt: EditText, button: Button) = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            buttonCheck(binding.edtEmail, binding.edtPassword ,binding.btLogin)
        }

        override fun afterTextChanged(s: Editable?) {}

    }


}