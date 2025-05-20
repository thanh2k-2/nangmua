package vn.thanhdz.nangmua.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import vn.thanhdz.nangmua.databinding.ActivityLocationListBinding
import vn.thanhdz.nangmua.ui.adapter.CityAdapter
import vn.thanhdz.nangmua.ui.viewmodel.CityViewModel

class LocationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationListBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityViewModel = CityViewModel()
        cityAdapter = CityAdapter(mutableListOf())

        cityViewModel.citySearch.observe(this@LocationListActivity) {
            if (it != null) {
                cityAdapter.update(it)
            }
        }

        binding.apply {
            rvLocationList.adapter = cityAdapter
            val layout =
                LinearLayoutManager(this@LocationListActivity, LinearLayoutManager.VERTICAL, false)
            rvLocationList.layoutManager = layout

            editLoaction.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    progressBar2.visibility = View.VISIBLE
                    cityViewModel.loadLocationData(s.toString().trim())
                }
            })
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n   {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}