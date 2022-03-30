package com.generation.todo.MainViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generation.todo.databinding.FragmentFormBinding
import com.generation.todo.model.Categoria
import com.generation.todo.model.Tarefa
import com.generation.todo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private var _myCategoriaResponse = MutableLiveData<Response<List<Categoria>>>()
    private lateinit var binding: FragmentFormBinding
    val myCategoriaResponse: LiveData<Response<List<Categoria>>> =
            _myCategoriaResponse
    val dataSeleciona = MutableLiveData<LocalDate>()
    init {
        dataSeleciona.value = LocalDate.now()
        listCategorias()
    }

    init {
        listCategorias()
    }

    fun listCategorias(){
viewModelScope.launch {
    try {
    val response = repository.listCategoria()
        _myCategoriaResponse.value = response

    }catch (e: Exception){
    Log.d("Erro",e.message.toString())

    }
}

    }



fun addLista(tarefa: Tarefa){

    viewModelScope.launch {
        try {
        repository.addTarefa(tarefa)
        }catch (e: Exception){
            Log.d("Erro",e.message.toString())


        }
    }

}
    fun validarCampos(nome: String, desc: String, responsavel: String, data: String): Boolean {

        return (
                (nome==""|| nome.length < 3 || nome.length > 20)||
                (desc == ""|| desc.length < 5 || desc.length > 200)||
                (responsavel==""||responsavel.length < 3|| responsavel.length > 20)||
                        data=="")
    }
    fun inserirNoBanco(){

        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val responsavel = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria = Categoria(categoriaSeleciona, null,null)
    }
}
