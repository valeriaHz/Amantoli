package com.example.amantoli

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuPrincipal : AppCompatActivity() {
    private lateinit var cierreSesion: Button
    private lateinit var adaptador: UsuarioAdapter
    private var ListaUsuarios = arrayListOf<Usuarios>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val recyclerView = findViewById<RecyclerView>(R.id.item_rv_usuario)
        adaptador = UsuarioAdapter(ListaUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptador
        obtenerUsuarios()

        cierreSesion = findViewById(R.id.cierreSesion)
        cierreSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun eliminarUsuario(userId:Int){
        CoroutineScope(Dispatchers.IO).launch{
            val call = RetrofitClient.webService.eliminarUsuario(userId)
            runOnUiThread{
                if(call.isSuccessful){
                    ListaUsuarios.removeAll {it.id_usuario == userId}
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(this@MenuPrincipal, "Usuario Eliminado", Toast.LENGTH_LONG).show()
                    obtenerUsuarios()
                }
            }
        }
    }

    private fun obtenerUsuarios() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.obtenerDatos()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val usuarios: List<Usuarios> = response.body() as List<Usuarios>? ?: emptyList()
                        ListaUsuarios.clear()
                        ListaUsuarios.addAll(usuarios)
                        adaptador.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MenuPrincipal, "No hay registros", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MenuPrincipal, "Error al obtener los datos: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun cerrarSesion(){
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}