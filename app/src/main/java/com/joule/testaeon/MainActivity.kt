package com.joule.testaeon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.joule.testaeon.adapter.PhotosAdapter
import com.joule.testaeon.dataClass.Photos
import com.joule.testaeon.databinding.ActivityMainBinding
import com.joule.testaeon.db.AppDb

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var db:AppDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        db = Room.databaseBuilder(
            applicationContext,
            AppDb::class.java, "data-list"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        viewModel.getPhotos(db)


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p: String?): Boolean {
                if (p?.length != 0){
                    val title = p
                    viewModel.searchByName(db, title as String)
                    Log.d("yy", "onQueryTextChange: $title")
                }else{
//                    back default
                    viewModel.getPhotos(db)
                }
                return false

            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        binding.rvPhotos.setHasFixedSize(true)
        binding.rvPhotos.layoutManager = GridLayoutManager(this, 2)
        viewModel.dataPhotos.observe(this, {
            it?.let {
                binding.rvPhotos.adapter = PhotosAdapter(it)
            }
        })
    }

}