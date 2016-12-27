package com.example.aaguilar.tonosnavideos;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashscreenActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private ImageView e2,e3,e4,e5,e6,e7,e8,e9;
    private Animation animacion;
    private Animation bounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splashscreen);

        ivLogo = (ImageView) findViewById(R.id.ivLogo1);
        animacion = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacion);
        ivLogo.startAnimation(animacion);

        e2 = (ImageView) findViewById(R.id.e2);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
        e2.startAnimation(bounce);

        e3 = (ImageView) findViewById(R.id.e3);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce3);
        e3.startAnimation(bounce);

        e4 = (ImageView) findViewById(R.id.e4);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce4);
        e4.startAnimation(bounce);

        e5 = (ImageView) findViewById(R.id.e5);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce5);
        e5.startAnimation(bounce);

        e6 = (ImageView) findViewById(R.id.e6);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce6);
        e6.startAnimation(bounce);

        e7 = (ImageView) findViewById(R.id.e7);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce7);
        e7.startAnimation(bounce);

        e8 = (ImageView) findViewById(R.id.e8);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce8);
        e8.startAnimation(bounce);

        e9 = (ImageView) findViewById(R.id.e9);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce9);
        e9.startAnimation(bounce);


        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
