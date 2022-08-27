package com.example.android.mockapiresponse

data class Property(
    val address: Address,
    val attributes: List<Attribute>,
    val description: String,
    val documents: List<Document>,
    val features: List<String>,
    val id: String,
    val pictures: List<String>,
    val posted_date_time: String,
    val price: Price,
    val title: String,
    val visits: Int
)