package com.example.android.mockapiresponse

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DocumentItemsAdapter(
    private val context: Activity,
    private val documents: ArrayList<Document>
) : BaseAdapter() {
    private lateinit var title: TextView
    private lateinit var link: String

    override fun getCount(): Int {
        return documents.size
    }

    override fun getItem(position: Int): Any {
        return documents[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getLink(): String {
        return this.link
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var thisiConvertView = convertView
        thisiConvertView =
            LayoutInflater.from(context).inflate(R.layout.document_item, parent, false)
        title = thisiConvertView.findViewById(R.id.document_title_text_view)
        title.setText(documents[position].title)
        link = documents[position].link
        return thisiConvertView
    }
}