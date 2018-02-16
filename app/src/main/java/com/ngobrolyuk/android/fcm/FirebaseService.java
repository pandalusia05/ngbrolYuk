package com.ngobrolyuk.android.fcm;

/**
 * Created by MIP on 1/6/2018.
 */


import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ngobrolyuk.android.R;
import com.ngobrolyuk.android.activities.ChatActivity;
import com.ngobrolyuk.android.models.Message;
import com.ngobrolyuk.android.utils.Session;

import java.util.List;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /**
         * be sure that data size > 0
         */

        if (remoteMessage.getData().size() > 0) {
            // get values from data that sent from php by fcm
            Log.e("message content", remoteMessage.getData().get("message"));
            String messageContent = remoteMessage.getData().get("message");
            String roomId = remoteMessage.getData().get("room_id");
            String userId = remoteMessage.getData().get("user_id");
            String username = remoteMessage.getData().get("username");
            String messageType = remoteMessage.getData().get("type");
            String timestamp = remoteMessage.getData().get("timestamp");

            // Create new message and assign value to it
            Message message = new Message();
            message.setContent(messageContent);
            message.setRoomId(roomId);
            message.setUserId(userId);
            message.setUsername(username);
            message.setType(messageType);
            message.setTimestamp(timestamp);

            // check if the sender of message is current user or not
            if (!(Integer.valueOf(userId) == Session.newInstance().getUser().id)) {

                // check if app in background or not

                if (isAppIsInBackground(this)) {
                    // app is in background show notification to user
                    sendNotification(message);
                } else {
                    // app is forground and user see it now send broadcast to update chat
                    // you can send broadcast to do anything if you want !
                    Intent intent = new Intent("UpdateChateActivity");
                    intent.putExtra("msg", message);
                    sendBroadcast(intent);

                }
            }


        }

    }

    /**
     * Method check if app is in background or in foreground
     *
     * @param context this contentx
     * @return true if app is in background or false if app in foreground
     */

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * Method send notification
     *
     * @param message message object
     */
    private void sendNotification(Message message) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("msg", message);
        intent.putExtra("room_id", Integer.parseInt(message.getRoomId()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hendienger")
                .setContentText(message.getContent())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}


