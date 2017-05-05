package com.example.wolgan.viagemfinal.lists;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;
import com.example.wolgan.viagemfinal.items.Item;
import com.example.wolgan.viagemfinal.items.ItemDatabaseController;
import com.example.wolgan.viagemfinal.items.ItemUpdateActivity;

public class ListInsertItem extends AppCompatActivity {
    private List list;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    ItemDatabaseController dbController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_insert_item);
        this.dbController = new ItemDatabaseController(this);
        this.cursor = this.dbController.retrieve();
        this.list = getIntent().getExtras().getParcelable("EXTRA_LIST");
        this.setAdapter();
    }
    private void setAdapter() {
        String from[] = {"name"};
        int to[] = {R.id.itemNameList};
        this.lista = (ListView) findViewById(R.id.itemToListListView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.adapter = new SimpleCursorAdapter(this, R.layout.item, this.cursor, from, to, 0);
        this.lista.setAdapter(this.adapter);
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                Item item = dbController.find(_id);
                long l = dbController.insertItemToList(item.getId(), list.getId());
                if (l > 0) {
                    Toast.makeText(getBaseContext(),
                            "Item inserido na lista!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(),
                            "Erro ao inserir item na lista!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
