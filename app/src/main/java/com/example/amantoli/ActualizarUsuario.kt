package com.example.amantoli

import android.R.attr.defaultValue
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActualizarUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actualizar_usuario)

        val idUsuario = intent.getIntExtra("id_usuario", -1)
        val nombre = intent.getStringExtra("nombre")
        val aPaterno = intent.getStringExtra("aPaterno")
        val aMaterno = intent.getStringExtra("aMaterno")
        val edad = intent.getStringExtra("edad")
        val genero = intent.getStringExtra("genero")
        val correo = intent.getStringExtra("correo")
        val contraseña = intent.getStringExtra("contraseña")
        val fechaNac = intent.getStringExtra("fechaNac")

        val editTextNombre = findViewById<EditText>(R.id.nombre)
        val editTextaPaterno = findViewById<EditText>(R.id.aPaterno)
        val editTextaMaterno = findViewById<EditText>(R.id.aMaterno)
        val editTextEdad = findViewById<EditText>(R.id.edad)
        val editTextCorreo = findViewById<EditText>(R.id.correo)
        val editTextContraseña = findViewById<EditText>(R.id.contraseña)

        editTextNombre.setText(nombre)
        editTextaPaterno.setText(aPaterno)
        editTextaMaterno.setText(aMaterno)
        editTextEdad.setText(edad)
        editTextCorreo.setText(correo)
        editTextContraseña.setText(contraseña)

        val radioGroupGenero = findViewById<RadioGroup>(R.id.radioGenero)
        when (genero){
            "Masculino" -> findViewById<RadioButton>(R.id.radioMasculino).isChecked = true
            "Femenino" -> findViewById<RadioButton>(R.id.radioFemenino).isChecked = true
            "Otro" -> findViewById<RadioButton>(R.id.radioOtro).isChecked = true
        }

        val btnFechaNacimiento = findViewById<Button>(R.id.fechaNac)
        btnFechaNacimiento.text = fechaNac

        btnFechaNacimiento.setOnClickListener{
            showDatePickerdialog(btnFechaNacimiento)
        }

        val btnActualizar = findViewById<Button>(R.id.button_actualizar)
        btnActualizar.setOnClickListener{
            val nombreNuevo = editTextNombre.text.toString()
            val aPaternoNuevo = editTextaPaterno.text.toString()
            val aMaternoNuevo = editTextaMaterno.text.toString()
            val edadNuevo = editTextEdad.text.toString()
            val generoNuevo = obtenerGeneroSeleccionado()
            val correoNuevo = editTextCorreo.text.toString()
            val contraseñaNuevo = editTextContraseña.text.toString()
            val fechaNacNuevo = btnFechaNacimiento.text.toString()

            val usuarioActualizado = Usuarios(
                id_usuario = idUsuario,
                nombre = nombreNuevo,
                apellidoPat = aPaternoNuevo,
                apellidoMat = aMaternoNuevo,
                edad = edadNuevo,
                genero = generoNuevo,
                correo = correoNuevo,
                contraseña = contraseñaNuevo,
                fechaNac = fechaNacNuevo
            )

            actualizarUsuario(idUsuario, usuarioActualizado)
        }

        val btnCancelar = findViewById<Button>(R.id.button_cancelar)
        btnCancelar.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun obtenerGeneroSeleccionado(): String{
        val radioGroupGenero = findViewById<RadioGroup>(R.id.radioGenero)
        return when(radioGroupGenero.checkedRadioButtonId){
            R.id.radioMasculino -> "Masculino"
            R.id.radioFemenino -> "Femenino"
            else -> "Otro"
        }
    }

    private fun showDatePickerdialog(btnFechaNacimiento: Button){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedYear - 1900, selectedMonth, selectedDay))
            btnFechaNacimiento.text = formattedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun actualizarUsuario(id: Int, usuarios: Usuarios){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = RetrofitClient.webService.actualizarUsuario(id, usuarios)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful) {
                        Toast.makeText(
                            this@ActualizarUsuario,
                            "Actualización exitosa",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@ActualizarUsuario, MenuPrincipal::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@ActualizarUsuario, "Fallo la actualizacion", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ActualizarUsuario, "Error de conexion: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}