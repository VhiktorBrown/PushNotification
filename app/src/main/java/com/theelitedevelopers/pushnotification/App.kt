package com.theelitedevelopers.pushnotification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class App : Application() {
    private val CHANNEL_ID = "12345"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        showNotification()
    }

    private fun createNotificationChannel(){

        //If Android OS is greater than Oreo, create Notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description);

            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }

            //Register the channel
            val notificationManager : NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotificationBuilder() : NotificationCompat.Builder {
        //pass the layouts through Remote views
        val notificationLayout = RemoteViews(packageName, R.layout.notification_collapsed)
        val notificationExpandedLayout = RemoteViews(packageName, R.layout.notification_expanded)

        //set up Notification with Notification Builder
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.frame)
            .setColor(ContextCompat.getColor(this, R.color.notification_color))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationExpandedLayout)
    }


    private fun showNotification() {
        val notification = createNotificationBuilder()

        val uniqueNotificationID= 0;

        with(NotificationManagerCompat.from(this
        )) { notify(uniqueNotificationID, notification.build())}
    }
}