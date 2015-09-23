package com.codekitchen.allen.mycce;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class Activity_WayLiTry extends YouTubeBaseActivity {

    public static final String DEVELOPER_KEY = "AIzaSyDBVArbgHV33PUn8jQue9xQp5dVJ8R21DQ";

    private YouTubeThumbnailView youtube_thumbnail;
    private YouTubePlayerView youtub_view;
    private YouTubePlayer player;
    private String vid = "CVNzfSZXE1Q";
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_li_try);

        context = this;

        //YouTube縮圖
        youtube_thumbnail = (YouTubeThumbnailView)findViewById(R.id.youtube_thumbnail);
        youtube_thumbnail.initialize(DEVELOPER_KEY, new YoutubeThumbnailOnInitializedListener());
        youtube_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Play !", Toast.LENGTH_SHORT).show();
                player.cueVideo(vid);
            }
        });


        //YouTube VideoView
        youtub_view = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youtub_view.initialize(DEVELOPER_KEY, new YoutubeOnInitializedListener());

    }


    private class YoutubeThumbnailOnInitializedListener implements YouTubeThumbnailView.OnInitializedListener{

        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
            youTubeThumbnailLoader.setVideo(vid);
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
            Toast.makeText(context, "Youtube onInitializationFailure !", Toast.LENGTH_SHORT).show();
        }
    }



    private class YoutubeOnInitializedListener implements YouTubePlayer.OnInitializedListener{

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            if(!b){
                youTubePlayer.loadVideo(vid);
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Toast.makeText(context, "Youtube onInitializationFailure !", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__way_li_try, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
