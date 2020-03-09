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

  private lateinit var  viewAdapter: RecyclerView.Adapter<*>
  private lateinit var  viewManager: RecyclerView.LayoutManager
  private var dataSet = mutableListOf<Question>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    dataSet.add(Question("is het mooi weer? ", false))
    dataSet.add(Question("Joost", true))

    viewAdapter = QuestionAdapter(dataSet)
    viewManager = LinearLayoutManager(this)

    initViews()
  }

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

  private fun createItemTouchHelper() {
    val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or  ItemTouchHelper.LEFT) {

        override fun onMove(
          recyclerView: RecyclerView,
          viewHolder: RecyclerView.ViewHolder,
          target: RecyclerView.ViewHolder
        ): Boolean {
          return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
          if (position == ItemTouchHelper.RIGHT){
            (viewAdapter as QuestionAdapter).correctOrNot(viewHolder, true)
//            (viewAdapter as QuestionAdapter).deleteCurrentTodoItem(viewHolder)
          }
          if (position == ItemTouchHelper.LEFT) {
            (viewAdapter as QuestionAdapter).correctOrNot(viewHolder, false)
//            (viewAdapter as QuestionAdapter).deleteCurrentTodoItem(viewHolder)

          }
        }
      }
    val itemTouchHelper = ItemTouchHelper(callback)
    itemTouchHelper.attachToRecyclerView(rvQuestion)

  }
}
