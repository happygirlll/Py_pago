package com.example.sw.pypago;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sw.pypago.R;

import java.util.ArrayList;
import java.util.List;

public class ProblemListActivity extends AppCompatActivity {
    private ProblemDataAdapter mAdapter;
    private List<ProvblemData> appDataArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemlist);

        PrbolemData();

        setUpRecyclerView();
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appDataArrayList.get(position).getApplink()));
                intent.setData(Uri.parse(appDataArrayList.get(position).getApplink()));
                startActivity(intent);
            }
        });


        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                return true;
            }
        });

    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.problem_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProblemDataAdapter(appDataArrayList,this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void PrbolemData() {
        appDataArrayList = new ArrayList<>();
        appDataArrayList.add(new ProvblemData("1. 파이썬 시작하기", "https://wikidocs.net/7014"));
        appDataArrayList.add(new ProvblemData("2. 파이썬 변수", "https://wikidocs.net/7021"));
        appDataArrayList.add(new ProvblemData("3. 파이썬 문자열", "https://wikidocs.net/7022"));
        appDataArrayList.add(new ProvblemData("4. 파이썬 리스트", "https://wikidocs.net/7023"));
        appDataArrayList.add(new ProvblemData("5. 파이썬 튜플", "https://wikidocs.net/7027"));
        appDataArrayList.add(new ProvblemData("6. 파이썬 딕셔너리", "https://wikidocs.net/22000"));
        appDataArrayList.add(new ProvblemData("7. 파이썬 분기문", "https://wikidocs.net/7028"));
        appDataArrayList.add(new ProvblemData("8. 파이썬 반복문", "https://wikidocs.net/78562"));
        appDataArrayList.add(new ProvblemData("9. 파이썬 함수", "https://wikidocs.net/23906"));
        appDataArrayList.add(new ProvblemData("10. 파이썬 모듈", "https://wikidocs.net/7040"));
        appDataArrayList.add(new ProvblemData("11. 파이썬 클래스", "https://wikidocs.net/7035"));
        appDataArrayList.add(new ProvblemData("12. 파이썬 파일 입출력과 예외처리", "https://wikidocs.net/7044"));





    }
}

