package com.example.weathersample.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weathersample.R
import com.example.weathersample.SharedViewModel
import com.example.weathersample.common.extended
import com.example.weathersample.databinding.FragmentSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SharedViewModel by activityViewModels {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        SharedViewModel.Factory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragment = this
    }

    fun nightModeOn(b: Boolean) {
        viewModel.setValue(b)
    }

    fun extendSetting(){
        val rotate = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate
        )
        val up = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_up_anim
        )
        binding.extend.startAnimation(rotate)
        val img = if (extended) R.drawable.ic_down else R.drawable.ic_up
        binding.extend.setImageResource(img)
        binding.extendLayout.visibility = if (extended) View.GONE else View.VISIBLE
        if(!extended) binding.extendLayout.startAnimation(up)
        extended = !extended

    }

    fun setHomeCardColor(view: View){
        val background: Int = when(view){
            binding.color1 -> R.drawable.card_bg
            binding.color2 -> R.drawable.card_bg1
            binding.color3 -> R.drawable.card_bg2
            else -> R.drawable.card_bg3
        }
        viewModel.setHomeColor(background)
    }


}