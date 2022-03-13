package com.example.composetest.login.presentation.supports

import android.app.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.destinations.DirectionDestination
import com.example.composetest.login.presentation.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

object AppNotification {
    private const val CHANNEL_ID = "App_notification"
    private const val NOTIFICATION_ID = 0


    fun initNotification(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = activity.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val channel = notificationManager.getNotificationChannel(CHANNEL_ID)

            if(channel == null) {
                val name = "App Notification Channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                notificationManager.createNotificationChannel(mChannel)
            }

        }
    }

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    fun makeNotification(
        context: Context,
        title: String,
        text: String,
//        destinationsNavigator: DestinationsNavigator,
//        destination: DirectionDestination =  HomeScreenDestination,
//        bundle: Bundle = bundleOf()
    ) {

        /*Пока не ясно как использовать навигацию библиотеки DestinationsNavigator у pendingIntent*/
//        val pendingIntentIntent = navController
//            .createDeepLink()
//            .setDestination(R.id.insuranceCheckFragment)
//            .setArguments(bundle)
//            .createPendingIntent()


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_baseline_online_prediction_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntentIntent)
            .setAutoCancel(true)
            .build()


        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancelAll()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}