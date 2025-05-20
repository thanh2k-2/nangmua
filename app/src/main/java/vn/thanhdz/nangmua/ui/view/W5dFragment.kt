package vn.thanhdz.nangmua.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import vn.thanhdz.nangmua.R
import vn.thanhdz.nangmua.ui.adapter.Forecast5dAdapter
import vn.thanhdz.nangmua.ui.viewmodel.Forecast5dViewModel

class W5dFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Forecast5dAdapter
    private lateinit var viewModel: Forecast5dViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = Forecast5dViewModel()
        adapter = Forecast5dAdapter(mutableListOf())
        viewModel.weather.observe(this) {
            if(it==null) {
                adapter.updateData(mutableListOf())
            } else{
                adapter.updateData(it)
            }
        }
        viewModel.loadDataForecast5d(353412)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_w5d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_w5day)
        recyclerView.adapter = adapter
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)
    }


}