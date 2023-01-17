package matixar.mystockhub.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import matixar.mystockhub.API.StockApiModel
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
    private var name: String? = null


    private var _binding: FragmentStockDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.get(ARG_PARAM1) as StockApiModel
            name = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockDetailsBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    private fun initData() {
        binding.stockDetailsAdvancedInfoLow.text = data?.low.toString()
        binding.stockDetailsAdvancedInfoHigh.text = data?.high.toString()
        binding.stockDetailsAdvancedInfoOpen.text = data?.open.toString()
        binding.stockDetailsAdvancedInfoLatestTradingDay.text = data?.latestTradingDay
        binding.stockDetailsAdvancedInfoPreviousClose.text = data?.previousClose.toString()
        binding.stockDetailsBasicInfoName.text = name
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
        fun newInstance(data: StockApiModel, name: String) =
            StockDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, data)
                    putString(ARG_PARAM2, name)
                }
            }
    }
}