package com.shipilov.lab3

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyListAdapter (
    context: Activity,
    private val dataSource: MutableList<ListItem>,
    private val coroutineScope: CoroutineScope,
    private val database: AppDatabase
) : ArrayAdapter<ListItem>(context, R.layout.list_item) {
    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): ListItem? = dataSource[position]

    override fun getItemId(position: Int): Long = dataSource[position].id?.toLong() ?: 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        val deleteButton = view.findViewById<Button>(R.id.delete_button)
        val item = dataSource[position]
        checkBox.text = item.task
        checkBox.isChecked = item.status
        updateCheckBoxStyle(checkBox, item.status)

        deleteButton.setOnClickListener {
            coroutineScope.launch {
                database.listItemDao().delete(item)
                dataSource.remove(item)
                notifyDataSetChanged()
            }
        }
        checkBox.setOnCheckedChangeListener {_, isChecked ->
            coroutineScope.launch {
                item.status = isChecked
                database.listItemDao().update(item)
                updateCheckBoxStyle(checkBox, isChecked)
            }
        }
        return view
    }
    private fun updateCheckBoxStyle(checkBox: CheckBox, isChecked: Boolean) {
        if (isChecked) {
            checkBox.setTextColor(context.getColor(R.color.gray))
        } else {
            checkBox.setTextColor(context.getColor(R.color.black))
        }
    }
}