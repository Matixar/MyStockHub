package matixar.mystockhub.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import matixar.mystockhub.API.models.StockApiModel
import matixar.mystockhub.R
import matixar.mystockhub.databinding.FragmentStockDetailsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StockDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StockDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var data: StockApiModel? = null


    private var _binding: FragmentStockDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.get(ARG_PARAM1) as StockApiModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockDetailsBinding.inflate(layoutInflater,container,false)
        initData()
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
        binding.stockDetailsBasicInfoPriceChangePercent.text = String.format("%.2f",data?.changePercent)
        if(data?.change!! > 0)
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_profit)
        else if(data?.change!! < 0)
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_loss)
        else
            binding.stockDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_no_change)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StockDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(data: StockApiModel) =
            StockDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, data)
                }
            }
    }
}