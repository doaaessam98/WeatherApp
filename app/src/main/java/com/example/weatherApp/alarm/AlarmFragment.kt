package com.example.weatherApp.alarm

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FragmentAlarmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : Fragment() ,OnDeleteAlarmClickListener{
    private val alarmViewModel: AlarmViewModel by viewModels()
   lateinit var binding: FragmentAlarmBinding
   lateinit var alarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView: alarm", )
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_alarm,container,false)

        setUpView()
        alarmViewModel.getAllWeatherAlarm().observe(viewLifecycleOwner,
            Observer {
               it.let {

               if (it.isEmpty()){
                    binding.alarmBackground.visibility=View.VISIBLE

                }
                else{
                    binding.alarmBackground.visibility=View.GONE


                }
                   alarmAdapter.setDataToAlarmAdapter(it)
                   alarmAdapter.notifyDataSetChanged()

               }


            })

        return binding.root
    }

    private fun setUpView() {
        binding.lifecycleOwner = this
        binding.viewModel = alarmViewModel
        alarmAdapter= AlarmAdapter(arrayListOf(),this)
        binding.alarmRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = alarmAdapter
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnAddAlarm.setOnClickListener {
            binding.btnAddAlarm.setOnClickListener{
                Navigation.findNavController(it).navigate(R.id.action_alarmFragment_to_setAlarmsFragment);

            }
        }

    }


    override fun onDeleteAlarmClicked(view: View,id:Int) {
        var popup = PopupMenu(requireContext(),view);
        popup.inflate(R.menu.alarm_menu);
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_alarm -> {
                    alarmViewModel.deleteALarm(id)
                    alarmAdapter.notifyDataSetChanged()

                }

            }
            alarmAdapter.notifyDataSetChanged()
            false
        }
        popup.show();

    }

    companion object {
    }
}