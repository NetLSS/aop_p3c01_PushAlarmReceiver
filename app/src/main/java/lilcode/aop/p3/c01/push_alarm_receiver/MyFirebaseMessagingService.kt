package lilcode.aop.p3.c01.push_alarm_receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/*
다음과 같은 경우에 등록 토큰이 변경될 수 있습니다.

- 새 기기에서 앱 복원
- 사용자가 앱 삭제/재설치
- 사용자가 앱 데이터 소거

API 사용
https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages/send?hl=ko


https://developer.android.com/training/notify-user/channels
채널
- 중요도 설정 필요
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        // 토큰이 갱신될 때마다 처리 해주는 작업 여기에 필요 (실무에서)
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // FCM 수신 마다 실행
        super.onMessageReceived(remoteMessage)

        // 채널 생성
        createNotificationchannel()

        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        // 실제 알림 컨텐츠 만들기
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)// 아이콘 보여주기
            .setContentTitle(title) // 메세지 에서 받은 타이틀 활용
            .setContentText(message) // 메세지 에서 받은 메세지 활용
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 오레오 이하 버전 에서는 지정 필요

        // 실제로 노티파이를 하기 위해서
        // 메세지가 왔을 때 타이틀과 메세지에 맞게 각각 보여줌
        NotificationManagerCompat.from(this)// 매니저 가져옴
            .notify(1, notificationBuilder.build())
    }

    private fun createNotificationchannel() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create the NotificationChannel
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
//            mChannel.description = descriptionText
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(mChannel)
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}