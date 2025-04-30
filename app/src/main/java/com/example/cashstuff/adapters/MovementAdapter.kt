package com.example.cashstuff.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MovementAdapter(var items: List<Movement>,
                  val onClick: (Int) -> Unit,
                  val onDelete: (Int) -> Unit,
                  val onCheck: (Int) -> Unit,
                  val context: Context
) : Adapter<MovementViewHolder>() {

    // Acceder a los arrays definidos en el archivo XML de recursos
    private val priorityLabels = context.resources.getStringArray(R.array.priority_labels)
    private val priorityColors = context.resources.getIntArray(R.array.priority_colors)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val binding = ItemMovementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovementViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        val movement = items[position]
        holder.render(movement)

        // Asignar el color de la prioridad usando la posición
        val priorityColor = if (movement.priority in 1..3) {
            priorityColors[movement.priority] // Obtener el color según el valor de la prioridad
        } else {
            Color.TRANSPARENT // Sin prioridad
        }

        // Color del círculo
        holder.binding.priorityCircle.setColorFilter(priorityColor)


        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.binding.deleteButton.setOnClickListener {
            onDelete(position)
        }
        holder.binding.doneCheckBox.setOnCheckedChangeListener { _, _ ->
            if (holder.binding.doneCheckBox.isPressed) {
                onCheck(position)
            }
        }
    }

    fun updateItems(items: List<Movement>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class MovementViewHolder(val binding: ItemMovementBinding) : ViewHolder(binding.root) {

    fun render(movement: Movement) {
        binding.doneCheckBox.isChecked = movement.done

        if (movement.done) {
            binding.titleTextView.text = movement.title.addStrikethrough()
        } else {
            binding.titleTextView.text = movement.title
        }
    }
}