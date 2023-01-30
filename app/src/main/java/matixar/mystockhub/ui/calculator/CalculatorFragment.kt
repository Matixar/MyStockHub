package matixar.mystockhub.ui.calculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import matixar.mystockhub.API.models.Currency
import matixar.mystockhub.MyStockHubApplication
import matixar.mystockhub.databinding.FragmentCalculatorBinding

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