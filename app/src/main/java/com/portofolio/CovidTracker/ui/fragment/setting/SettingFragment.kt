package com.portofolio.CovidTracker.ui.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.portofolio.CovidTracker.R
import com.portofolio.CovidTracker.databinding.FragmentSettingBinding
import com.portofolio.CovidTracker.util.AlarmReceiver

class SettingFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val settingBinding get() = _binding!!
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return settingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingBinding.onNotifyButton.setOnClickListener { this }
        settingBinding.offNotifyButton.setOnClickListener { this }
        settingBinding.changeLangButton.setOnClickListener { setLanguage() }
    }

    private fun setLanguage() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.onNotifyButton -> alarmReceiver.setRepeatingAlarm(requireContext())
            R.id.offNotifyButton -> alarmReceiver.cancelAlarm(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}