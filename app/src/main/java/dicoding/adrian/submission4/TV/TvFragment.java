package dicoding.adrian.submission4.TV;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;

import dicoding.adrian.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    // Variables
    RecyclerView rvTv;
    private TvAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModelTv mainViewModelTv;

    // Empty Constructor
    public TvFragment() {
        // Required empty public constructor
    }

    // Observer
    private Observer<ArrayList<TvItems>> getTvs = new Observer<ArrayList<TvItems>>() {
        @Override
        public void onChanged(ArrayList<TvItems> tvItems) {
            if (tvItems != null) {
                adapter.setData(tvItems);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adapter Instance
        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        // Cast Recyclerview
        rvTv = view.findViewById(R.id.rv_tv);

        // Layout Manager
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvTv.addItemDecoration(itemDecorator);
        rvTv.setHasFixedSize(true);

        //Set Adapter
        rvTv.setAdapter(adapter);

        // Progress Bar Declaration
        progressBar = view.findViewById(R.id.progressBar_tv);
        progressBar.bringToFront();

        //Intent to Detail Activity
        adapter.setOnItemClickListener(new TvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TvItems tvItems) {
                // Define and Start Intent
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailTvActivity.class);
                moveWithObjectIntent.putExtra(DetailTvActivity.EXTRA_TV, tvItems);
                Objects.requireNonNull(getActivity()).startActivity(moveWithObjectIntent);

                // Intent Transition Animation
                (getActivity()).overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel Instance
        mainViewModelTv = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModelTv.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        mainViewModelTv.getTvs().observe(Objects.requireNonNull(getActivity()), getTvs);

        // Display The Items
        mainViewModelTv.setTv();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        mainViewModelTv.getTvs().removeObserver(getTvs);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }
}
