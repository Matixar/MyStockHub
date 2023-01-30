package matixar.mystockhub.ui.gold

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.entities.GoldEntity
import java.util.*

class GoldFragment : Fragment() {

    companion object {
        fun newInstance() = GoldFragment()
    }

    private val viewModel: GoldViewModel by lazy { 
        ViewModelProvider(this,
            GoldViewModelFactory((activity?.application as MyStockHubApplication).goldRepository)
        ).get(GoldViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gold, container, false)
        val priceToday = view.findViewById<TextView>(R.id.gold_f_price_now)
        val priceYesterday = view.findViewById<TextView>(R.id.gold_f_price_yesterday)
        val changePercent = view.findViewById<TextView>(R.id.gold_f_price_change_percent)
        val button = view.findViewById<Button>(R.id.gold_f_button_buy)

        viewModel.goldList.observe(viewLifecycleOwner) { goldList ->
            goldList?.let {
                val preferences = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
                val rate = 1 / preferences.getFloat("currency_" + preferences.getString("currency","PLN"),1F)

                priceToday.text = String.format("%.2f",goldList[0].price * rate)
                priceYesterday.text = String.format("%.2f",goldList[1].price * rate)
                val change = ((goldList[0].price * rate) - (goldList[1].price * rate))/(goldList[0].price * rate) * 100
                changePercent.text = String.format("%.2f",change) + "%"
                if(change > 0)
                    changePercent.setTextColor(resources.getColor(R.color.green))
                else if (change < 0)
                    changePercent.setTextColor(resources.getColor(R.color.red))
            }
        }
        viewModel.getGoldListPrice()

        button.setOnClickListener {
            val edittext = EditText(context)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Enter amount")
            builder.setView(edittext)
            builder.setPositiveButton("Buy", DialogInterface.OnClickListener { dialogInterface, i ->
                if(edittext.text.toString().toFloatOrNull() != null) {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
                    val rate = 1 / preferences.getFloat("currency_" + preferences.getString("currency","PLN"),1F)
                    val gold = GoldEntity(gold = viewModel.goldList.value!![0], amount = edittext.text.toString().toFloat(), currentPrice = viewModel.goldList.value!![0].price, purchaseDate = Date())
                    viewModel.insert(gold)
                    Toast.makeText(context, "Bought gold for " + gold.currentPrice * rate, Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Not a number", Toast.LENGTH_SHORT).show()
            }).show()
        }
        return view
    }



}