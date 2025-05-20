package vn.thanhdz.nangmua.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.database.LocRoomDatabase
import vn.thanhdz.nangmua.databinding.ActivityFavoriteLocationBinding
import vn.thanhdz.nangmua.ui.adapter.FavoriteLocationAdapter

class FavoriteLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteLocationBinding
    private lateinit var adapter: FavoriteLocationAdapter
    private lateinit var locDb: LocRoomDatabase

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locDb = LocRoomDatabase.getDataBase(this)
        adapter = FavoriteLocationAdapter(mutableListOf(), locDb)
        binding.apply {
            rvFavoriteList.adapter = adapter
            rvFavoriteList.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        getAll()
    }

    private fun getAll() {
        CoroutineScope(Dispatchers.Main).launch {
            val locations = locDb.locDao().getAllLocation()
            locations.let {
                if (it != null) {
                    adapter.updateData(it)
                } else {
                    adapter.updateData(mutableListOf())
                }

            }
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        Toast.makeText(this, "Không thể quay lại!", Toast.LENGTH_SHORT).show()
    }

}