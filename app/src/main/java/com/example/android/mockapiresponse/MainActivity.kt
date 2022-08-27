package com.example.android.mockapiresponse

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.android.mockapiresponse.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.HttpException
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var attributesListView: ListView
    private lateinit var docsListView: ListView
    private lateinit var featuresGridView: GridView
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = findViewById(R.id.viewpager)
        attributesListView = findViewById(R.id.attribute_listview)
        docsListView = findViewById(R.id.docs_listview)

        imageList = java.util.ArrayList<String>()




        docsListView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as Document
            var pdfURLString = selectedItem.link
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfURLString));
            startActivity(intent)
        }
        featuresGridView = findViewById(R.id.features_gridview)


        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getProperties()
            } catch (ioException: IOException) {
                Log.e(
                    TAG,
                    "IO exception , which means you might have trouble with your connection ",
                    ioException
                )
                return@launchWhenCreated
            } catch (httpException: HttpException) {
                Log.e(
                    TAG,
                    "HTTP exception , which means the response from api server was unexpected ",
                    httpException
                )
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                var property = response.body()!!
                updateUI(property)
            } else {
                Log.e(TAG, "trouble from api's response")
            }
        }


    }

    private fun MainActivity.updateUI(property: Property) {
        setTitle(property)
        setPrice(property)
        setAddress(property)
        setID(property)
        setViews(property)
        setDate(property)
        setDiscription(property)
        var utilityInstance = setAttributes(property)
        setDocuments(property, utilityInstance)
        setFeatures(property)
        setImages(property)
    }

    private fun setPrice(property: Property) {
        var amountOfPrice = property.price.amount
        var formattedPrice = "%,d".format(amountOfPrice)
        binding.priceTextView.setText("$formattedPrice  " + property.price.currency)
    }

    private fun setAddress(property: Property) {
        binding.addressTextView.setText(property.address.street + ", " + property.address.city)
    }

    private fun setID(property: Property) {
        binding.idTextView.setText("ID: " + property.id)
    }

    private fun setViews(property: Property) {
        var visits = property.visits
        binding.viewsTextView.setText("$visits")
    }

    private fun setDate(property: Property) {
        var date = property.posted_date_time.substring(
            8,
            10
        ) + "." + property.posted_date_time.substring(
            5,
            7
        ) + "." + property.posted_date_time.substring(0, 4)
        binding.dateTextView.setText(date)
    }

    private fun setDiscription(property: Property) {
        binding.descriptionValueTextView.setText(property.description)
    }

    private fun setAttributes(property: Property): Utility {
        var attrAdapter =
            AttributeItemsAdapter(this@MainActivity, property.attributes as ArrayList<Attribute>)
        attributesListView.adapter = attrAdapter
        var utilityInstance = Utility()
        utilityInstance.setListViewHeightBasedOnChildren(attributesListView)
        return utilityInstance
    }

    private fun setDocuments(
        property: Property,
        utilityInstance: Utility
    ) {
        var docsAdapter =
            DocumentItemsAdapter(this@MainActivity, property.documents as ArrayList<Document>)
        docsListView.adapter = docsAdapter
        utilityInstance.setListViewHeightBasedOnChildren(docsListView)
    }

    private fun setImages(property: Property) {
        imageList = property.pictures as ArrayList<String>
        viewPagerAdapter = ViewPagerAdapter(this@MainActivity, imageList)
        viewPager.adapter = viewPagerAdapter
    }

    private fun setFeatures(property: Property) {
        var featuresAdapter =
            FeatureItemAdapter(this@MainActivity, property.features as ArrayList<String>)
        featuresGridView.adapter = featuresAdapter
    }


    private fun setTitle(property: Property) {
        binding.titleTextView.setText(property.title)
    }


}