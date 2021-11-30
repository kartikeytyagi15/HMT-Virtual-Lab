package info.androidhive.hmtvirtuallab;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import info.androidhive.hmtvirtuallab.MainActivity;
import info.androidhive.hmtvirtuallab.R;

public class SplashScreen extends AppCompatActivity {

    private boolean ispaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar

        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.purple_500));

        VideoView vView = findViewById(R.id.splash);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.splash);

        if (vView != null) {
            vView.setVideoURI(video);
            vView.setZOrderOnTop(true);
            vView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    jump();
                }
            });


            vView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    jump();
                    return false;
                }
            });

            vView.start();

        }else{

            jump();
        }
    }


    private void jump() {

        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);

        SplashScreen.this.finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ispaused) {
            jump();
        }

    }
}