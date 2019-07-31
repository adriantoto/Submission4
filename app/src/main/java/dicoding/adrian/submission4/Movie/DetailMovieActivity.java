package dicoding.adrian.submission4.Movie;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.like.LikeButton;
import com.like.OnLikeListener;

import dicoding.adrian.submission4.R;

public class DetailMovieActivity extends AppCompatActivity {

    // Default Value
    public static final String EXTRA_MOVIE = "extra_movie";

    // Data Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    TextView txtScoreAngkaDetail;
    ImageView posterBanner;
    ImageView posterDetail;

    // Like Button Declaration
    LikeButton likeButton;

    // Button Variable Declaration
    ImageButton btnBack;

    // Progress Bar Variable Declaration
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        // TextView Layout Gradient
        TextView myBackground = findViewById(R.id.textView5);
        AnimationDrawable animationDrawable = (AnimationDrawable) myBackground.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Translucent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Casting Data Variables
        txtTitleDetail = findViewById(R.id.txt_title_detail);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail);
        txtScoreAngkaDetail = findViewById(R.id.txt_scoreAngkaDetail);
        posterBanner = findViewById(R.id.poster_banner);
        posterDetail = findViewById(R.id.poster_detail);

        // Casting Button Variables
        btnBack = findViewById(R.id.btn_back);
        likeButton = findViewById(R.id.star_button);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailMovie);
        progressBar.bringToFront();

        // Menerima intent
        final MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        // Mengisi data String
        txtTitleDetail.setText(movie.getTitle());
        txtOverviewDetail.setText(movie.getOverview());
        double score = movie.getScore() * 10;
        txtScoreAngkaDetail.setText(String.valueOf((int) score));

        // Mengisi data image
        String url = "https://image.tmdb.org/t/p/original" + movie.getPoster();
        Glide.with(DetailMovieActivity.this).load(url).into(posterBanner);
        Glide.with(DetailMovieActivity.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(posterDetail);

        // setOnClickListener untuk Button Like
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(DetailMovieActivity.this, "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(DetailMovieActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
            }
        });

        // setOnClickListener untuk Button Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });
    }

    // Animation onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailMovieActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

}
