package matixar.mystockhub.ui.investments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R

class OwnInvestmentsFragment : Fragment() {

    companion object {
        fun newInstance() = OwnInvestmentsFragment()
    }

    private val ownInvestmentsViewModel: OwnInvestmentsViewModel by viewModels {
        OwnInvestmentsViewModelFactory((activity?.application as MyStockHubApplication).investmentsRepository,
            (activity?.application as MyStockHubApplication).stockRepository,
            (activity?.application as MyStockHubApplication).cryptoRepository,
            (activity?.application as MyStockHubApplication).goldRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_own_investments, container, false)

        val stockRecyclerView = view.findViewById<RecyclerView>(R.id.own_investments_f_stocks)
        val cryptoRecyclerView = view.findViewById<RecyclerView>(R.id.own_investments_f_crypto)
        val goldRecyclerView = view.findViewById<RecyclerView>(R.id.own_investments_f_gold)

        val passwordEditText = view.findViewById<EditText>(R.id.own_investments_f_password)

        passwordEditText.doOnTextChanged { text, start, before, count ->
            val preferences = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
            if(preferences.getString("password","") == passwordEditText.text.toString()) {
                stockRecyclerView.visibility = View.VISIBLE
                cryptoRecyclerView.visibility = View.VISIBLE
                goldRecyclerView.visibility = View.VISIBLE
                passwordEditText.visibility = View.GONE
                view.findViewById<TextView>(R.id.own_investments_f_password_textview).visibility = View.GONE
                val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        stockRecyclerView.layoutManager = LinearLayoutManager(view.context)
        cryptoRecyclerView.layoutManager = LinearLayoutManager(view.context)
        goldRecyclerView.layoutManager = LinearLayoutManager(view.context)

        val stockAdapter = OwnStocksAdapter()
        val cryptoAdapter = OwnCryptoAdapter()
        val goldAdapter = OwnGoldAdapter()

        stockRecyclerView.adapter = stockAdapter
        cryptoRecyclerView.adapter = cryptoAdapter
        goldRecyclerView.adapter = goldAdapter

        ownInvestmentsViewModel.allStock.observe(viewLifecycleOwner) { stocks ->
            stocks.let {
                it.forEach {
                    ownInvestmentsViewModel.getStockData(it.stockApiModel.symbol)
                }
                stockAdapter.submitList(it)
            }
        }

        ownInvestmentsViewModel.allCrypto.observe(viewLifecycleOwner) { crypto ->
            crypto.let {
                it.forEach {
                    ownInvestmentsViewModel.getCoinData(it.coin.symbol)
                }
                cryptoAdapter.submitList(it)
            }
        }

        ownInvestmentsViewModel.allGold.observe(viewLifecycleOwner) { gold ->
            gold.let { goldAdapter.submitList(it) }
        }

        ownInvestmentsViewModel.coin.observe(viewLifecycleOwner) { coin ->
            ownInvestmentsViewModel.allCrypto.value?.forEach { crypto ->
                if(coin != null && coin.symbol == crypto.coin.symbol && coin.price.toFloat() != crypto.currentPrice) {
                    crypto.currentPrice = coin.price.toFloat()
                    Log.d("Observer", "coin observer triggered with: crypto = $crypto, coin = $coin")
                    ownInvestmentsViewModel.update(crypto)
                }
            }
        }

        ownInvestmentsViewModel.stock.observe(viewLifecycleOwner) { stockApiModel ->
            ownInvestmentsViewModel.allStock.value?.forEach { stock ->
                if(stockApiModel != null && stock.stockApiModel.symbol == stockApiModel.symbol && stock.currentPrice != stockApiModel.price) {
                    stock.currentPrice = stockApiModel.price
                    Log.d("Observer", "stock observer triggered with: stock = $stock, stockApiModel = $stockApiModel")
                    ownInvestmentsViewModel.update(stock)
                }
            }
        }

        ownInvestmentsViewModel.getGoldData()

        return view
    }

}