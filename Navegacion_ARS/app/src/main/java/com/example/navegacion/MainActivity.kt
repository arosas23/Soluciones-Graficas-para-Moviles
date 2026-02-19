package com.example.navegacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var Correo: EditText
    private lateinit var Password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        Correo = findViewById(R.id.email)
        Password = findViewById(R.id.password)

    }

    fun login(view: View) {

        val correo = Correo.text.toString() //arosas@gmail.com
        val password = Password.text.toString() //arosas23

        if (correo.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(correo, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,Home::class.java).putExtra("email", task.result.user?.email.toString()))
            }
            else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val usuarioActual = Firebase.auth.currentUser

        if (usuarioActual != null) {
            startActivity(Intent(this, Home::class.java))
            Toast.makeText(this, "Usuario previamente autenticado", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}