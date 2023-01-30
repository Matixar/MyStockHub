package matixar.mystockhub.ui.crypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R

class CryptoFragment : Fragment() {
    companion object {
        fun newInstance() = CryptoFragment()
    }

    private val viewModel: CryptoViewModel by lazy {
        ViewModelProvider(this,
            CryptoViewModelFactory((activity?.application as MyStockHubApplication).cryptoRepository)
        ).get(CryptoViewModel::class.java)
    }

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
            }
        }
        viewModel.getAllCoins()
        view.findViewById<Button>(R.id.crypto_search_button).setOnClickListener {
            val text = view.findViewById<TextInputEditText>(R.id.crypto_search_edittext).text.toString()
            if(viewModel.allCoins.value?.find { coin -> (coin.symbol.uppercase() == text.uppercase() || coin.name.uppercase() == text.uppercase()) } != null) {
                openCryptoDetails(viewModel.allCoins.value?.find { coin -> (coin.symbol.uppercase() == text || coin.name.uppercase() == text.uppercase()) }!!.symbol)
            }
            else {
                Toast.makeText(context, "There is no such coin", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }
    private fun openCryptoDetails(name: String) {
        viewModel.openCoinDetailsFragment(name)
        viewModel.coinDataLoaded.observe(viewLifecycleOwner) {
            if(it) {
                val bundle = Bundle()
                viewModel.coin.value?.let { bundle.putSerializable("param1",it)
                    view?.findNavController()?.navigate(R.id.nav_crypto_details, bundle)}
            }
        }
    }
}