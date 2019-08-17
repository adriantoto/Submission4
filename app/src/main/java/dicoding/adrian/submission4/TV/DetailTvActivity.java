package dicoding.adrian.submission4.TV;

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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import dicoding.adrian.submission4.Favorite.TvFavorite.Database.TvHelper;
import dicoding.adrian.submission4.R;

public class DetailTvActivity extends AppCompatActivity {

    // Default Keys Values
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    // isEdit Declaration
    private boolean isEdit = false;

    /// Position Variable
    private int position;

    // Default Value
    public static final String EXTRA_TV = "extra_tv";
    public static final String EXTRA_POSITION = "extra_position";

    // Database Declaration
    private TvHelper tvHelper;

    // Adaoter Declaration
    TvAdapter adapter;

    // Instance Movie Items
    private TvItems tv;

    // Data Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    ImageView posterBanner;
    Button btnLike;
    ImageButton btnBack;
    ProgressBar progressBar;
    RatingBar scoreDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        // Movie Helper Instance
        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        // Adapter Instance
        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        // Translucent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Casting Data Variables
        txtTitleDetail = findViewById(R.id.txt_title_detail_tv);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail_tv);
        posterBanner = findViewById(R.id.poster_banner_tv);
        scoreDetail = findViewById(R.id.score_detail_tv);

        // Casting Button Variables
        btnBack = findViewById(R.id.btn_back_tv);
        btnLike = findViewById(R.id.btn_like_tv);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailTv);
        progressBar.bringToFront();

        // Menerima intent
        tv = getIntent().getParcelableExtra(EXTRA_TV);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (position > 0) {
            isEdit = true;
        }

        // Mengisi data String
        txtTitleDetail.setText(tv.getTitle());
        txtOverviewDetail.setText(tv.getOverview());
        double score = tv.getScore() * 10;
        scoreDetail.setRating((float) ((score * 5) / 100));

        // Mengisi data image
        String url = "https://image.tmdb.org/t/p/original" + tv.getBackdrop();
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
                .into(posterBanner);

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
                intent.putExtra(EXTRA_TV, tv);
                intent.putExtra(EXTRA_POSITION, position);
                if (isEdit) {
                    String failedLike = getString(R.string.FailedLike);
                    Toast.makeText(DetailTvActivity.this, failedLike, Toast.LENGTH_SHORT).show();
                } else {
                    String successLike = getString(R.string.like);
                    long result = tvHelper.insertTv(tv);
                    tv.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailTvActivity.this, successLike, Toast.LENGTH_SHORT).show();
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
        DetailTvActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
