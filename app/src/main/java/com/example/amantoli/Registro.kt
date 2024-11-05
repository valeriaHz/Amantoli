package com.example.amantoli

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Registro : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var aPatInput: EditText
    private lateinit var aMatInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var radioGenero: RadioGroup
    private lateinit var birthdateInput: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registrar: Button
    private lateinit var cancelar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        nameInput = findViewById(R.id.name)
        aPatInput = findViewById(R.id.aPat)
        aMatInput = findViewById(R.id.aMat)
        ageInput = findViewById(R.id.age)
        radioGenero = findViewById(R.id.radioGenero)
        birthdateInput = findViewById(R.id.birthdate)
        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        registrar = findViewById(R.id.button_Registrar)
        cancelar = findViewById(R.id.button_cancelar)

        birthdateInput.setOnClickListener{
            showDatePickerDialog()
        }

        registrar.setOnClickListener {

            val nombre = nameInput.text.toString()
            val apellidoP = aPatInput.text.toString()
            val apellidoM = aMatInput.text.toString()
            val edadUsuario = ageInput.text.toString()
            val fechaNac = birthdateInput.text.toString()
            val correoUsuario = emailInput.text.toString()
            val contrasenaUsuario = passwordInput.text.toString()
            val generoSeleccionadoId = radioGenero.checkedRadioButtonId

            val generoSeleccionado = when (generoSeleccionadoId) {
                R.id.radioMasculino -> "Masculino"
                R.id.radioFemenino -> "Femenino"
                R.id.radioOtro -> "Otro"
                else -> "No especificado"
            }

            if(nombre.isNotEmpty() && apellidoP.isNotEmpty() && apellidoM.isNotEmpty()
                && edadUsuario.isNotEmpty() && fechaNac.isNotEmpty() && correoUsuario.isNotEmpty()
                && contrasenaUsuario.isNotEmpty()){

                val nuevoUsuario = Usuarios(
                    id_usuario = 0,
                    nombre = nombre,
                    apellidoPat = apellidoP,
                    apellidoMat = apellidoM,
                    edad = edadUsuario,
                    genero = generoSeleccionado,
                    correo = correoUsuario,
                    contraseña = contrasenaUsuario,
                    fechaNac = fechaNac
                )

                registrarUsuario(nuevoUsuario)
            }else{
                Toast.makeText(this, "Por favor llene los campos", Toast.LENGTH_LONG).show()
            }

        }

        cancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedYear - 1900, selectedMonth, selectedDay))
            birthdateInput.text = formattedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun registrarUsuario(usuarios: Usuarios){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitClient.webService.agregarUsuarios(usuarios)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        Toast.makeText(this@Registro, "Registro con Exito", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Registro, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@Registro, "Fallo el registro", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@Registro, "Error de conexion: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
