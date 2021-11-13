package com.thejan.starwars.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thejan.starwars.R
import com.thejan.starwars.databinding.ActivityMainBinding
import com.thejan.starwars.helper.PLANET
import com.thejan.starwars.helper.startActivityRightToLeft
import com.thejan.starwars.model.Planet
import com.thejan.starwars.view.adapter.PlanetAdapter
import com.thejan.starwars.viewmodel.MainViewModel
import com.thejan.starwars.viewmodel.Status
import com.thejan.starwars.viewmodel.ViewModelState

class MainActivity : BaseActivity(), PlanetAdapter.ClickItem {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var messageFromResponse: String
    private lateinit var adapter: PlanetAdapter
    private var isLoading: Boolean = false
    private var loadingMore: Boolean = false
    private var pageNumber: Int = 1
    private var message = Observer<String> { msg ->
        messageFromResponse = msg!!
    }
    private var planets = Observer<List<Planet>> { list ->
        adapter.setList(list)
    }
    private var loading = Observer<Boolean> { loadingMore ->
        this.loadingMore = loadingMore
    }
    private var page = Observer<Int> { pageNumber ->
        this.pageNumber = pageNumber
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViewModel()
        initSubscription()
        initView()
        loadData()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initSubscription() {
        mViewModel.message.observe(this, message)
        mViewModel.planets.observe(this, planets)
        mViewModel.loadingMore.observe(this, loading)
        mViewModel.pageNumber.observe(this, page)
        mViewModel.state!!.observe(this, {
            it?.let {
                update(it)
            }
        })
    }

    private fun update(state: ViewModelState) {
        when (state.status) {
            Status.LOADING -> {
                binding.progress.visibility = View.VISIBLE
                isLoading = true
            }
            Status.SUCCESS -> {
                binding.progress.visibility = View.GONE
                isLoading = false
            }
            Status.ERROR -> {
                showToast(messageFromResponse)
                binding.progress.visibility = View.GONE
                isLoading = false
            }
            Status.LIST_EMPTY -> {
                binding.tvNoRecordFound.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE
                isLoading = false
            }
        }
    }

    private fun initView() {
        setToolBar(binding.toolbar, getString(R.string.home), true)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvRecyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        binding.rvRecyclerView.layoutManager = mLayoutManager
        adapter = PlanetAdapter(ArrayList(), this)
        binding.rvRecyclerView.adapter = adapter

        binding.rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (loadingMore && !isLoading) {
                    if (dy >= 0) {
                        val visibleItemCount: Int = mLayoutManager.childCount
                        val totalItemCount: Int = mLayoutManager.itemCount
                        val firstVisibleItemPosition: Int =
                            mLayoutManager.findFirstVisibleItemPosition()
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                        ) {
                            loadData()
                        }
                    }
                }
            }

        })
    }

    fun loadData() {
        mViewModel.getPlanets(pageNumber)
    }

    override fun itemClick(position: Int, planet: Planet) {
        val intent = Intent(this, PlanetActivity::class.java)
        intent.putExtra(PLANET, planet)
        startActivityRightToLeft(this, intent)
    }
}