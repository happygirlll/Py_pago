package com.example.sw.pypago;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sw.pypago.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity_memo extends AppCompatActivity {
    MemoDB helper;
    SQLiteDatabase db;
    MyAdapter adapter;
    Cursor cursor;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memo);


        getSupportActionBar().setTitle("개발일지");

        getSupportActionBar().setDisplayShowHomeEnabled(true);



        helper=new MemoDB(this);
        db=helper.getReadableDatabase();

        cursor=db.rawQuery("select * from memo order by wdate desc",null);
        list=findViewById(R.id.list);
        adapter=new MyAdapter(this,cursor);
        list.setAdapter(adapter);


        FloatingActionButton btnwrite=findViewById(R.id.btnwrite);
        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity_memo.this,InsertActivity.class);
                startActivity(intent);
            }
        });

    }

    class MyAdapter extends CursorAdapter{
        public MyAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return getLayoutInflater().inflate(R.layout.item,parent,false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            TextView txtcontent=view.findViewById(R.id.txtcontent);
            txtcontent.setText(cursor.getString(1));
            TextView txtwdate=view.findViewById(R.id.txtwdate);
            txtwdate.setText(cursor.getString(2));

            ImageView btndel=view.findViewById(R.id.btndel);

            final int _id=cursor.getInt(0);

            btndel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder box=new AlertDialog.Builder(MainActivity_memo.this);
                    box.setMessage("해당 글을 삭제하시겠습니까?");
                    box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String sql="delete from memo where _id="+_id;
                            db.execSQL(sql);

                            onRestart();
                        }
                    });
                    box.setNegativeButton("닫기",null);
                    box.show();
                }
            });
            ImageView btnupdate=view.findViewById(R.id.btnupdate);
            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity_memo.this,UpdateActivity.class);
                    intent.putExtra("_id", _id);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem search=menu.findItem(R.id.search);
        SearchView view=(SearchView)search.getActionView();

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String sql="select * from memo where content like  '%" + newText + "%'";
                cursor=db.rawQuery(sql,null);
                adapter.changeCursor(cursor);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemcontent:
                cursor=db.rawQuery("select * from memo order by content",null);
                break;
            case R.id.itemwdate:
                cursor=db.rawQuery("select * from memo order by wdate desc",null);
                break;
        }

        adapter.changeCursor(cursor);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        cursor=db.rawQuery("select * from memo order by wdate desc",null);
        adapter.changeCursor(cursor);
        super.onRestart();
    }
}