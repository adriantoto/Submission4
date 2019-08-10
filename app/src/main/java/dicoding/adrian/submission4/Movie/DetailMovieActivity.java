package dicoding.adrian.submission4.Movie;

import android.content.Intent;
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

import dicoding.adrian.submission4.Favorite.MovieFavorite.Database.MovieHelper;
import dicoding.adrian.submission4.R;

public class DetailMovieActivity extends AppCompatActivity {

    // Default Keys Values
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    // isEdit Declaration
    private boolean isEdit = false;

    /// Position Variable
    private int position;

    // Default Values
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    // Database Declaration
    private MovieHelper movieHelper;

    // Adaoter Declaration
    MovieAdapter adapter;

    // Instance Movie Items
    private MovieItems movie;

    // Widget Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    TextView txtScoreAngkaDetail;
    ImageView posterBanner;
    ImageView posterDetail;
    Button btnLike;
    ImageButton btnBack;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        // Movie Helper Instance
        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        // Adapter Instance
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

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
        btnLike = findViewById(R.id.btn_like_movie);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailMovie);
        progressBar.bringToFront();

        // Menerima Intent Movie dan Positon
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (position > 0) {
            isEdit = true;
        }

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
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, movie);
                intent.putExtra(EXTRA_POSITION, position);
                if (isEdit) {
                    String failedLike = getString(R.string.FailedLike);
                    Toast.makeText(DetailMovieActivity.this, failedLike, Toast.LENGTH_SHORT).show();
                } else {
                    String successLike = getString(R.string.like);
                    long result = movieHelper.insertMovie(movie);
                    movie.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailMovieActivity.this, successLike, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Animation onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailMovieActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
