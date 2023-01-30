package matixar.mystockhub

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.*
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch
import matixar.mystockhub.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainScreen.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main_screen)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_stock, R.id.nav_crypto,
                R.id.nav_currency, R.id.nav_gold, R.id.nav_own_invest, R.id.nav_calculator, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        addExchangeRatesToPreferences()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_settings -> {
                item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_content_main_screen))
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_screen)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun addExchangeRatesToPreferences() {
        val repository = (application as MyStockHubApplication).currencyRepository
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        lifecycleScope.launch {
            repository.getCurrencyExchangeRate("USD", preferences)
            repository.getCurrencyExchangeRate("EUR", preferences)
            repository.getCurrencyExchangeRate("GBP", preferences)
            repository.getCurrencyExchangeRate("CHF", preferences)
        }
    }
}