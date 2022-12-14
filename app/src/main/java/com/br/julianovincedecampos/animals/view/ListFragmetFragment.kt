package com.br.julianovincedecampos.animals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.br.julianovincedecampos.animals.R
import com.br.julianovincedecampos.animals.model.Animal
import com.br.julianovincedecampos.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list_fragmet.*


class ListFragmetFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var listAdapter = AnimalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_fragmet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)

        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            textViewEror.visibility = View.GONE
            listProgressBar.visibility = View.GONE
            viewModel.hardRefresh()
            refreshLayout.isRefreshing = false
        }
    }


    private val animalListDataObserver = Observer<List<Animal>> { list ->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        listProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            textViewEror.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        textViewEror.visibility = if (isError) View.VISIBLE else View.GONE
    }
}