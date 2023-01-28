package matixar.mystockhub.ui.gold

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R

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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}