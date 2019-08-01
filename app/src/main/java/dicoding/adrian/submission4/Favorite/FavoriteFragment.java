package dicoding.adrian.submission4.Favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Toolbar Title
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.favorite));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
