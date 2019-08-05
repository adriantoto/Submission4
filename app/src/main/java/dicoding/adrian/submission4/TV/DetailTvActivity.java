package dicoding.adrian.submission4.TV;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import dicoding.adrian.submission4.Movie.DetailMovieActivity;
import dicoding.adrian.submission4.R;

public class DetailTvActivity extends AppCompatActivity {

    // Default Value
    public static final String EXTRA_TV = "extra_tv";

    // Data Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    TextView txtScoreAngkaDetail;
    ImageView posterBanner;
    ImageView posterDetail;

    // Favorite Button Declaration
    Button btnLike;
    Button btnDislike;

    // Button Variable Declaration
    ImageButton btnBack;

    // Progress Bar Variable Declaration
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        // TextView Layout Gradient
        TextView myBackground = findViewById(R.id.textView5_tv);
        AnimationDrawable animationDrawable = (AnimationDrawable) myBackground.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Translucent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Casting Data Variables
        txtTitleDetail = findViewById(R.id.txt_title_detail_tv);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail_tv);
        txtScoreAngkaDetail = findViewById(R.id.txt_scoreAngkaDetail_tv);
        posterBanner = findViewById(R.id.poster_banner_tv);
        posterDetail = findViewById(R.id.poster_detail_tv);

        // Casting Button Variables
        btnBack = findViewById(R.id.btn_back_tv);
        btnLike = findViewById(R.id.btn_like_tv);
        btnDislike = findViewById(R.id.btn_dislike_tv);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailMovie_tv);
        progressBar.bringToFront();

        // Menerima intent
        final TvItems tv = getIntent().getParcelableExtra(EXTRA_TV);

        // Mengisi data String
        txtTitleDetail.setText(tv.getTitle());
        txtOverviewDetail.setText(tv.getOverview());
        double score = tv.getScore() * 10;
        txtScoreAngkaDetail.setText(String.valueOf((int) score));

        // Mengisi data image
        String url = "https://image.tmdb.org/t/p/original" + tv.getPoster();
        Glide.with(DetailTvActivity.this).load(url).into(posterBanner);
        Glide.with(DetailTvActivity.this)
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

        // setOnClickListener untuk Button Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });

        // setOnClickListener untuk Button Like
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailTvActivity.this, "Like", Toast.LENGTH_SHORT).show();
            }
        });

        // setOnClickListener untuk Button Dislike
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailTvActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Animation onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailTvActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

}
