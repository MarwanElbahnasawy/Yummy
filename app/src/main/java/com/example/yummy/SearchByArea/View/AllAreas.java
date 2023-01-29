package com.example.yummy.SearchByArea.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.R;
import com.example.yummy.SearchByArea.Presenter.InterfaceAllAreas;
import com.example.yummy.SearchByArea.Presenter.PresenterAllAreas;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

public class AllAreas extends Fragment implements InterfaceAllAreas {
    private List<EachAreaModel> areas;
    private AllAreasAdapter allAreasAdapter;
    private RecyclerView recyclerView;
    public static TextInputEditText textInputEditText;
    private PresenterAllAreas presenterAllAreas;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_coun);
        textInputEditText = view.findViewById(R.id.tinput_search);

        presenterAllAreas = new PresenterAllAreas(this);
        presenterAllAreas.getAllAreas();


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                allAreasAdapter = new AllAreasAdapter(areas.stream().filter(
                        EachAreaModel -> EachAreaModel.getStrArea().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(allAreasAdapter);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_search_b_y_country, container, false);
    }

    @Override
    public void responseOfDataOnSuccess(List<EachAreaModel> aresReceived) {
        areas = aresReceived;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        allAreasAdapter = new AllAreasAdapter(areas);
        recyclerView.setAdapter(allAreasAdapter);
    }
}