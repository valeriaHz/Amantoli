package com.example.amantoli

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var loginTitle: TextView
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var correo: EditText
    private lateinit var contraseña: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        correo = findViewById(R.id.Correo)
        contraseña = findViewById(R.id.Contraseña)
        loginTitle = findViewById(R.id.login_title)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)

        loginButton.setOnClickListener {
            val email = correo.text.toString()
            val password = contraseña.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUsuario(email, password)
            } else {
                Toast.makeText(this, "Ingresa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    fun loginUsuario(email: String, password: String) {
        val loginRequest = LoginRequest(correo = email, contraseña = password)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<LoginResponse> =
                    RetrofitClient.webService.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.usuarios != null) {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Login Exitoso",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent = Intent(this@MainActivity, MenuPrincipal::class.java)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Credenciales Incorrectas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error de Conexión",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error:${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


}