package com.generation.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.generation.todo.MainViewModel.MainViewModel
import com.generation.todo.databinding.FragmentFormBinding
import com.generation.todo.databinding.FragmentListBinding
import com.generation.todo.fragment.DatePickerFragment
import com.generation.todo.fragment.TimePickerListener
import com.generation.todo.model.Categoria
import com.generation.todo.repository.Repository
import java.time.LocalDate

class FormFragment : Fragment(), TimePickerListener {

    private lateinit var binding: FragmentFormBinding
    private lateinit var mainViewModel: MainViewModel
    private var categoriaSelecionada = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)

        val repository = Repository()

        mainViewModel = MainViewModel(repository)

        mainViewModel.listCategorias()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner){
                response -> Log.d("Requisicao", response.body().toString())
                spinnerCategoria(response.body())
        }
        mainViewModel.dataSeleciona.observe(viewLifecycleOwner){
                selectedDate -> binding.editData.setText(selectedDate.toString())
        }

        binding.buttonSalvar.setOnClickListener {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }
        binding.editData.setOnClickListener {
            DatePickerFragment(this)
                .show(parentFragmentManager,"DatePicker")
        }
        
        return binding.root
    }
    fun spinnerCategoria(categoria: List<Categoria>?){

        if(categoria != null){

            binding.spinnerCategoria.adapter = ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                categoria
            )
            binding.spinnerCategoria.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val categoriaSelecionadaFun = binding.spinnerCategoria.selectedItem as Categoria
                        categoriaSelecionada = categoriaSelecionadaFun.id
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }


    }

    override fun onTimeSelected(date: LocalDate) {
        mainViewModel.dataSeleciona.value = date
    }
}