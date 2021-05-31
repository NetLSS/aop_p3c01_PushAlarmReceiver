package lilcode.aop.p3.c01.push_alarm_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
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

[확장형 알림 만들기](https://developer.android.com/training/notify-user/expanded)
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

        val type = remoteMessage.data["type"] //enum 이랑 동일한 값
            ?.let { NotificationType.valueOf(it) }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]


        // type 이 null 이면 알림 생성 x
        type ?: return


        // 실제로 노티파이를 하기 위해서
        // 메세지가 왔을 때 타이틀과 메세지에 맞게 각각 보여줌
        NotificationManagerCompat.from(this)// 매니저 가져옴
            .notify(type.id, createNotification(type, title, message))
    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {
        // 실제 알림 컨텐츠 만들기
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)// 아이콘 보여주기
            .setContentTitle(title) // 메세지 에서 받은 타이틀 활용
            .setContentText(message) // 메세지 에서 받은 메세지 활용
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 오레오 이하 버전 에서는 지정 필요

        when (type) {
            NotificationType.NORMAL -> Unit
            // 확장 가능한 알림
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 \uD83D\uDE01 \uD83D\uDE06 " +
                                    "\uD83D\uDE05 \uD83D\uDE02 \uD83E\uDD23 \uD83E\uDD72 ☺️ " +
                                    "\uD83D\uDE0A \uD83D\uDE07 \uD83D\uDE42 \uD83D\uDE43 " +
                                    "\uD83D\uDE09 \uD83D\uDE0C \uD83D\uDE0D \uD83E\uDD70 " +
                                    "\uD83D\uDE18 \uD83D\uDE17 \uD83D\uDE19 \uD83D\uDE1A " +
                                    "\uD83D\uDE0B \uD83D\uDE1B \uD83D\uDE1D \uD83D\uDE1C " +
                                    "\uD83E\uDD2A \uD83E\uDD28 \uD83E\uDDD0 \uD83E\uDD13 " +
                                    "\uD83D\uDE0E \uD83E\uDD78 \uD83E\uDD29 \uD83E\uDD73 " +
                                    "\uD83D\uDE0F \uD83D\uDE12 \uD83D\uDE1E \uD83D\uDE14 " +
                                    "\uD83D\uDE1F \uD83D\uDE15 \uD83D\uDE41 ☹️ \uD83D\uDE23 " +
                                    "\uD83D\uDE16 \uD83D\uDE2B \uD83D\uDE29 \uD83E\uDD7A " +
                                    "\uD83D\uDE22 \uD83D\uDE2D \uD83D\uDE24 \uD83D\uDE20 " +
                                    "\uD83D\uDE21 \uD83E\uDD2C \uD83E\uDD2F \uD83D\uDE33 " +
                                    "\uD83E\uDD75 \uD83E\uDD76 \uD83D\uDE31 \uD83D\uDE28 " +
                                    "\uD83D\uDE30 \uD83D\uDE25 \uD83D\uDE13 \uD83E\uDD17 " +
                                    "\uD83E\uDD14 \uD83E\uDD2D \uD83E\uDD2B \uD83E\uDD25 " +
                                    "\uD83D\uDE36 \uD83D\uDE10 \uD83D\uDE11 \uD83D\uDE2C " +
                                    "\uD83D\uDE44 \uD83D\uDE2F \uD83D\uDE26 \uD83D\uDE27 " +
                                    "\uD83D\uDE2E \uD83D\uDE32 \uD83E\uDD71 \uD83D\uDE34 " +
                                    "\uD83E\uDD24 \uD83D\uDE2A \uD83D\uDE35 \uD83E\uDD10 " +
                                    "\uD83E\uDD74 \uD83E\uDD22 \uD83E\uDD2E \uD83E\uDD27 " +
                                    "\uD83D\uDE37 \uD83E\uDD12 \uD83E\uDD15 \uD83E\uDD11 " +
                                    "\uD83E\uDD20 \uD83D\uDE08 \uD83D\uDC7F \uD83D\uDC79 " +
                                    "\uD83D\uDC7A \uD83E\uDD21 \uD83D\uDCA9 \uD83D\uDC7B " +
                                    "\uD83D\uDC80 ☠️ \uD83D\uDC7D \uD83D\uDC7E \uD83E\uDD16 " +
                                    "\uD83C\uDF83 \uD83D\uDE3A \uD83D\uDE38 \uD83D\uDE39 " +
                                    "\uD83D\uDE3B \uD83D\uDE3C \uD83D\uDE3D \uD83D\uDE40 " +
                                    "\uD83D\uDE3F \uD83D\uDE3E"
                        )
                )
            }
            // 커스텀 알림
            // https://developer.android.com/training/notify-user/custom-notification
            NotificationType.CUSTOM -> {
                notificationBuilder
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title, title) // 타이틀 영역에 받아온 타이틀 넣기
                            setTextViewText(R.id.message, message) // 메세지 넣기
                        }
                    )
            }
        }

        return notificationBuilder.build()
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