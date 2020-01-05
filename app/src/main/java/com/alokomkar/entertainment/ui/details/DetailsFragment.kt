package com.alokomkar.entertainment.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alokomkar.core.extensions.handleFailures
import com.alokomkar.core.extensions.isInternetConnected
import com.alokomkar.core.extensions.showToast
import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.EntertainApplication
import com.alokomkar.entertainment.MainActivity
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.ui.EntertainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: EntertainViewModel
    private lateinit var featureLocal: FeatureLocal

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as EntertainApplication).appComponent.inject(this)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedItem().observe(viewLifecycleOwner, Observer {
            with(it) {
                Glide.with(ivBackground).load(poster).into(ivBackground)
                tvTitle.text = title
                tvDescription.text = type
                tvDate.text = year
                this@DetailsFragment.featureLocal = this
                fetchShowDetails()
            }
        })
        viewModel.showDetailsLiveData.observe(viewLifecycleOwner, Observer {
            when( it ) {
                is Response.Progress -> {
                    pbLoading.visibility = if( it.loading ) View.VISIBLE else View.GONE
                }
                is Response.Failure -> {
                    handleFailures(it.e) { fetchShowDetails() }
                }
                is Response.Success -> {
                    with(it.data) {
                        tvGenre.text = genre
                        tvDescription.text = getShowDetails()
                    }
                }
            }

        })
        ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun fetchShowDetails() {
        if( isInternetConnected() )
            viewModel.fetchShowById(featureLocal.imdbID)
        else {
            showToast(R.string.message_showing_offline_data)
            pbLoading.visibility = View.GONE
        }
    }

}