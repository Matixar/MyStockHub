package matixar.mystockhub.ui.calculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import matixar.mystockhub.API.models.Currency
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.R
import matixar.mystockhub.databinding.FragmentCalculatorBinding
import matixar.mystockhub.ui.currency.CurrencyViewModel
import matixar.mystockhub.ui.currency.CurrencyViewModelFactory

class CalculatorFragment : Fragment() {

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    private var _binding: FragmentCalculatorBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CalculatorViewModel by lazy {
        ViewModelProvider(this,
            CalculatorViewModelFactory((activity?.application as MyStockHubApplication).currencyRepository)
        ).get(CalculatorViewModel::class.java)
    }

    private var firstCurrency: Currency? = null
    private var secondCurrency: Currency? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater,container,false)

        viewModel.getCurrencyList()

        initChips()
        initSwapButton()
        initTextWatchers()


        return binding.root
    }

    private fun initTextWatchers() {
        //i have no idea what is going on with those text watchers, doesn't work instantly, only when pressing buttons
        binding.calculatorFFirstcurrencyEdittext.doOnTextChanged { text, start, before, count ->
            firstCurrency = if (text.toString().length == 3)
                if (text.toString().uppercase() == "PLN")
                    Currency("Polski złoty","PLN",1F)
                else if(viewModel.currencyList.value?.rates?.firstOrNull { it.code == text.toString().uppercase() } != null)
                    viewModel.currencyList.value?.rates?.first { it.code == text.toString().uppercase() }
                else
                    null
            else
                null
            if(secondCurrency != null && firstCurrency != null)
                if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                    calculateRate()
                }
                else
                    binding.calculatorFCurrencyvalueTextview.text = ""
        }
        binding.calculatorFFirstcurrencyEdittext.doAfterTextChanged {
            if(secondCurrency != null && firstCurrency != null)
                if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                    calculateRate()
                }
                else
                    binding.calculatorFCurrencyvalueTextview.text = ""
        }

        binding.calculatorFSecondcurrencyEdittext.doOnTextChanged { s, start, before, count ->
            secondCurrency = if (s.toString().length == 3)
                if (s.toString().uppercase() == "PLN")
                    Currency("Polski złoty","PLN",1F)
                else if(viewModel.currencyList.value?.rates?.firstOrNull { it.code == s.toString().uppercase() } != null)
                    viewModel.currencyList.value?.rates?.first { it.code == s.toString().uppercase() }
                else
                    null
            else
                null
            if(secondCurrency != null && firstCurrency != null)
                if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                    calculateRate()
                }
                else
                    binding.calculatorFCurrencyvalueTextview.text = ""
        }
        binding.calculatorFSecondcurrencyEdittext.doAfterTextChanged {
            if(secondCurrency != null && firstCurrency != null)
                if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                    calculateRate()
                }
                else
                    binding.calculatorFCurrencyvalueTextview.text = ""
        }

        binding.calculatorFCurrencyvalueEdittext.doOnTextChanged { s, start, before, count ->
            if(secondCurrency != null && firstCurrency != null)
                if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                    calculateRate()
                }
            else
                binding.calculatorFCurrencyvalueTextview.text = ""
        }
        //this somehow doesn't work
        /*binding.calculatorFFirstcurrencyEdittext.addTextChangedListener { object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println("test1")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("test first currency")
                firstCurrency = if (count == 3)
                    if(viewModel.currencyList.value?.rates?.any { it.code == s.toString().uppercase() } != null)
                        viewModel.currencyList.value?.rates?.first { it.code == s.toString().uppercase() }
                    else if (s.toString().uppercase() == "PLN")
                        Currency("Polski złoty","PLN",1F)
                    else
                        null
                else
                    null
            }

            override fun afterTextChanged(p0: Editable?) {
                println("test firstcurrency after")
                if(secondCurrency != null && firstCurrency != null)
                    if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                        calculateRate()
                    }
            }

        } }

        binding.calculatorFSecondcurrencyEdittext.addTextChangedListener { object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println("test2")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("test secondcurrency")
                secondCurrency = if (count == 3)
                    if(viewModel.currencyList.value?.rates?.any { it.code == s.toString().uppercase() } != null)
                        viewModel.currencyList.value?.rates?.first { it.code == s.toString().uppercase() }
                    else if(s.toString().uppercase() == "PLN")
                        Currency("Polski złoty", "PLN", 1F)
                    else
                        null
                else
                    null
            }

            override fun afterTextChanged(p0: Editable?) {
                println("test secondcurrency after")
                if(secondCurrency != null && firstCurrency != null)
                    if(binding.calculatorFCurrencyvalueEdittext.text.toString().isNotEmpty()) {
                        calculateRate()
                    }
            }

        } }

        binding.calculatorFCurrencyvalueEdittext.addTextChangedListener { object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println("test3")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println("test value")
                if(secondCurrency != null && firstCurrency != null)
                    if(!p0.isNullOrEmpty()) {
                        calculateRate()
                    }
            }

            override fun afterTextChanged(p0: Editable?) {
                println("test4")
            }

        } }*/
    }

    private fun calculateRate() {
        val value = binding.calculatorFCurrencyvalueEdittext.text.toString().toFloat()
        val crossRate = firstCurrency!!.value / secondCurrency!!.value
        binding.calculatorFCurrencyvalueTextview.text = String.format("%.2f", value * crossRate)
    }

    private fun initSwapButton() {
        binding.calculatorFSwapcurrenciesButton.setOnClickListener {
            val temp = binding.calculatorFFirstcurrencyEdittext.text
            binding.calculatorFFirstcurrencyEdittext.text = binding.calculatorFSecondcurrencyEdittext.text
            binding.calculatorFSecondcurrencyEdittext.text = temp
        }
    }

    private fun initChips() {
        binding.calculatorFChipEurchf.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("EUR")
            binding.calculatorFSecondcurrencyEdittext.setText("CHF")
        }
        binding.calculatorFChipEurgbp.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("EUR")
            binding.calculatorFSecondcurrencyEdittext.setText("GBP")
        }
        binding.calculatorFChipGbpchf.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("GBP")
            binding.calculatorFSecondcurrencyEdittext.setText("CHF")
        }
        binding.calculatorFChipPlnchf.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("PLN")
            binding.calculatorFSecondcurrencyEdittext.setText("CHF")
        }
        binding.calculatorFChipPlneur.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("PLN")
            binding.calculatorFSecondcurrencyEdittext.setText("EUR")
        }
        binding.calculatorFChipPlngbp.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("PLN")
            binding.calculatorFSecondcurrencyEdittext.setText("GBP")
        }
        binding.calculatorFChipPlnusd.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("PLN")
            binding.calculatorFSecondcurrencyEdittext.setText("USD")
        }
        binding.calculatorFChipUsdchf.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("USD")
            binding.calculatorFSecondcurrencyEdittext.setText("CHF")
        }
        binding.calculatorFChipUsdcny.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("USD")
            binding.calculatorFSecondcurrencyEdittext.setText("CNY")
        }
        binding.calculatorFChipUsdeur.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("USD")
            binding.calculatorFSecondcurrencyEdittext.setText("EUR")
        }
        binding.calculatorFChipUsdgbp.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("USD")
            binding.calculatorFSecondcurrencyEdittext.setText("GBP")
        }
        binding.calculatorFChipUsdjpy.setOnClickListener {
            binding.calculatorFFirstcurrencyEdittext.setText("USD")
            binding.calculatorFSecondcurrencyEdittext.setText("JPY")
        }
    }

}