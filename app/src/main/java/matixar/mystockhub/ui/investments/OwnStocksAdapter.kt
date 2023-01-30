package matixar.mystockhub.ui.investments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.R
import matixar.mystockhub.database.entities.Stock
import java.text.SimpleDateFormat

class OwnStocksAdapter: ListAdapter<Stock, OwnStocksAdapter.OwnStocksViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnStocksViewHolder {
        return OwnStocksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OwnStocksViewHolder, position: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.context.applicationContext)
        val rate = preferences.getFloat("currency_USD", 1F) / preferences.getFloat("currency_" + preferences.getString("currency","USD"),1F)
        val current = getItem(position)
        holder.bind(current.stockApiModel.symbol,current.amount.toString(),
            (current.amount * current.stockApiModel.price * rate).toString(),
        SimpleDateFormat("yyyy-MM-dd").format(current.purchaseDate),
            (current.currentPrice * rate).toString(), (((current.amount * current.currentPrice) -(current.amount * current.stockApiModel.price)) * rate).toString())
    }

    class OwnStocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.own_investments_p_name)
        private val amount: TextView = itemView.findViewById(R.id.own_investments_p_amount)
        private val value: TextView = itemView.findViewById(R.id.own_investments_p_value)
        private val purchaseDate: TextView = itemView.findViewById(R.id.own_investments_p_date)
        private val currentPrice: TextView = itemView.findViewById(R.id.own_investments_p_current_price)
        private val change: TextView = itemView.findViewById(R.id.own_investments_p_change)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.own_investments_p_buttonDelete)
        private val editButton: ImageButton = itemView.findViewById(R.id.own_investments_p_buttonEdit)

        fun bind(name: String?, amount: String?, value: String?, purchaseDate: String?, currentPrice: String?, change: String?) {
            this.name.text = name
            this.amount.text = amount
            this.value.text = value
            this.purchaseDate.text = purchaseDate
            this.currentPrice.text = currentPrice
            this.change.text = change
            deleteButton.setOnClickListener {
                Toast.makeText(itemView.context, "TODO", Toast.LENGTH_SHORT).show()
            }
            editButton.setOnClickListener {
                Toast.makeText(itemView.context, "TODO", Toast.LENGTH_SHORT).show()
            }
        }

        companion object {
            fun create(parent: ViewGroup): OwnStocksViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.own_investments_p, parent, false)
                return OwnStocksViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem.stockApiModel == newItem.stockApiModel
                        && oldItem.purchaseDate == newItem.purchaseDate
                        && oldItem.amount == newItem.amount
                        && oldItem.currentPrice == newItem.currentPrice
            }
        }
    }
}