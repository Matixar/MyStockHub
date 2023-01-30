package matixar.mystockhub.ui.stock

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import matixar.mystockhub.API.models.StockApiModel
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.entities.Stock
import matixar.mystockhub.databinding.FragmentStockDetailsBinding
import java.util.*

private const val ARG_PARAM1 = "param1"

class StockDetailsFragment : Fragment() {
    private var data: StockApiModel? = null


    private var _binding: FragmentStockDetailsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this,StockViewModelFactory((activity?.application as MyStockHubApplication).stockRepository)).get(StockViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.get(ARG_PARAM1) as StockApiModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockDetailsBinding.inflate(layoutInflater,container,false)
        initData()
        initButton()
        return binding.root
    }

    private fun initData() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
        val rate = preferences.getFloat("currency_USD", 1F) / preferences.getFloat("currency_" + preferences.getString("currency","USD"),1F)

        binding.stockDetailsChipSymbol.text = data?.symbol
        binding.stockDetailsAdvancedInfoLow.text = String.format("%.4f",(data?.low?.times(rate)))
        binding.stockDetailsAdvancedInfoHigh.text = String.format("%.4f",(data?.high?.times(rate)))
        binding.stockDetailsAdvancedInfoOpen.text = String.format("%.4f",(data?.open?.times(rate)))
        binding.stockDetailsAdvancedInfoLatestTradingDay.text = data?.latestTradingDay
        binding.stockDetailsAdvancedInfoPreviousClose.text = String.format("%.4f",(data?.previousClose?.times(rate)))
        binding.stockDetailsAdvancedInfoVolume.text = data?.volume.toString()
        binding.stockDetailsBasicInfoPrice.text = String.format("%.4f",(data?.price?.times(rate)))
        binding.stockDetailsBasicInfoPriceChange.text = String.format("%.2f",(data?.change?.times(rate)))
        binding.stockDetailsBasicInfoPriceChangePercent.text = data?.changePercent
        if(data?.change!! > 0)
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_profit)
        else if(data?.change!! < 0)
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_loss)
        else
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_no_change)
    }

    private fun initButton() {
        binding.stockDetailsButtonBuy.setOnClickListener {
            val edittext = EditText(context)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Enter amount")
            builder.setView(edittext)
            builder.setPositiveButton("Buy", DialogInterface.OnClickListener { dialogInterface, i ->
                if(edittext.text.toString().toFloatOrNull() != null) {
                    val stock = Stock(stockApiModel = data!!, amount = edittext.text.toString().toFloat(), currentPrice = data!!.price, purchaseDate = Date())
                    viewModel.insert(stock)
                    Toast.makeText(context, "Bought " + data!!.symbol + " for " + data!!.price, Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Not a number", Toast.LENGTH_SHORT).show()
            }).show()
        }
    }
}