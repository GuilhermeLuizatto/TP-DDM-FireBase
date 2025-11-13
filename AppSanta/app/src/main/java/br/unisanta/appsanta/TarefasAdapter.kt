package br.unisanta.appsanta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TarefasAdapter(private var tarefas: List<Tarefa>) : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {

    class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeTarefa)
        val categoria: TextView = itemView.findViewById(R.id.tvCategoriaTarefa)
        val status: TextView = itemView.findViewById(R.id.tvStatusTarefa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.nome.text = tarefa.nome
        holder.categoria.text = "Categoria: ${tarefa.categoria}"
        holder.status.text = "Status: ${tarefa.status}"
    }

    override fun getItemCount() = tarefas.size

    fun updateData(newTarefas: List<Tarefa>) {
        this.tarefas = newTarefas
        notifyDataSetChanged() // Notifica o adapter que os dados mudaram
    }
}
