package matixar.mystockhub.ui.crypto

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
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.entities.Crypto
import matixar.mystockhub.databinding.FragmentCryptoDetailsBinding
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CryptoDetailsFragment : Fragment() {
    private var coin: Coin? = null
    private var param2: String? = null

    private var _binding: FragmentCryptoDetailsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CryptoViewModel by lazy {
        ViewModelProvider(this,
            CryptoViewModelFactory((activity?.application as MyStockHubApplication).cryptoRepository)
        ).get(CryptoViewModel::class.java)
    }

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
    ): View {
        _binding = FragmentCryptoDetailsBinding.inflate(layoutInflater,container,false)

        initData()
        initButton()

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
        binding.cryptoDetailsBasicInfoPriceChange.text = String.format("%.4f%",priceChange * rate)
        if(priceChange > 0)
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_profit)
        else if(priceChange < 0)
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_arrow_loss)
        else
            binding.cryptoDetailsBasicInfoPriceChangeImageview.setImageResource(R.drawable.ic_no_change)


    }

    private fun initButton() {
        binding.cryptoDetailsButtonBuy.setOnClickListener {
            val edittext = EditText(context)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Enter amount")
            builder.setView(edittext)
            builder.setPositiveButton("Buy", DialogInterface.OnClickListener { dialogInterface, i ->
                if(edittext.text.toString().toFloatOrNull() != null) {
                    val crypto = Crypto(coin = coin!!, amount = edittext.text.toString().toFloat(), currentPrice = coin!!.price.toFloat(), purchaseDate = Date())
                    viewModel.insert(crypto)
                    Toast.makeText(context, "Bought " + coin!!.name + " for " + coin!!.price, Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Not a number", Toast.LENGTH_SHORT).show()
            }).show()
        }
    }
}