package edu.bluejack22_2.Caddo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.bluejack22_2.Caddo.ViewModel.HomeViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var tvToRegister : TextView
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText

    private lateinit var btnLogin : Button

    private lateinit var loginViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvToRegister = findViewById(R.id.tv_toRegister)
        var auth : FirebaseAuth = Firebase.auth

        etEmail = findViewById(R.id.login_et_email)
        etPassword = findViewById(R.id.login_et_password)
        btnLogin = findViewById(R.id.btnLogin)

        loginViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //direct to register activity
        tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            if(etEmail.text.toString().isEmpty())
            {
                Toast.makeText(this, "the email must be filled !", Toast.LENGTH_SHORT).show()
            }
            else if(etPassword.text.toString().isEmpty()){
                Toast.makeText(this, "the password must be filled !", Toast.LENGTH_SHORT).show()
            }
            else if(!(etEmail.text.toString().endsWith(".com") || etEmail.text.toString().endsWith(".co.id"))){
                Toast.makeText(this, "the email must ends with .com or .co.id",Toast.LENGTH_SHORT).show()
            }
            else if(etPassword.text.toString().length < 6){
                Toast.makeText(this, "the password characters must above than 6",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "login ${etEmail.text.toString()}",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "auth failed !", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .addOnFailureListener {

                    }
            }
        }

    }
}