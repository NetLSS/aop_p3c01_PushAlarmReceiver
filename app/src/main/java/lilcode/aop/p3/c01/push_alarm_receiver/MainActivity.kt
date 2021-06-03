package lilcode.aop.p3.c01.push_alarm_receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

/*
cf.
[firebase 클라우드 메시징(FCM)](https://firebase.google.com/docs/cloud-messaging?hl=ko)
[Android 앱에서 메시지 수신](https://firebase.google.com/docs/cloud-messaging/android/receive?hl=ko)



1. 메세지 빌딩/타겟팅 : 서버에서 요청 또는 파베콘솔에서 요청
2. FCM 백엔드로 요청 보내면
3. 각 플렛폼에 맞는 메세지로 전송
4. SDK 사용하여 처리

메세지 유형 크게 2가지
1. 알림 메세지 ( 구현은 쉽지만, 실제로 앱이 백그라운드에 있을 때 처리할 수 없이 바로 보이는 형태) (여러 케이스에 유연하게 대응하기 어려움)
    - FireBase 콘솔 사용하여 알림 메세지 전송 가능
    - A/B 테스팅 제공 : 콘솔 상에서 알림 메세지를 보낼때 2가지로 테스팅 (할인율 70%, 어떤 브랜드 신상 보냈을 때 어떤 것을 많이 클릭 하는지)
    - 포그라운드일 때는 따로 처리 필요
2. 데이터 메세지 (백그라운드, 포그라운드 상관x 앱에서 자체 처리가능. 구현할건 많으나 유연 대응 가능)

알림의 경우 호환성을 유심히 챙겨야함

 */

class MainActivity : AppCompatActivity() {

    private val resultTextView: TextView by lazy {
        findViewById(R.id.resultTextView)
    }

    private val firebaseTokenTextView: TextView by lazy {
        findViewById(R.id.firebaseTokenTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
        updateResult()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent) // 새로 들어온 인텐트로 교체

        updateResult(true)
    }

    // firebase 토큰을 가져오는 부분
    private fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { // 성공 시
                    firebaseTokenTextView.text = task.result // 토큰 가져오기
                }

            }
    }

    private fun updateResult(isNewIntent: Boolean = false) {
        // isNewIntent 앱이 새로 켜진건지
        resultTextView.text =
            intent.getStringExtra("notificationType") ?: "앱 런처" + if (isNewIntent) {
                "(으)로 갱신했습니다."
            } else {
                "(으)로 실행했습니다."
            }
    }
}