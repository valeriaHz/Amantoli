package com.example.amantoli

data class Usuarios (
    var id_usuario: Int,
    var nombre: String,
    var apellidoPat: String,
    var apellidoMat: String,
    var edad: String,
    var genero: String,
    var correo: String,
    var contraseña: String,
    var fechaNac: String
)

data class LoginRequest(
    var correo: String,
    var contraseña: String
)

data class LoginResponse(
    val message: String,
    val usuarios: Usuarios?,
    val error: String
)