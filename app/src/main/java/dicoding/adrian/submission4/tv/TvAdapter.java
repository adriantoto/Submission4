package dicoding.adrian.submission4.tv;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import dicoding.adrian.submission4.R;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private ArrayList<TvItems> mData = new ArrayList<>();
    private TvAdapter.OnItemClickListener listener;

    void setData(ArrayList<TvItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_items, viewGroup, false);
        return new TvViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder tvViewHolder, int position) {
        double score = mData.get(position).getScore() * 10;
        tvViewHolder.textViewScore.setText(String.valueOf((int) score));
        String uri = "https://image.tmdb.org/t/p/original" + mData.get(position).getPoster();
        Glide.with(tvViewHolder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        tvViewHolder.progressBarItemTv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(tvViewHolder.poster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView textViewScore;
        ProgressBar progressBarItemTv;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_item_poster_tv);
            textViewScore = itemView.findViewById(R.id.tv_item_scoreAngkaHome_tv);
            progressBarItemTv = itemView.findViewById(R.id.progressBar_itemTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mData.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TvItems tvItems);
    }

    void setOnItemClickListener(TvAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
