package dicoding.adrian.submission4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    protected ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);

        Animation splashTransition = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        tv.startAnimation(splashTransition);
        iv.startAnimation(splashTransition);

        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    // Lama SplashScreen
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
