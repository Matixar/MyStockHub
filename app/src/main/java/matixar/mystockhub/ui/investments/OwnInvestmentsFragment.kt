package matixar.mystockhub.ui.investments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
            stocks.let { stockAdapter.submitList(it) }
        }

        ownInvestmentsViewModel.allCrypto.observe(viewLifecycleOwner) { crypto ->
            crypto.let { cryptoAdapter.submitList(it) }
        }

        ownInvestmentsViewModel.allGold.observe(viewLifecycleOwner) { gold ->
            gold.let { goldAdapter.submitList(it) }
        }

        ownInvestmentsViewModel.allStock.value?.forEach { stock ->
            ownInvestmentsViewModel.getStockData(stock.stockApiModel.symbol)
        }
        ownInvestmentsViewModel.getGoldData()
        ownInvestmentsViewModel.allCrypto.value?.forEach { crypto ->
            ownInvestmentsViewModel.getCoinData(crypto.coin.symbol)
        }

        return view
    }

}