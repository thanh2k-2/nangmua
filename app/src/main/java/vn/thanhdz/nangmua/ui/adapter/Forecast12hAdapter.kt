package vn.thanhdz.nangmua.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.thanhdz.nangmua.data.repository.UpLoadImg
import vn.thanhdz.nangmua.data.model.HourlyForecast
import vn.thanhdz.nangmua.data.repository.DateTimeConverter
import vn.thanhdz.nangmua.databinding.FragmentW12hItemBinding

class Forecast12hAdapter(private val data: MutableList<HourlyForecast>) :
    RecyclerView.Adapter<Forecast12hAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentW12hItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(weather: HourlyForecast) {

            Glide.with(binding.root)
                .load(UpLoadImg().load(weather.weatherIcon))
                .into(binding.w12hImage)
            binding.tvW12hTime.text = DateTimeConverter().toTime(weather.date)
            binding.tvW12hTemperature.text = weather.temperature.value.toInt().toString() + "°"
            binding.tvW12hRainProbability.text = weather.rainProbability.toString() + "%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentW12hItemBinding.inflate(
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

    fun update(it: MutableList<HourlyForecast>) {
        data.clear()
        data.addAll(it)
        notifyItemRangeChanged(0, data.size)

    }


}