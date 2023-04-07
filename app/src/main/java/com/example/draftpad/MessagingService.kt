package com.example.draftpad;

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Notification Message Body: ${remoteMessage.notification!!.body}")
            makeStatusNotification(remoteMessage.notification!!.body!!, applicationContext)
        }
    }



    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
