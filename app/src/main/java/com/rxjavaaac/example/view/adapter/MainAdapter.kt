package com.rxjavaaac.example.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rxjavaaac.example.R
import com.rxjavaaac.example.model.TProduct
import com.rxjavaaac.example.util.toCurr
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.product_item_list.*

/**
 * @author caca rusmana on 2019-09-26
 */
class MainAdapter(
    private val context: Context,
    private var productList: MutableList<TProduct>,
    private val listener: (TProduct) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val containerView = LayoutInflater.from(context).inflate(R.layout.product_item_list, parent, false)

        return MainViewHolder(containerView)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(productList[position], listener)
    }

    fun notifyDataSetChanged(productList: MutableList<TProduct>) {
        this.productList.clear()
        this.productList.addAll(productList)

        notifyDataSetChanged()
    }


    class MainViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(product: TProduct, listener: (TProduct) -> Unit) = with(product) {
            tvInitial.text = productName[0].toString()
            tvProductName.text = productName
            tvPrice.text = price.toCurr()

            containerView.setOnClickListener { listener(product) }
        }

    }
}