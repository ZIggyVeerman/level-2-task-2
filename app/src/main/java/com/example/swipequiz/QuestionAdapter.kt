package com.example.swipequiz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter(private val questions: MutableList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
  private lateinit var removedItem: Question
  private var removedPosition: Int = 0
  private val correct: String = "correct"
  private val incorrect: String = "incorrect"
  private var correctOrIncorrect: String = ""

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(question: Question) {
      itemView.tvQuestion.text = question.questionText
      itemView.setOnClickListener{
        if(question.correctOrFalse){
          correctOrIncorrect = correct
        }

        if (!question.correctOrFalse){
          correctOrIncorrect = incorrect
        }

        val snackbar = Snackbar.make(itemView, "The correct answer for this question should be: $correctOrIncorrect",
          Snackbar.LENGTH_LONG).setAction("Action", null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)

        val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 18f

        snackbar.show()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
    )
  }

  override fun getItemCount(): Int {
    return questions.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(questions[position])
  }

  private fun deleteCurrentTodoItem(viewHolder: RecyclerView.ViewHolder) {
    questions.removeAt(viewHolder.adapterPosition)
    notifyItemRemoved(viewHolder.adapterPosition)

  }
  private fun undoDelete(viewHolder: RecyclerView.ViewHolder) {
    removedPosition = viewHolder.adapterPosition
    removedItem = questions[viewHolder.adapterPosition]

    questions.add(
      removedPosition,
      removedItem
    )
    notifyItemInserted(removedPosition)
  }

  private fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
  }

  fun correctOrNot(viewHolder: RecyclerView.ViewHolder, trueOrFalse: Boolean) {
    val question = questions[viewHolder.adapterPosition]

    if (trueOrFalse == question.correctOrFalse){
      deleteCurrentTodoItem(viewHolder)

    }

    if (!trueOrFalse == question.correctOrFalse) {
       undoDelete(viewHolder)
    }
  }


}
