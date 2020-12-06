package com.example.playmusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.playmusic.R;

public class DetailSong extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    Boolean Status = true;
    ImageView imageView, imageViewPrevious, imageViewSkip;
    TextView tvCurrentTime, tvTotalTime;
    Button btnPlay, btnRestart, btnStop;
    SeekBar sb;
    VideoView vv;
    public final Handler handler = new Handler();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        sb = (SeekBar) findViewById(R.id.sb_status);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewPrevious = (ImageView) findViewById(R.id.imageViewPrevious);
        imageViewSkip = (ImageView) findViewById(R.id.imageViewSkip);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        mediaPlayer = new MediaPlayer();

        prepareMediaPlayer();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Status) {
                    mediaPlayer.start();
                    btnPlay.setText("Pause");
                    imageView.setImageResource(R.drawable.ic_pause);
                    sb.setMax(mediaPlayer.getDuration());
                    updateSeekBar();
                    Status = false;
                    tvTotalTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
                } else {
                    btnPlay.setText("Play");
                    imageView.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                    Status = true;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                startActivity(new Intent(DetailSong.this, MainActivity.class));
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(sb.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        prepareMediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                sb.setSecondaryProgress(percent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.callOnClick();
            }
        });

        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(sb.getProgress() - 10000);
                sb.setProgress(mediaPlayer.getCurrentPosition());
            }
        });

        imageViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(sb.getProgress() + 10000);
                sb.setProgress(mediaPlayer.getCurrentPosition());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        startActivity(new Intent(DetailSong.this, MainActivity.class));
    }

    private final Runnable Run = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
        }
    };

    private final Runnable notRun = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            sb.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(Run, 100);
        } else {
            handler.postDelayed(notRun, 100);
        }
    }

    private String milliSecondsToTimer(long milliSeconds) {
        String timerString = "";
        String minuteString = "";
        String secondString = "";

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60) / (1000 * 60));
        int seconds = (int) (milliSeconds % (1000 * 60 * 60) % (1000 * 60) / (1000));

        if (hours > 0) {
            timerString = hours + ":";
        }
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else
            minuteString = "" + minutes;
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else
            secondString = "" + seconds;
        timerString = timerString + minuteString + ":" + secondString;
        return timerString;
    }

    private void prepareMediaPlayer() {
        try {
            String idSong = getIntent().getStringExtra("idSong");
            //mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.chiisana_koi_no_uta);
            //  http://api.mp3.zing.vn/api/streaming/<type/<id>/<quality>
            //  https://trungnhan.name.vn/chia-se-api-zing-mp3-2020-tai-nhac-chat-luong-cao/
            //mediaPlayer.setDataSource("http://vnso-zn-23-tf-mp3-s1-zmp3.zadn.vn/5576bd4abd0e54500d1f/8728165239271639865?authen=exp=1607341382~acl=/5576bd4abd0e54500d1f/*~hmac=92a7ab61428d8cd7c874cec6f7264ecd");
            mediaPlayer.setDataSource("http://api.mp3.zing.vn/api/streaming/audio/"+idSong+"/320");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
