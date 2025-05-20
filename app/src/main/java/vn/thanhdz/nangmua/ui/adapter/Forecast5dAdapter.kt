package vn.thanhdz.nangmua.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.thanhdz.nangmua.data.repository.UpLoadImg
import vn.thanhdz.nangmua.data.model.DailyForecast
import vn.thanhdz.nangmua.data.repository.DateTimeConverter
import vn.thanhdz.nangmua.databinding.FragmentW5dItemBinding
import java.util.Calendar

class Forecast5dAdapter(
    private val data: MutableList<DailyForecast>,
) :
    RecyclerView.Adapter<Forecast5dAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentW5dItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weather: DailyForecast) {
            val calendar = Calendar.getInstance()
            Glide.with(binding.root)
                .load(
                    UpLoadImg().load(
                        weather.icon
                    )
                )
                .into(binding.imgW5dIcWeather)

            binding.tvW5dDate.text = DateTimeConverter().toDate(weather.date)
            binding.tvW5dRainProbability.text =
                ((weather.day.rainProbability + weather.night.rainProbability) / 2).toString() + "%"
            binding.tvW5dTemMin.text = weather.temperature.minimum.value.toInt().toString() + "°"
            binding.tvW5dTemMax.text = weather.temperature.maximum.value.toInt().toString() + "°"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentW5dItemBinding.inflate(
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

    fun updateData(dataNew: MutableList<DailyForecast>) {

        data.clear()
        data.addAll(dataNew)
        notifyItemRangeChanged(0, dataNew.size)
    }
}