package vn.thanhdz.nangmua.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.database.LocRoomDatabase
import vn.thanhdz.nangmua.data.model.Loc
import vn.thanhdz.nangmua.databinding.FavoriteItemBinding
import vn.thanhdz.nangmua.ui.view.MainActivity

class FavoriteLocationAdapter(
    private var data: MutableList<Loc>,
    val locDb: LocRoomDatabase
) : RecyclerView.Adapter<FavoriteLocationAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
        fun bind(loc: Loc) {
            binding.tvFavoriteLocation.text =
                loc.locationName + if (loc.country != null) ", ${loc.country}" else ""
            binding.imgDeleteLocation.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    locDb.locDao().delete(loc)
                    data = locDb.locDao().getAllLocation()!!
                    notifyDataSetChanged()
                }
            }
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.putExtra("FAVORITE_KEY", loc.key)
                intent.putExtra("FAVORITE_NAME", loc.locationName)
                intent.putExtra("FAVORITE_COUNTRY", loc.country)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavoriteItemBinding.inflate(
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
    fun updateData(newData: MutableList<Loc>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


}