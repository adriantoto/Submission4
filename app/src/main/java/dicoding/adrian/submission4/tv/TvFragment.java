package dicoding.adrian.submission4.tv;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        // Cast Layout
        final ConstraintLayout mainLayout = view.findViewById(R.id.container_fragment_tv);

        // Cast Widget
        final EditText etSearch = view.findViewById(R.id.search_tv);

        // OnEditorActionListener for Edit Text
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Keyboard hide after click the button
                    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                    // TV input
                    String tv = etSearch.getText().toString().trim();
                    // Search TV
                    mainViewModelTv.searchTv(tv);
                    progressBar.setVisibility(View.VISIBLE);
                    // If input is empty
                    if (TextUtils.isEmpty(tv)) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });

        // Toolbar Declaration
        Toolbar toolbarTv = view.findViewById(R.id.toolbar_tv);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarTv);

        // Adapter Instance
        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        // Cast Recyclerview
        rvTv = view.findViewById(R.id.rv_tv);

        // Recyclerview Layout Manager
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
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

        // Set Item
        mainViewModelTv.setTv();

        // Observer
        mainViewModelTv.getTvs().observe(Objects.requireNonNull(getActivity()), getTvs);
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
        // Toolbar Title
        Objects.requireNonNull(getActivity()).setTitle("");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }
}
