package com.example.swipequiz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter(private val questions: MutableList<Question>) :
  RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
  private lateinit var removedItem: Question
  private var removedPosition: Int = 0
  private val correct: String = "correct"
  private val incorrect: String = "incorrect"
  private var correctOrIncorrect: String = ""

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(question: Question) {
      itemView.tvQuestion.text = question.questionText
      itemView.setOnClickListener {
        if (question.correctOrFalse) {
          correctOrIncorrect = correct
        }

        if (!question.correctOrFalse) {
          correctOrIncorrect = incorrect
        }

        val snackbar = Snackbar.make(
          itemView, "The correct answer for this question should be: $correctOrIncorrect",
          Snackbar.LENGTH_LONG
        ).setAction("Action", null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)

        val textView =
          snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 18f

        snackbar.show()
      }
    }
  }

  /**
   * Creates and returns a ViewHolder object, inflating the layout called item_reminder.
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
    )
  }

  /**
   *  Returns the size of the list
   */
  override fun getItemCount(): Int {
    return questions.size
  }

  /**
   * Called by RecyclerView to display the data at the specified position.
   */
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(questions[position])
  }

  /**
   * Deletes current to do item from list
   */
  private fun deleteCurrentTodoItem(viewHolder: RecyclerView.ViewHolder) {
    removedPosition = viewHolder.adapterPosition
    removedItem = questions[viewHolder.adapterPosition]

    questions.removeAt(viewHolder.adapterPosition)
    notifyItemRemoved(viewHolder.adapterPosition)
  }

  /**
   * retrieves deleted item
   */
  private fun undoDelete() {
    questions.add(
      removedPosition,
      removedItem
    )
    notifyItemInserted(removedPosition)
  }

  /**
   * shows snack message
   */
  private fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
  }

  /**
   * check if the question was correct or not
   */
  fun correctOrNot(viewHolder: RecyclerView.ViewHolder, trueOrFalse: Boolean) {
    val question = questions[viewHolder.adapterPosition]

    if (trueOrFalse == question.correctOrFalse) {
      deleteCurrentTodoItem(viewHolder)
      viewHolder.itemView.snack("The answer you gave was correct!!")
    }

    if (!trueOrFalse == question.correctOrFalse) {
      deleteCurrentTodoItem(viewHolder)
      undoDelete()
      viewHolder.itemView.snack("The answer you gave was not correct!")
    }
  }
}
