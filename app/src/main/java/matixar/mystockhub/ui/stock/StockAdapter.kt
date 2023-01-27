package matixar.mystockhub.ui.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.API.models.SearchResultModel
import matixar.mystockhub.R

class StockAdapter(val fragmentFunction: (name: String) -> (Unit)) :
    RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    var dataSet = mutableListOf<SearchResultModel>()
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: View
        val itemCode: TextView
        val fullName: TextView

        fun bind(dataSet: List<SearchResultModel>) {
            itemCode.text = dataSet[position].symbol
            fullName.text = dataSet[position].name
        }

        init {
            // Define click listener for the ViewHolder's View
            content = view.rootView
            itemCode = view.findViewById(R.id.stock_search_p_code)
            fullName = view.findViewById(R.id.stock_search_p_full_name)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.stock_search_p, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.itemCode.text = dataSet[position].symbol
        viewHolder.fullName.text = dataSet[position].name
        viewHolder.content.setOnClickListener {
            fragmentFunction(dataSet[position].symbol)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: List<SearchResultModel>) {
        this.dataSet = dataSet.toMutableList()
        notifyDataSetChanged()
    }

}
