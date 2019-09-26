package com.rxjavaaac.example.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxjavaaac.example.model.TProduct
import com.rxjavaaac.example.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val products: MutableLiveData<MutableList<TProduct>> = MutableLiveData()
    val loadingState: MutableLiveData<Int> = MutableLiveData()

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadingState.value = View.VISIBLE

//        initData()
        findProducts()
    }

    private fun initData() {
        // this method will init 1000 records data
        disposable.add(productRepository.initData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { findProducts() })
    }

    fun findProducts(value: String = "") {
        loadingState.value = View.VISIBLE

        disposable.add((if (value == "") productRepository.getAll() else productRepository.findProducts(
            value
        ))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                loadingState.value = View.GONE
                products.value = items
            })
    }

    fun save(product: TProduct) {
        disposable.add(productRepository.save(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { findProducts() })
    }

    fun delete(product: TProduct) {
        disposable.add(productRepository.delete(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { findProducts() })
    }

    fun deleteAll() {
        disposable.add(productRepository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { findProducts() })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}