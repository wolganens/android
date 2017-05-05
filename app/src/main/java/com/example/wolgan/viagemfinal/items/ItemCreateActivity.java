package com.example.wolgan.viagemfinal.items;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;
import com.example.wolgan.viagemfinal.categories.CategoryDatabaseController;

public class ItemCreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private CategoryDatabaseController dbCatController;
    private ItemDatabaseController dbItemController;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private long selectedCatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);
        this.dbCatController = new CategoryDatabaseController(getBaseContext());
        this.dbItemController = new ItemDatabaseController(getBaseContext());
        this.cursor = this.dbCatController.retrieve();
        String from[] = {"name"};
        int to[] = {R.id.categoryName};
        this.adapter = new SimpleCursorAdapter(this, R.layout.category, this.cursor, from, to, 0);
        Spinner spin = (Spinner) this.findViewById(R.id.spinnerCategory);
        spin.setAdapter(this.adapter);
        spin.setOnItemSelectedListener(this);
    }
    public void insert(View view) {
        EditText itemNameView = (EditText) findViewById(R.id.createItemName);
        String itemName = itemNameView.getText().toString();
        long insert = this.dbItemController.insert(itemName, this.selectedCatId);
        if ( insert > 0 ) {
            Toast.makeText(this,
                    "Item cadastrado com sucesso!",
                    Toast.LENGTH_SHORT).show();
            NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
        }

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(pos);
        this.selectedCatId = cursor.getLong(cursor.getColumnIndex("_id"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
