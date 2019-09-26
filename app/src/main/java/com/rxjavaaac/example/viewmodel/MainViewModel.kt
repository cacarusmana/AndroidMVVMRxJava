package com.rxjavaaac.example.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxjavaaac.example.model.TProduct
import com.rxjavaaac.example.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author caca rusmana on 2019-09-26
 */
class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val products: MutableLiveData<MutableList<TProduct>> = MutableLiveData()
    val loadingState: MutableLiveData<Int> = MutableLiveData()

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadingState.value = View.VISIBLE

//        initData()
        getProducts()
    }

    private fun initData() {
        // this method will init 1000 records data
        disposable.add(
            productRepository.initData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { getProducts() }
        )
    }

    fun getProducts(value: String = "") {
        loadingState.value = View.VISIBLE

        disposable.add(
            productRepository.getProducts(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showData, this::promptError)
        )
    }

    private fun showData(products: MutableList<TProduct>) {
        loadingState.value = View.GONE
        this.products.value = products
    }

    private fun promptError(throwable: Throwable) = throwable.printStackTrace()

    fun save(product: TProduct) {
        disposable.add(productRepository.save(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getProducts() })
    }

    fun delete(product: TProduct) {
        disposable.add(productRepository.delete(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getProducts() })
    }

    fun deleteAll() {
        disposable.add(productRepository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getProducts() })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}