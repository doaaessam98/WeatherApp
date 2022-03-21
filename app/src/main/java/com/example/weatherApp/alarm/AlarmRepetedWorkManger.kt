package com.example.weatherApp.alarm

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.weatherApp.alarm.setAlarmDialog.AlarmDialogViewModel
import com.example.weatherApp.home.HomeViewModel
import com.example.weatherApp.repo.RepositoryInterface
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
@HiltWorker
class AlarmRepetedWorkManger @AssistedInject constructor(@Assisted var context: Context
,@Assisted workerParams: WorkerParameters,private var repository: RepositoryInterface):
    CoroutineWorker(context, workerParams) {



    override suspend fun doWork(): Result {
        println("do worker")
        val inputData = inputData
        val id = inputData.getInt("ID",0).toString()
        val timeZone = inputData.getString("time")
        val lat=inputData.getDouble("lat",0.0)
        val lon=inputData.getDouble("lon",0.0)
        if (timeZone != null) {
            getCurrentData(lat,lon,timeZone)
        }
        return Result.success()

    }

    private fun getCurrentData(lat:Double,log:Double, timeZone: String) {
       CoroutineScope(Dispatchers.IO).launch {
           val currentWeather = repository.getDataForAlarm(lat,log,timeZone)
           setOneTimeWorkManager("hello",
               "1")
//           setOneTimeWorkManager(currentWeather.current?.weather?.get(0)!!.description,
//               currentWeather.current?.weather!![0]!!.icon)
       }

        }

    private fun setOneTimeWorkManager(description: String, icon: String) {
        val data = Data.Builder()
            .putString("description", description)
            .putString("icon", icon)
            .build()
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            AlarmOneWorkmanger::class.java
        )
            .setInputData(data)
            .setConstraints(constraints)
           // .setInitialDelay(delay, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            id.toString(), ExistingWorkPolicy.REPLACE, oneTimeWorkRequest
        )

    }

}
