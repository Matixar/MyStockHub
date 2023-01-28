package matixar.mystockhub.ui.crypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.R
import matixar.mystockhub.databinding.FragmentCryptoDetailsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CryptoDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CryptoDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var coin: Coin? = null
    private var param2: String? = null

    private var _binding: FragmentCryptoDetailsBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getSerializable(ARG_PARAM1) as Coin
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCryptoDetailsBinding.inflate(layoutInflater,container,false)
        initData()
        return binding.root
    }

    private fun initData() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
        val rate = preferences.getFloat("currency_USD", 1F) / preferences.getFloat("currency_" + preferences.getString("currency","USD"),1F)

        binding.cryptoDetailsChipSymbol.text = coin?.symbol
        binding.cryptoDetailsBasicInfoName.text = coin?.name
        binding.cryptoDetailsBasicInfoPrice.text = String.format("%.4f", coin?.price?.toFloat()!! * rate)
        binding.cryptoDetailsAdvancedInfoMarketCap.text = coin?.marketCap
        binding.cryptoDetailsBasicInfoPriceChangePercent.text = coin?.delta24h
        val priceChange = coin?.price?.toFloat()!! * coin?.delta24h?.toFloat()!! / 100F
        binding.cryptoDetailsBasicInfoPriceChange.text = String.format("%.4f",priceChange * rate)
        if(priceChange > 0)
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_profit)
        else if(priceChange < 0)
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_loss)
        else
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_no_change)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CryptoDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CryptoDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}