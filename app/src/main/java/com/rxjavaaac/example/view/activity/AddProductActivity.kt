package com.rxjavaaac.example.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.rxjavaaac.example.R
import com.rxjavaaac.example.model.TProduct
import com.rxjavaaac.example.util.bottomSheetConfirmationDialog
import com.rxjavaaac.example.util.gone
import com.rxjavaaac.example.util.value
import kotlinx.android.synthetic.main.activity_add_product.*
import java.math.BigDecimal

/**
 * @author caca rusmana on 2019-09-26
 */
class AddProductActivity : BaseActivity() {

    private lateinit var product: TProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        initComponent()
    }

    private fun initComponent() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        product = intent.getParcelableExtra("object")

        with(product) {
            if (id == 0) btnDelete.gone()

            etProductName.setText(productName)
            etPrice.setText(if (price == BigDecimal.ZERO) "" else price.toPlainString())
        }

        initListener()
    }

    private fun initListener() {
        btnSave.setOnClickListener { view ->
            val errorMsg = validateFields()

            if (errorMsg != "") {
                Snackbar.make(view, errorMsg, Snackbar.LENGTH_LONG).show()
            } else {
                product.apply {
                    productName = etProductName.value()
                    price = BigDecimal(etPrice.value())
                }

                Intent().apply {
                    putExtra("object", product)
                    setResult(Activity.RESULT_OK, this)
                }
                finish()
            }
        }

        btnDelete.setOnClickListener {
            bottomSheetConfirmationDialog(getString(R.string.delete_question)) {
                val intent = Intent()
                intent.putExtra("object", product)
                intent.putExtra("delete", true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun validateFields(): String {
        return if (etProductName.value() == "") {
            getString(R.string.product_name_blank)
        } else if (etPrice.value() == "") {
            getString(R.string.price_blank)
        } else if (etPrice.value().toInt() <= 0) {
            getString(R.string.price_zero)
        } else {
            ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }

}