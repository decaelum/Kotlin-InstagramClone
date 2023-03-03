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
import com.yagizcgrgn.instagramclone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    private var intent : Intent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        intent  =  Intent(this@RegisterActivity,MainActivity::class.java)
        val view = binding.root


        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        binding.edtEmail.addTextChangedListener(getTextWatcher(binding.edtEmail))
        binding.edtPassword.addTextChangedListener(getTextWatcher(binding.edtPassword))
    }

    fun registerSave(view: View){

        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@RegisterActivity) { task ->
                    if (task.isSuccessful) {
                        Log.d("NEWUSER", "New user added")
                        val user = auth.currentUser
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("NEWUSER", "New user adding failed", task.exception)
                        Toast.makeText(this@RegisterActivity, "Adding Failed", Toast.LENGTH_LONG
                        ).show()
                        startActivity(intent)
                        finish()
                        TODO("Snackbar add(optional)")
                    }
                }
        }catch (e : Exception){
            print(e.stackTrace)
        }

    }

    fun cancel(view: View){
        startActivity(intent)
        finish()
    }

    private fun buttonCheck(emailEDT : EditText, passEDT : EditText, button : Button) {

        val email = emailEDT.text.toString().trim()
        val password = passEDT.text.toString().trim()

        button.isEnabled = email.isNotEmpty() && password.isNotEmpty()

    }

    private fun getTextWatcher(emailEdt: EditText) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            buttonCheck(binding.edtEmail, binding.edtPassword ,binding.btRegisterSave)
        }

        override fun afterTextChanged(s: Editable?) {}

    }

}