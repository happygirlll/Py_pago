package com.example.sw.pypago;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sw.pypago.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class TabFragment1 extends Fragment {
    private Button btn1;
    private Button btn2;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectRef = db.collection("프로젝트");

    private ProjectAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        Query query = projectRef.orderBy("date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ProjectInfo> options = new FirestoreRecyclerOptions.Builder<ProjectInfo>()
                .setQuery(query,ProjectInfo.class)
                .build();
        adapter = new ProjectAdapter(options);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.title_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ProjectInfo info = documentSnapshot.toObject(ProjectInfo.class);
                String id = documentSnapshot.getId();
                String title = info.getTitle();
                String day = info.getDate();
                String pur = info.getPurpose();
                String path = documentSnapshot.getReference().getPath();
                Intent intent = new Intent(getActivity(),ProjectBoardActivity.class);

                intent.putExtra("project_docuId",id);
                intent.putExtra("project_path",path);
                intent.putExtra("project_title",title);
                intent.putExtra("project_day",day);
                intent.putExtra("project_pur",pur);
                startActivity(intent);
            }
        });


        return view;


    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}