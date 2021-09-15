package com.example.weathersample.view.nextsevenday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weathersample.R
import com.example.weathersample.SharedViewModel
import com.example.weathersample.databinding.FragmentNextSevenDayBinding
import com.example.weathersample.view.NextSevenDays.ForecastDayAdapter


class NextSevenDayFragment : DialogFragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        SharedViewModel.Factory(activity.application)
    }
    private lateinit var binding:FragmentNextSevenDayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_next_seven_day, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
//        binding.fragment = this // Loi o day à
        val lat = viewModel.weather.value?.coord?.lat.toString()
        val lon = viewModel.weather.value?.coord?.lon.toString()
        viewModel.getDailyResponse(lat, lon)
        binding.recyclerView.adapter = ForecastDayAdapter()
    }

//    private fun initExitButton() {
//        binding.closeButton.setOnClickListener {
//            dismiss()
//        }
//    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

}