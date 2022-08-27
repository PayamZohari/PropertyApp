package com.example.android.mockapiresponse

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class FeatureItemAdapter(private val context: Activity, private val features: ArrayList<String>) :
    BaseAdapter() {
    private lateinit var feature: TextView

    override fun getCount(): Int {
        return features.size
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
            LayoutInflater.from(context).inflate(R.layout.feature_item, parent, false)
        feature = thisiConvertView.findViewById(R.id.feature_text_view)
        feature.setText(features[position])
        return thisiConvertView
    }
}