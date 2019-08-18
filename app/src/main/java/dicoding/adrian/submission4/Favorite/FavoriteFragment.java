package dicoding.adrian.submission4.Favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import dicoding.adrian.submission4.Favorite.MovieFavorite.MovieFavoriteFragment;
import dicoding.adrian.submission4.Favorite.TvFavorite.TvFavoriteFragment;
import dicoding.adrian.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    // Empty Constructor
    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Toolbar Declaration
        Toolbar toolbarFavorite = view.findViewById(R.id.toolbar_favorite);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarFavorite);

        // Tab Configuration
        TabLayout tabLayoutFavorite = view.findViewById(R.id.tablayout_favorite);
        NonSwipeableViewPager viewPagerFavorite = view.findViewById(R.id.viewpager_favorite);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        // Adding Fragments
        adapter.AddFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        adapter.AddFragment(new TvFavoriteFragment(), getString(R.string.tv_show));

        // Adapter Setup
        viewPagerFavorite.setAdapter(adapter);
        tabLayoutFavorite.setupWithViewPager(viewPagerFavorite);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Toolbar Title
        Objects.requireNonNull(getActivity()).setTitle("");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}
