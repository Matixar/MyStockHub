package matixar.mystockhub.ui.currency

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.API.models.Currency
import matixar.mystockhub.R

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    var dataSet = mutableListOf<Currency>()
    var dataSetYesterday = mutableListOf<Currency>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: View
        val name: TextView
        val value: TextView
        val change: TextView

        init {
            // Define click listener for the ViewHolder's View
            content = view.rootView
            name = view.findViewById(R.id.currency_search_p_name)
            value = view.findViewById(R.id.currency_search_p_value)
            change = view.findViewById(R.id.currency_search_p_change)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.currency_search_p, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val preferences = PreferenceManager.getDefaultSharedPreferences(viewHolder.itemView.context.applicationContext)
        val rate = 1 / preferences.getFloat("currency_" + preferences.getString("currency","PLN"),1F)
        
        viewHolder.name.text = dataSet[position].code
        viewHolder.value.text = String.format("%.4f", dataSet[position].value * rate)
        val change = (dataSetYesterday[position].value - dataSet[position].value) * rate
        viewHolder.change.text = String.format("%.4f", change)
        if (change > 0)
            viewHolder.change.setTextColor(Color.GREEN)
        else if (change < 0)
            viewHolder.change.setTextColor(Color.RED)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: List<Currency>, dataSetYesterday: List<Currency>) {
        this.dataSet = dataSet.toMutableList()
        this.dataSetYesterday = dataSetYesterday.toMutableList()
        notifyDataSetChanged()
    }
}