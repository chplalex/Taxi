package com.chplalex.taxi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.taxi.R
import com.chplalex.taxi.data.TaxiUiModel

class ChooseTaxiAdapter(private val list: List<TaxiUiModel>) :
    RecyclerView.Adapter<ChooseTaxiAdapter.MainViewHolder>() {

    private lateinit var taxiNames: Array<String>
    private lateinit var taxiImages: IntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        taxiNames = arrayOf(
            parent.context.resources.getString(R.string.taxi_name_0),
            parent.context.resources.getString(R.string.taxi_name_1),
            parent.context.resources.getString(R.string.taxi_name_2),
            parent.context.resources.getString(R.string.taxi_name_3),
        )

        taxiImages = intArrayOf(
            R.drawable.ic_taxi_0_logo,
            R.drawable.ic_taxi_1_logo,
            R.drawable.ic_taxi_2_logo,
            R.drawable.ic_taxi_3_logo,
        )

        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.taxi_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val taxiLogo = view.findViewById<ImageView>(R.id.taxi_item_image_view)
        private val taxiName = view.findViewById<TextView>(R.id.taxi_item_name)
        private val taxiEta = view.findViewById<TextView>(R.id.taxi_item_eta)

        fun bind(item: TaxiUiModel) {
            val taxiNameText = getTaxiNameById(item.taxiId)

            taxiLogo.setImageResource(getTaxiImageResourceById(item.taxiId))
            taxiName.text = taxiNameText
            taxiEta.text = item.etaString

            itemView.setOnClickListener {
                showToast(taxiNameText)
            }
        }

        private fun showToast(taxiName: String?) {
            Toast.makeText(
                itemView.context,
                itemView.resources.getString(R.string.choose_taxi_item_toast, taxiName),
                Toast.LENGTH_SHORT
            ).show()
        }

        private fun getTaxiNameById(taxiId: Int): String? {
            return try {
                taxiNames[taxiId]
            } catch (_: Error) {
                null
            }
        }
    }

    private fun getTaxiImageResourceById(taxiId: Int): Int {
        return try {
            taxiImages[taxiId]
        } catch (_: Error) {
            -1
        }
    }
}