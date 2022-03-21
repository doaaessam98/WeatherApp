package com.example.weatherApp.alarm

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherApp.databinding.AlarmItimBinding
import com.example.weatherApp.favorite.OnFavMenuClick

class AlarmAdapter(var alarmList:ArrayList<AlarmModel>,var onDeleteAlarmClickListener: OnDeleteAlarmClickListener) :RecyclerView.Adapter<AlarmAdapter.AlarmViewModel>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewModel {
        var root=AlarmItimBinding.inflate(LayoutInflater
            .from(parent.context),parent,false)
        return AlarmAdapter.AlarmViewModel(root)
    }

    override fun onBindViewHolder(holder: AlarmViewModel, position: Int) {
        Log.e(TAG, "onBindViewHolder: ", )
        var alarm_item=alarmList[position]
        holder.view.alarmTimeFrom.text=alarm_item.TimeFrom

        holder.view.alarmTimeTo.text=alarm_item.TimeTo
        holder.view.alarmDateTo.text=alarm_item.DateTO
        holder.view.alarmDataFrom.text=alarm_item.DateFrom
        holder.view.moreAlarm.setOnClickListener {
            onDeleteAlarmClickListener.onDeleteAlarmClicked(holder.view.moreAlarm,alarm_item.alarmId)

        }
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    fun setDataToAlarmAdapter( alarmUpdatedList:List<AlarmModel>){
        alarmList.clear()
        alarmList.addAll(alarmUpdatedList)
        notifyDataSetChanged()

    }
    class AlarmViewModel(var view:AlarmItimBinding):RecyclerView.ViewHolder(view.root){

    }

}