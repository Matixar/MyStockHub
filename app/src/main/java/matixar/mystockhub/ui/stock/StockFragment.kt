package matixar.mystockhub.ui.stock

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.Stock
import java.util.*

class StockFragment : Fragment() {

    companion object {
        fun newInstance() = StockFragment()
    }

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this,StockViewModelFactory((activity?.application as MyStockHubApplication).stockRepository)).get(StockViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.stock_recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)

        recyclerView?.adapter = StockAdapter(this::openStockDetails)

        //recyclerView.adapter = viewModel.allStocks.value?.let { StockAdapter(it) }
        viewModel.allStocks.observe(viewLifecycleOwner) { stocks ->
            stocks?.let { (recyclerView.adapter as StockAdapter?)?.updateDataSet(it) }
            println("test observer")
        }
        view.findViewById<Button>(R.id.stock_search_button).setOnClickListener {
            val text = view.findViewById<TextInputEditText>(R.id.stock_search_edittext).text.toString()
            viewModel.searchStocks(text)
            //viewModel.allStocks.value?.let {(recyclerView.adapter as StockAdapter?)?.updateDataSet(it) }
        }
        return view
    }

    private fun openStockDetails(name: String) {
        runBlocking {
            launch {
                viewModel.getStockData(name)
            }
            launch {
                val bundle = Bundle()
                viewModel.stockData.value?.let { bundle.putSerializable("param1",it)
                    view?.findNavController()?.navigate(R.id.nav_stock_details, bundle)}
            }
        }
    }

}