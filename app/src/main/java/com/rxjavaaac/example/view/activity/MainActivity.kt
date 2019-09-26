package com.rxjavaaac.example.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxjavaaac.example.R
import com.rxjavaaac.example.model.TProduct
import com.rxjavaaac.example.util.bottomSheetConfirmationDialog
import com.rxjavaaac.example.util.gone
import com.rxjavaaac.example.util.onChange
import com.rxjavaaac.example.util.visible
import com.rxjavaaac.example.view.adapter.MainAdapter
import com.rxjavaaac.example.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal


/**
 * @author caca rusmana on 2019-09-26
 */
class MainActivity : BaseActivity() {

    companion object {
        const val INSERT_CODE = 10
        const val UPDATE_CODE = 11
    }

    private val mainViewModel: MainViewModel by viewModel()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this, mutableListOf()) {
            nextActivity(it, UPDATE_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        initObserver()
        initListener()
    }

    private fun initComponent() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        rvProducts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }

    }

    private fun initListener() {
        etSearch.onChange { mainViewModel.getProducts(it) }

        fab.setOnClickListener {
            nextActivity(requestCode = INSERT_CODE)
        }
    }

    private fun initObserver() {
        with(mainViewModel) {

            this.loadingState.observe(this@MainActivity, Observer { visibility ->
                visibility?.let { loading.visibility = it }
            })

            this.products.observe(this@MainActivity, Observer { products ->
                products?.let {

                    mainAdapter.notifyDataSetChanged(it)

                    if (it.isEmpty())
                        tvEmpty.visible()
                    else
                        tvEmpty.gone()
                }
            })

        }

    }

    private fun nextActivity(product: TProduct? = null, requestCode: Int) {
        Intent(this, AddProductActivity::class.java).apply {
            if (requestCode == UPDATE_CODE)
                putExtra("object", product)
            else
                putExtra("object", TProduct(productName = "", price = BigDecimal.ZERO))
            startActivityForResult(this, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            data?.getParcelableExtra<TProduct>("object")?.let {
                when (requestCode) {
                    INSERT_CODE -> {
                        mainViewModel.save(it)
                    }
                    UPDATE_CODE -> {
                        if (data.getBooleanExtra("delete", false))
                            mainViewModel.delete(it)
                        else
                            mainViewModel.save(it)
                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_settings -> finish()
            R.id.action_clear_data -> {
                bottomSheetConfirmationDialog(getString(R.string.delete_all_question)) { mainViewModel.deleteAll() }
            }
        }

        return true
    }


}
