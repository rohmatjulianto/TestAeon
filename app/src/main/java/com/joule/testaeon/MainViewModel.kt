package com.joule.testaeon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joule.testaeon.apiCall.Retrofit
import com.joule.testaeon.dataClass.Photos
import com.joule.testaeon.db.AppDb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import kotlin.math.log

class MainViewModel : ViewModel() {

    private val _photos = MutableLiveData<ArrayList<Photos>>().apply {
        value = null
    }

    val dataPhotos: LiveData<ArrayList<Photos>> = _photos

    fun getPhotos(db: AppDb) {
        val api = Retrofit.call().create(TypeCode::class.java)
        val call = api.getPhotos()
        call.enqueue(object : Callback<ArrayList<Photos>> {
            override fun onResponse(
                call: Call<ArrayList<Photos>>,
                response: Response<ArrayList<Photos>>
            ) {
                response.body()?.let {
                    for (i in 0 until response.body()!!.size) {
                        val photos = response.body()?.get(i)
                        if (photos?.let { it1 -> db.photosDa().getById(it1.id) } == null) {
                            photos?.let { it1 -> db.photosDa().insertAll(it1) }
                        }
                    }

                    _photos.postValue(ArrayList(db.photosDa().getAll()))
                }

            }

            override fun onFailure(call: Call<ArrayList<Photos>>, t: Throwable) {
                Log.d("yy", "${t.message}")
            }
        })
    }

    fun searchByName(db: AppDb, title:String){
        _photos.value = ArrayList(db.photosDa().searchByTitle(title))
        Log.d("yy", "searchByName: ${ArrayList(db.photosDa().searchByTitle(title))}")
    }



    interface TypeCode {
        @GET("/photos")
        fun getPhotos(): Call<ArrayList<Photos>>
    }
}