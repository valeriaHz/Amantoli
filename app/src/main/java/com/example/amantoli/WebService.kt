package com.example.amantoli

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object AppContantes{
    const val BASE_URL="http://10.0.2.2:3000" //poner direccion ip de la computadora si emulas desde el celular
}

interface WebService {
    @POST("/appAgregarUsuario")
    suspend fun agregarUsuarios(
        @Body usuarios:Usuarios
    ): Response<String>

    @POST("/appInicioSesion")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}

object  RetrofitClient{
    val webService : WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppContantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}