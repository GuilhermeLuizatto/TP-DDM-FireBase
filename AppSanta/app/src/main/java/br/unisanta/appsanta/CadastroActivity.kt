package br.unisanta.appsanta

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.appsanta.databinding.ActivityCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Firebase.firestore

        binding.btnCadastrar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val categoria = binding.edtCategoria.text.toString()
            val status = binding.edtStatus.text.toString()

            if (nome.isNotEmpty() && categoria.isNotEmpty() && status.isNotEmpty()) {
                val tarefa = hashMapOf(
                    "nome" to nome,
                    "categoria" to categoria,
                    "status" to status
                )
                db.collection("tarefas")
                    .add(tarefa)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this, "Tarefa cadastrada com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Limpa os campos após o cadastro
                            binding.edtNome.text.clear()
                            binding.edtCategoria.text.clear()
                            binding.edtStatus.text.clear()
                        } else {
                            Toast.makeText(
                                this, "Erro ao cadastrar tarefa",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação para o botão de ver tarefas
        binding.btnVerTarefas.setOnClickListener {
            val intent = Intent(this, ListaTarefasActivity::class.java)
            startActivity(intent)
        }
    }
}
