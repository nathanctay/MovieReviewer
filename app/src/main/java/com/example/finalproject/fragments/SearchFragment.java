package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.SearchAdapter;
import com.example.finalproject.api.tmdbAPI;
import com.example.finalproject.models.SearchItem;
import com.example.finalproject.viewmodel.ReviewsViewModel;


public class SearchFragment extends Fragment {

    public SearchFragment() {
        super(R.layout.search_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewsViewModel viewModel = new ViewModelProvider(getActivity()).get(ReviewsViewModel.class);
        view.findViewById(R.id.search_fab).setOnClickListener(fab -> {
            EditText editText = view.findViewById(R.id.search_edit_text);
            String search = editText.getText().toString();
            while (search.contains(" ")) {
                int spaceIndex = search.indexOf(" ");
                search = search.substring(0, spaceIndex) + "+" + search.substring(spaceIndex + 1);
            }
            tmdbAPI api = tmdbAPI.getInstance(getContext());
            api.getMovieTitle(new tmdbAPI.OnRequestCompleteListener<SearchItem>() {
                @Override
                public void onComplete(SearchItem[] results, String errorMessage) {
                    RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new SearchAdapter(results, result -> {
                        viewModel.setCurrentSearch(result);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, CreateReviewFragment.class, null)
                                .setReorderingAllowed(true)
                                .commit();
                    }));
                }
            }, search);
        });

    }
}
