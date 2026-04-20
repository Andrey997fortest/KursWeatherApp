package ru.istu.kursgoldenringweather.ui.main

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.istu.kursgoldenringweather.R
import ru.istu.kursgoldenringweather.data.model.GoldenRingCity
import ru.istu.kursgoldenringweather.databinding.ItemCityBinding

class CityViewHolder(
    private val binding: ItemCityBinding,
    private val onCityClicked: (GoldenRingCity) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GoldenRingCity, isSelected: Boolean) = with(binding) {
        val context = root.context
        titleText.text = context.getString(item.nameResId)

        val bgColor = if (isSelected) {
            R.color.city_chip_selected_bg
        } else {
            R.color.city_chip_unselected_bg
        }
        val strokeColor = if (isSelected) {
            R.color.city_chip_selected_stroke
        } else {
            R.color.city_chip_unselected_stroke
        }
        val textColor = if (isSelected) {
            R.color.city_chip_selected_text
        } else {
            R.color.city_chip_unselected_text
        }

        cardView.setCardBackgroundColor(ContextCompat.getColor(context, bgColor))
        cardView.strokeColor = ContextCompat.getColor(context, strokeColor)
        titleText.setTextColor(ContextCompat.getColor(context, textColor))
        root.setOnClickListener { onCityClicked(item) }
    }
}
