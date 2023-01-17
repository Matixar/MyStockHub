package matixar.mystockhub.ui.crypto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.R
import matixar.mystockhub.database.Crypto

class CryptoAdapter(private val dataSet: List<Crypto>) :
RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val content: View
            val itemCode: TextView
            val fullName: TextView
            val statusIcon: ImageView
            val currentValue: TextView
            val valueChange: TextView

            init {
                // Define click listener for the ViewHolder's View
                content = view.rootView
                content.setOnClickListener {
                    Toast.makeText(view.context, "TODO", Toast.LENGTH_SHORT).show()

                }
                itemCode = view.findViewById(R.id.item_p_code)
                fullName = view.findViewById(R.id.item_p_full_name)
                statusIcon = view.findViewById(R.id.item_p_status_icon)
                currentValue = view.findViewById(R.id.item_p_current_value)
                valueChange = view.findViewById(R.id.item_p_value_change)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_p, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.itemCode.text = dataSet[position].searchString
            viewHolder.fullName.text = dataSet[position].currencyName
            viewHolder.currentValue.text = String.format("%.2f", dataSet[position].currentValue)
            val difference = dataSet[position].currentValue!! - dataSet[position].previousValue!!
            if(difference > 0)
                viewHolder.statusIcon.setImageResource(R.drawable.ic_arrow_profit)
            else if(difference < 0)
                viewHolder.statusIcon.setImageResource(R.drawable.ic_arrow_loss)
            else
                viewHolder.statusIcon.setImageResource(R.drawable.ic_no_change)
            val percent = (difference/dataSet[position].previousValue!!)*100
            viewHolder.valueChange.text = String.format("%.2f", percent) + "%"
            if(percent > 0)
                viewHolder.valueChange.setTextColor(R.color.green)
            else if (percent < 0)
                viewHolder.valueChange.setTextColor(R.color.red)

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size
}