package com.generation.todo.repository

import com.generation.todo.api.RetrofitInstance
import com.generation.todo.model.Categoria
import com.generation.todo.model.Tarefa
import retrofit2.Response
import retrofit2.http.Body

class Repository {

    suspend fun listCategoria(): Response<List<Categoria>>{

        return RetrofitInstance.api.listCategoria()
    }
    suspend fun addTarefa(tarefa: Tarefa): Response<Tarefa>{
        return RetrofitInstance.api.addTarefa(tarefa)
    }
}