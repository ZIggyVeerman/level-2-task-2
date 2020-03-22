package com.example.swipequiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager
  private var dataSet = mutableListOf<Question>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    dataSet.add(Question("A 'val' and 'var' are the same", false))
    dataSet.add(Question("Mobile Application Development grants 12 ECTS", false))
    dataSet.add(Question("A Unit in Kotlin corresponds to a void in java", false))
    dataSet.add(Question("In Kotilin 'when' replaces the 'switch' operator in Java", true))

    viewAdapter = QuestionAdapter(dataSet)
    viewManager = LinearLayoutManager(this)

    initViews()
  }

  /**
   * Initialize the Ui of the application
   */
  private fun initViews() {
    rvQuestion.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
    rvQuestion.adapter = viewAdapter
    rvQuestion.addItemDecoration(
      DividerItemDecoration(
        this@MainActivity,
        DividerItemDecoration.VERTICAL
      )
    )
    createItemTouchHelper()
  }

  /**
   * Create a touch helper to recognize when a user swipes an item from a recycler view.
   * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
   * and uses callbacks to signal when a user is performing these actions.
   */
  private fun createItemTouchHelper() {
    val callback =
      object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

        override fun onMove(
          recyclerView: RecyclerView,
          viewHolder: RecyclerView.ViewHolder,
          target: RecyclerView.ViewHolder
        ): Boolean {
          return false
        }
        // on swiped method to check what is being swiped to were
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
          if (position == ItemTouchHelper.RIGHT) {
            (viewAdapter as QuestionAdapter).correctOrNot(viewHolder, true)
          }
          if (position == ItemTouchHelper.LEFT) {
            (viewAdapter as QuestionAdapter).correctOrNot(viewHolder, false)
          }
        }
      }
    val itemTouchHelper = ItemTouchHelper(callback)
    itemTouchHelper.attachToRecyclerView(rvQuestion)
  }
}
