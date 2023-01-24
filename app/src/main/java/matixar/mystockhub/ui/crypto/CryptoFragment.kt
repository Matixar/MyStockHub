package matixar.mystockhub.ui.crypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.Crypto
import matixar.mystockhub.ui.stock.StockAdapter
import matixar.mystockhub.ui.stock.StockViewModel
import matixar.mystockhub.ui.stock.StockViewModelFactory
import java.util.*

class CryptoFragment : Fragment() {
    companion object {
        fun newInstance() = CryptoFragment()
    }

    private val viewModel: CryptoViewModel by lazy {
        ViewModelProvider(this,
            CryptoViewModelFactory((activity?.application as MyStockHubApplication).cryptoRepository)
        ).get(CryptoViewModel::class.java)    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crypto, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.crypto_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = CryptoAdapter(this::openCryptoDetails)

        viewModel.allCoins.observe(viewLifecycleOwner) { coins ->
            coins?.let { (recyclerView.adapter as CryptoAdapter?)?.updateDataSet(it)
            println("test observer")
            }
        }
        viewModel.getAllCoins()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun openCryptoDetails(name: String) {
        kotlin.run {
            viewModel.getCoinData(name)
            val bundle = Bundle()
            viewModel.coin.value?.let { bundle.putSerializable("param1",it)
                view?.findNavController()?.navigate(R.id.nav_crypto_details, bundle)}
        }
    }

    private fun placeholderItems(): List<Crypto> {

        val s1 = Crypto(1,"Bitcoin","BTC",1000F, Calendar.getInstance(),900F)
        val s2 = Crypto(2,"Etherium","ETH",20000F, Calendar.getInstance(),21000F)
        val s3 = Crypto(3,"Dogecoin","DOG",250F, Calendar.getInstance(),250F)
        val s4 = Crypto(4,"Luna","LUN",420F, Calendar.getInstance(),421F)
        val s5 = Crypto(5,"BinanceCoin","BIN",69F, Calendar.getInstance(),80F)
        val s6 = Crypto(6,"FTXCoin","FTX",10F, Calendar.getInstance(),12F)
        return listOf<Crypto>(s1,s2,s3,s4,s5,s6)
    }
}