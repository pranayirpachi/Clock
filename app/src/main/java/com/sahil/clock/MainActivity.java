package com.sahil.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    AdView adView;
    InterstitialAd minterstitialAd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //time picker
        timePicker = findViewById(R.id.clock);
        timePicker.setIs24HourView(true);
        //getting selected time
        String s1 = " : " + timePicker.getCurrentMinute();
        String current = "Time:" +timePicker.getCurrentHour()+" : " + timePicker.getCurrentMinute();
        Toast.makeText(MainActivity.this, ""+current, Toast.LENGTH_SHORT).show();
        adView = findViewById(R.id.admods);


        //step 1
        MobileAds.initialize(this);

        //step 2

        AdRequest adRequest = new AdRequest.Builder().build();
        //step 3
        adView.loadAd(adRequest);


        InterstitialAd.load(this, getString(R.string.inter_app_unit), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                minterstitialAd = interstitialAd;

                minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });

                minterstitialAd.show(MainActivity.this);


            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Error",loadAdError.toString());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(minterstitialAd!=null){
                    minterstitialAd.show(MainActivity.this);
                }else{
                    Log.e("Adpending","Ad is not ready yet");
                }

            }
        },1000);
    }
}