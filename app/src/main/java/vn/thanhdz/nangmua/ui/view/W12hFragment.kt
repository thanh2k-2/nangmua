package vn.thanhdz.nangmua.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import vn.thanhdz.nangmua.R
import vn.thanhdz.nangmua.ui.adapter.Forecast12hAdapter
import vn.thanhdz.nangmua.ui.viewmodel.Forecast12hViewModel

class W12hFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : Forecast12hAdapter
    private lateinit var viewModel: Forecast12hViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = Forecast12hViewModel()
        adapter = Forecast12hAdapter(mutableListOf())
        viewModel.get().observe(this) {
            if(it==null) {
                adapter.update(mutableListOf())
            }else{
                adapter.update(it)
            }
        }
        viewModel.loadDataForecast12h(353412)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_w12h, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_w12house)
        recyclerView.adapter = adapter
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

    }
}