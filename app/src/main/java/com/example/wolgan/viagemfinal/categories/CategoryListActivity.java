package com.example.wolgan.viagemfinal.categories;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;

import org.w3c.dom.Text;

import java.io.Serializable;


public class CategoryListActivity extends AppCompatActivity {
    private CategoryDatabaseController dbController;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbController = new CategoryDatabaseController(getBaseContext());
        this.cursor = this.dbController.retrieve();
        setContentView(R.layout.activity_category_list);
        this.setAdapter();
    }
    public void insertCategory(View view){
        EditText categoryName = (EditText) findViewById(R.id.editText2);
        String message = categoryName.getText().toString();
        this.dbController.insert(message);
        this.cursor = this.dbController.retrieve();
        this.setAdapter();
    }
    private void loadCategoryList(){
        Cursor cursor = this.dbController.retrieve();
        this.lista = (ListView) findViewById(R.id.categoryListView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.setAdapter();
    }
    private void notifyDataChanged(){
        this.adapter.notifyDataSetChanged();
        this.lista.setAdapter(this.adapter);
    }
    private void setAdapter(){
        String from[] = {"name"};
        int to[] = {R.id.categoryName};
        this.lista = (ListView) findViewById(R.id.categoryListView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.adapter = new SimpleCursorAdapter(this, R.layout.category, this.cursor, from, to, 0);
        this.lista.setAdapter(this.adapter);
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), CategoryUpdateActivity.class);
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                Category category = dbController.find(_id);
                intent.putExtra("EXTRA_CATEGORY", (Parcelable) category);
                startActivity(intent);
            }
        });
    }
}
