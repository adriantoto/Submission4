package dicoding.adrian.submission4.favorite.MovieFavorite.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import dicoding.adrian.submission4.basic.CustomOnItemClickListener;
import dicoding.adrian.submission4.favorite.MovieFavorite.DetailMovieFavoriteActivity;
import dicoding.adrian.submission4.movie.MovieItems;
import dicoding.adrian.submission4.R;

import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.CONTENT_URI;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder> {

    private ArrayList<MovieItems> listMovies = new ArrayList<>();
    private Activity activity;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieItems> getListMovies() {
        return listMovies;
    }

    public void setListMovie(ArrayList<MovieItems> listMovies) {
        if (listMovies.size() > 0) {
            this.listMovies.clear();
        }
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    public void addItem(MovieItems movieItems) {
        this.listMovies.add(movieItems);
        notifyItemInserted(listMovies.size() - 1);
    }

    public void removeItem(int position) {
        this.listMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovies.size());
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movie, parent, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoriteAdapter.MovieFavoriteViewHolder holder, int position) {

        // String value
        double score = getListMovies().get(position).getScore() * 10;
        holder.tvScore.setText(String.valueOf((int) score));

        // Image Value
        String uri = "https://image.tmdb.org/t/p/original" + getListMovies().get(position).getPoster();
        Glide.with(holder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pgMovie.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivPoster);

        // Intent To Detail Activity
        holder.itemFavoriteMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                // Define and Start Intent
                Intent intent = new Intent(activity, DetailMovieFavoriteActivity.class);

                Uri uri = Uri.parse(CONTENT_URI + "/" + getListMovies().get(position).getId());
                intent.setData(uri);

                intent.putExtra(DetailMovieFavoriteActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailMovieFavoriteActivity.EXTRA_MOVIE, listMovies.get(position));
                activity.startActivityForResult(intent, DetailMovieFavoriteActivity.REQUEST_UPDATE);

                // Intent Transition Animation
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPoster;
        final ProgressBar pgMovie;
        final ConstraintLayout itemFavoriteMovie;
        final TextView tvScore;

        MovieFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tv_item_scoreAngkaHome_favorite_movie);
            ivPoster = itemView.findViewById(R.id.img_item_poster_favorite_movie);
            pgMovie = itemView.findViewById(R.id.progressBar_item_favorite_movie);
            itemFavoriteMovie = itemView.findViewById(R.id.cv_favorite_movie);
        }
    }
}