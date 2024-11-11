package com.example.amantoli

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(private var ListaUsuarios: ArrayList<Usuarios>) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = ListaUsuarios[position]
        holder.bind(usuario)
    }
    override fun getItemCount(): Int = ListaUsuarios.size

    fun actualizarLista(nuevaLista: ArrayList<Usuarios>) {
        ListaUsuarios.clear()
        ListaUsuarios.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    // ViewHolder para los elementos del RecyclerView
    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNombre: TextView = itemView.findViewById(R.id.rvNombre)

        // Vincular los datos del usuario al TextView
        fun bind(usuario: Usuarios) {
            tvNombre.text = usuario.nombre
        }
    }
}
