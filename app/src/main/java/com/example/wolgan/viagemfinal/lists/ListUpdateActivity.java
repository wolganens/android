package com.example.wolgan.viagemfinal.lists;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.wolgan.viagemfinal.R;
import com.example.wolgan.viagemfinal.items.Item;
import com.example.wolgan.viagemfinal.items.ItemDatabaseController;
import com.example.wolgan.viagemfinal.items.ItemUpdateActivity;

public class ListUpdateActivity extends AppCompatActivity {
    private ItemDatabaseController dbItemController;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private List list;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_update);
        this.dbItemController = new ItemDatabaseController(this);
        this.list = getIntent().getExtras().getParcelable("EXTRA_LIST");
        TextView textView = (TextView) findViewById(R.id.updateListName);
        textView.setText(this.list.getName());
        this.cursor = dbItemController.findByList(this.list.getId());
        this.setAdapter();

    }
    private void setAdapter() {
        String from[] = {"name"};
        int to[] = {R.id.itemNameList};
        this.lista = (ListView) findViewById(R.id.listItemslistView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.adapter = new SimpleCursorAdapter(this, R.layout.item, this.cursor, from, to, 0);
        this.lista.setAdapter(this.adapter);
    }
    public void btnNewItemToList(View view){
        System.out.println(1);
        Intent intent = new Intent(this,ListInsertItem.class);
        System.out.println(2);
        intent.putExtra("EXTRA_LIST", (Parcelable) this.list);
        System.out.println(3);
        startActivity(intent);
    }
}
