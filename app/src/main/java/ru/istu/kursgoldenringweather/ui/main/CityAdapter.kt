package ru.istu.kursgoldenringweather.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.istu.kursgoldenringweather.data.model.GoldenRingCity
import ru.istu.kursgoldenringweather.databinding.ItemCityBinding

class CityAdapter(
    private val onCityClicked: (GoldenRingCity) -> Unit,
) : RecyclerView.Adapter<CityViewHolder>() {

    private var items: List<GoldenRingCity> = emptyList()
    private var selectedCityId: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<GoldenRingCity>, selectedCityId: String?) {
        this.items = items
        this.selectedCityId = selectedCityId
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding, onCityClicked)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(items[position], items[position].id == selectedCityId)
    }

    override fun getItemCount(): Int = items.size
}
