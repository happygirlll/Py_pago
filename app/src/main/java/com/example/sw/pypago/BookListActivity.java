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

public class BookListActivity extends AppCompatActivity {
    private AppDataAdapter mAdapter;
    private List<AppData> appDataArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);

        bookData();

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
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AppDataAdapter(appDataArrayList,this);

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

    private void bookData() {
        appDataArrayList = new ArrayList<>();
        appDataArrayList.add(new AppData("Do it! 점프 투 파이썬", "박응용","이지스퍼블리싱","2019.06.20","16,920원","http://www.yes24.com/Product/Goods/24567417?scode=032&OzSrank=1", "https://shopping-phinf.pstatic.net/main_3245689/32456895000.20221019105649.jpg?type=w300"));
        appDataArrayList.add(new AppData("Do it! 알고리즘 코딩 테스트: 파이썬 편","김종관","이지스퍼블리싱", "2022.08.16","28,800원","http://www.yes24.com/Product/Goods/114276464","https://shopping-phinf.pstatic.net/main_3393843/33938438618.20221019145521.jpg?type=w300"));
        appDataArrayList.add(new AppData("어쩌다 데이터 분석 with 파이썬","김유지","한빛미디어", "2022.09.29","25,200원","http://www.yes24.com/Product/Goods/113476041","https://shopping-phinf.pstatic.net/main_3483069/34830698713.20221019140207.jpg?type=w300"));
        appDataArrayList.add(new AppData("혼자 공부하는 파이썬 (1:1 과외하듯 배우는 프로그래밍 자습서)","윤인성","한빛미디어", "2022.06.01","19,800원","http://www.yes24.com/Product/Goods/109625396","https://shopping-phinf.pstatic.net/main_3250760/32507605957.20221019133018.jpg?type=w300"));
        appDataArrayList.add(new AppData("파이썬 클린 코드 2nd Edition","마리아노 아나야","터닝포인트", "2022.11.11","26,100원","http://www.yes24.com/Product/Goods/114667254","https://shopping-phinf.pstatic.net/main_3521245/35212455631.20221019140442.jpg?type=w300"));
        appDataArrayList.add(new AppData("파이썬 머신러닝 완벽 가이드","권철민","위키북스", "2022.04.21","36,000원","http://www.yes24.com/Product/Goods/108824557","https://shopping-phinf.pstatic.net/main_3248589/32485894885.20221019134634.jpg?type=w300"));
        appDataArrayList.add(new AppData("만들면서 배우는 파이썬과 40개의 작품들","장문철","앤써북", "2022.02.28","16,920원","http://www.yes24.com/Product/Goods/107490270","https://image.aladin.co.kr/product/28919/57/cover500/k132836119_1.jpg"));
        appDataArrayList.add(new AppData("파이썬 알고리즘 인터뷰","박상길","책만", "2020.07.15","34,200원","http://www.yes24.com/Product/Goods/109625396","https://shopping-phinf.pstatic.net/main_3245648/32456486633.20221019145547.jpg?type=w300"));
        appDataArrayList.add(new AppData("안녕, 파이썬","김학인","북랩", "2022.03.14","16,110원","http://www.yes24.com/Product/Goods/107995262","https://shopping-phinf.pstatic.net/main_3248712/32487126181.20221019152815.jpg?type=w300"));
        appDataArrayList.add(new AppData("Do it! 첫 파이썬","엘리스 코딩","이지스퍼블리싱", "\n" +
                "2020.04.10","12,600원","http://www.yes24.com/Product/Goods/89904189","https://shopping-phinf.pstatic.net/main_3244559/32445596386.20221019132828.jpg?type=w300"));

    }
}

