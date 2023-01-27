package matixar.mystockhub

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import matixar.mystockhub.API.*
import matixar.mystockhub.API.models.GlobalQuote
import matixar.mystockhub.databinding.ActivityMainScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreenActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainScreen.toolbar)

        binding.appBarMainScreen.fab.setOnClickListener { view ->
            val api = AlphaVantageApiInterface.create().getStock(AlphaVantageFunctions.STOCK_MODEL.function, "IBM", AlphaVantageApiInterface.API_KEY)

            api.enqueue(object : Callback<GlobalQuote> {
                override fun onResponse(
                    call: Call<GlobalQuote>,
                    response: Response<GlobalQuote>
                ) {
                    println(response.body().toString())
                    println(response.body()?.stockApiModel?.symbol)
                    println(response.body()?.stockApiModel?.price)
                }

                override fun onFailure(call: Call<GlobalQuote>, t: Throwable) {
                    println(t.message)
                }

            })
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main_screen)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_stock, R.id.nav_crypto,
                R.id.nav_currency, R.id.nav_gold, R.id.nav_own_invest, R.id.nav_calculator, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_screen, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_screen)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}