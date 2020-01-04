package com.alokomkar.entertainment.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alokomkar.entertainment.EntertainApplication
import com.alokomkar.entertainment.MainActivity
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.ui.EntertainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

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
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedItem().observe(viewLifecycleOwner, Observer {
            with(it) {
                Glide.with(ivBackground).load(poster).into(ivBackground)
                tvTitle.text = title
                tvDescription.text = type
                tvDate.text = year
            }
        })
        ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

}