package vn.thanhdz.nangmua.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.thanhdz.nangmua.data.model.CitySearch
import vn.thanhdz.nangmua.databinding.SearchItemBinding
import vn.thanhdz.nangmua.ui.view.MainActivity

class CityAdapter(private var data: MutableList<CitySearch>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(city: CitySearch) {
            binding.textLocation.text = "${city.localizedName}, ${city.country.localizedName}"
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.putExtra("KEY", city.key.toInt())
                intent.putExtra("NAME", city.localizedName)
                intent.putExtra("COUNTRY", city.country.localizedName)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newData: MutableList<CitySearch>) {
        val oldSize = data.size
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}