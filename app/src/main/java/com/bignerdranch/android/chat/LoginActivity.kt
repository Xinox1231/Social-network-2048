package com.bignerdranch.android.chat

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    lateinit var etMail : EditText
    lateinit var etPassword : EditText

    lateinit var btnLogin : Button
    lateinit var btnForgotPassword : Button
    lateinit var tvRegister : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.login_btn_enter)
        btnForgotPassword = findViewById(R.id.login_btn_recovery_password)
        tvRegister = findViewById(R.id.login_btn_register)
        etMail = findViewById(R.id.login_et_email)
        etPassword = findViewById(R.id.login_et_password)

        btnLogin.setOnClickListener {

            when {
                TextUtils.isEmpty(etMail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Введите e-mail",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Введите пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etMail.text.toString()
                    val password: String = etPassword.text.toString()


                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Вы вошли в аккаунт", Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email", email)
                                startActivity(intent)
                                finish()
                                Log.d("Name", firebaseUser.displayName.toString())
                            }
                            else{
                                Toast.makeText(this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}