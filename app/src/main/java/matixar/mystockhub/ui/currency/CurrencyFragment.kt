package matixar.mystockhub.ui.currency

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R

class CurrencyFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyFragment()
    }

    private val viewModel: CurrencyViewModel by lazy {
        ViewModelProvider(this,
            CurrencyViewModelFactory((activity?.application as MyStockHubApplication).currencyRepository)
        ).get(CurrencyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.currency_recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)

        recyclerView?.adapter = CurrencyAdapter()

        viewModel.currencyListYesterday.observe(viewLifecycleOwner) { currencies ->
            currencies?.let {
                (recyclerView.adapter as CurrencyAdapter?)?.updateDataSet(viewModel.currencyListToday.value!!.rates, it.rates)
            }
        }
        viewModel.getCurrencyRates()
        return view
    }

}