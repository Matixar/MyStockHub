package matixar.mystockhub.ui.gold

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.get
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
                priceToday.text = goldList[0].price.toString()
                priceYesterday.text = goldList[1].price.toString()
                val change = (goldList[0].price - goldList[1].price)/goldList[0].price * 100
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