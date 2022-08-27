package com.example.android.mockapiresponse

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView

class AttributeItemsAdapter(
    private val context: Activity,
    private val attributes: ArrayList<Attribute>
) : BaseAdapter() {
    private lateinit var label: TextView
    private lateinit var value: TextView

    override fun getCount(): Int {
        return attributes.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var thisiConvertView = convertView
        thisiConvertView =
            LayoutInflater.from(context).inflate(R.layout.attribute_item, parent, false)
        label = thisiConvertView.findViewById(R.id.label_title_text_view)
        value = thisiConvertView.findViewById(R.id.value_title_text_view)
        label.setText(attributes[position].label)
        value.setText(attributes[position].value + " " + if (attributes[position].unit == null) "" else attributes[position].unit)
        return thisiConvertView
    }
}