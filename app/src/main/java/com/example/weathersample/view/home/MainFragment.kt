package com.example.weathersample.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathersample.*
import com.example.weathersample.common.*
import com.example.weathersample.database.City
import com.example.weathersample.databinding.FragmentMainBinding
import com.example.weathersample.view.hourlyforecast.HourlyForecastFragment
import com.example.weathersample.view.nextsevenday.NextSevenDayFragment
import com.example.weathersample.view.setting.SettingFragment


class MainFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        SharedViewModel.Factory(activity.application)
    }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.nightMode.observe(viewLifecycleOwner, Observer {
            viewModel.nightMode.value?.let { it1 -> setBackGround(it1) }
            Log.d("bg", "Resume")
        })

        viewModel.homeCardColor.observe(viewLifecycleOwner, Observer {
            viewModel.homeCardColor.value?.let { it1 ->
                binding.contentMain.homeCard.setBackgroundResource(
                    it1
                )
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("bg", "Destroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("bg", "Pause")
        viewModel.homeCardColor.value?.let { viewModel.nightMode.value?.let { it1 -> saveState(it1, it) } }
    }
    private fun setBackGround(nightMode: Boolean) {
        val bg = if(nightMode) R.drawable.night_bg else R.drawable.sunny_bg
            binding.mainLayout.setBackgroundResource(bg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentMain.lifecycleOwner = viewLifecycleOwner
        binding.contentMain.viewModel = viewModel
        viewModel.getWeatherResponse("Hanoi")
        viewModel.setValue(preferences.getBoolean(BACKGROUND_KEY, false))
        viewModel.setHomeColor(preferences.getInt(HOME_COLOR_KEY, R.drawable.card_bg))
        Log.d("state", viewModel.nightMode.value.toString())
        viewModel.getA()
        upDateHourly()
        initSearchBar(binding)
        initNextDay(binding)
        initDetail(binding)
        initMenuBnt(binding)
    }

    private fun initMenuBnt(binding: FragmentMainBinding) {
        binding.menu.setOnClickListener {
            showFragment(R.id.firstFrame, SettingFragment(), parentFragmentManager,true)
        }
    }

    private fun initDetail(binding: FragmentMainBinding) {
        binding.contentMain.chart.setOnClickListener {
            showFragment(R.id.firstFrame, HourlyForecastFragment(), parentFragmentManager,true)
        }
    }

    private fun upDateHourly() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                viewModel.getHourlyResponse(it.coord?.lat.toString(), it.coord?.lon.toString())
            }
        })
    }

    private fun initNextDay(binding: FragmentMainBinding) {
        binding.nextDay.setOnClickListener {
            showFragment(R.id.firstFrame, NextSevenDayFragment(), parentFragmentManager,true)
        }
    }

    private fun initSearchBar(binding: FragmentMainBinding) {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getWeatherResponse(query.toString())
                viewModel.weather.observe(viewLifecycleOwner, Observer {
                    val city = it.sys?.country?.let { it1 -> it.name?.let { it2 ->
                        City(it.id,
                            it2, it1)
                    } }
                    if (city != null) {
                        viewModel.updateRoom(city)
                    }
                })
                upDateHourly()
                searchMode(false)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        binding.searchBar.setOnSearchClickListener {
            searchMode(true)
        }

    }

    private fun searchMode(boolean: Boolean){
        binding.content.visibility = if(boolean) View.INVISIBLE else View.VISIBLE
        binding.listCity.visibility = if(boolean) View.VISIBLE else View.INVISIBLE
    }

}