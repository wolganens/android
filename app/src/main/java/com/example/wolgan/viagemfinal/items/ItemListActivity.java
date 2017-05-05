package com.example.wolgan.viagemfinal.items;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wolgan.viagemfinal.R;

public class ItemListActivity extends AppCompatActivity {
    ItemDatabaseController dbController;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbController = new ItemDatabaseController(getBaseContext());
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.cursor = this.dbController.retrieve();
        this.setAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ItemCreateActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setAdapter() {
        String from[] = {"name"};
        int to[] = {R.id.itemNameList};
        this.lista = (ListView) findViewById(R.id.itemListView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.adapter = new SimpleCursorAdapter(this, R.layout.item, this.cursor, from, to, 0);
        this.lista.setAdapter(this.adapter);
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ItemUpdateActivity.class);
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                Item item = dbController.find(_id);
                intent.putExtra("EXTRA_ITEM", (Parcelable) item);
                startActivity(intent);
            }
        });
    }

}
