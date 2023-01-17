package matixar.mystockhub.ui.stock

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.database.LocalDatabase
import matixar.mystockhub.database.Stock
import matixar.mystockhub.database.StockRepository
import java.util.*
import kotlin.collections.ArrayList

class StockFragment : Fragment() {

    companion object {
        fun newInstance() = StockFragment()
    }

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this,StockViewModelFactory((activity?.application as MyStockHubApplication).repository)).get(StockViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.stock_recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        recyclerView?.adapter = StockAdapter()
        //recyclerView.adapter = viewModel.allStocks.value?.let { StockAdapter(it) }
        viewModel.allStocks.observe(viewLifecycleOwner) { stocks ->
            stocks?.let { (recyclerView.adapter as StockAdapter?)?.updateDataSet(it) }
            println("test observer")
        }
        view.findViewById<Button>(R.id.stock_search_button).setOnClickListener {
            val text = view.findViewById<TextInputEditText>(R.id.stock_search_edittext).text.toString()
            viewModel.searchStocks(text)
            println(viewModel.allStocks.value.toString())
            //viewModel.allStocks.value?.let {(recyclerView.adapter as StockAdapter?)?.updateDataSet(it) }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun placeholderItems(): List<Stock> {

        val s1 = Stock(1,"Tesla","TSL",1000F, Calendar.getInstance(),900F)
        val s2 = Stock(2,"WIG20 Polska","WIG20",20000F, Calendar.getInstance(),21000F)
        val s3 = Stock(3,"Microsoft","MIC",250F, Calendar.getInstance(),250F)
        val s4 = Stock(4,"Apple","APL",420F, Calendar.getInstance(),421F)
        val s5 = Stock(5,"CDProjekt Red","CDP",69F, Calendar.getInstance(),80F)
        val s6 = Stock(6,"Nvidia","NVD",10F, Calendar.getInstance(),12F)
        return listOf<Stock>(s1,s2,s3,s4,s5,s6)
    }

}