package com.alokomkar.entertainment.ui.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alokomkar.core.extensions.handleFailures
import com.alokomkar.core.extensions.showToast
import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.EntertainApplication
import com.alokomkar.entertainment.MainActivity
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.ui.EntertainViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : Fragment(), SearchListAdapter.OnItemClickListener {

    @Inject
    lateinit var listAdapter: SearchListAdapter
    private lateinit var viewModel: EntertainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as EntertainApplication).appComponent.inject(this)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if( rvShows.adapter == null ) {
            rvShows.apply {
                adapter = listAdapter.apply {
                    onItemClickListener = this@ListFragment
                    onReadyToLoadMore = {
                        fetchData()
                        showToast(R.string.loading)
                    }
                }
            }
            listRefreshLayout.setOnRefreshListener { fetchData() }

            viewModel.showsListLiveData.observe(viewLifecycleOwner, Observer { response ->
                when( response ) {
                    is Response.Progress -> {
                        listRefreshLayout.isRefreshing = response.loading
                    }
                    is Response.Failure -> {
                        handleFailures(response.e) { fetchData() }
                    }
                    is Response.Success -> {
                        listAdapter.submitList(response.data)
                    }
                }
            })
        }

        if( viewModel.showsListLiveData.value == null )
            fetchData()
    }

    private fun fetchData() {
        context?.let {
            val isInternetConnected = isInternetConnected(it)
            if( !isInternetConnected ) {
                Toast.makeText(it, R.string.message_showing_offline_data, Toast.LENGTH_SHORT).show()
            }
            if( isInternetConnected ) {
                viewModel.refresh()
            }
            else
                viewModel.fetchShows()
        }
    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onItemClick(item: FeatureLocal) {
        viewModel.setSelectedItem(item)
        val navController = findNavController()
        if( navController.currentDestination?.id == R.id.listFragment ) {
            navController.navigate(R.id.action_listFragment_to_detailsFragment)
        }
    }

}