package br.unisanta.appsanta

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.unisanta.appsanta.databinding.ActivityListaTarefasBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class Tarefa(
    val nome: String = "",
    val categoria: String = "",
    val status: String = ""
)

class ListaTarefasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTarefasBinding
    private lateinit var tarefasAdapter: TarefasAdapter
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTarefasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        recuperarTarefas()
    }

    private fun setupRecyclerView() {
        tarefasAdapter = TarefasAdapter(emptyList())
        binding.recyclerViewTarefas.apply {
            layoutManager = LinearLayoutManager(this@ListaTarefasActivity)
            adapter = tarefasAdapter
        }
    }

    private fun recuperarTarefas() {
        db.collection("tarefas")
            .get()
            .addOnSuccessListener { result ->
                val listaTarefas = result.map { document ->
                    document.toObject(Tarefa::class.java)
                }
                tarefasAdapter.updateData(listaTarefas)
            }
            .addOnFailureListener { exception ->
                Log.w("ListaTarefasActivity", "Erro ao buscar documentos: ", exception)
                Toast.makeText(this, "Erro ao recuperar tarefas.", Toast.LENGTH_SHORT).show()
            }
    }
}
