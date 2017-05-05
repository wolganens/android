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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;
import com.example.wolgan.viagemfinal.categories.CategoryDatabaseController;

public class ItemUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Item item;
    private long selectedCatId;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_update);
        this.item = getIntent().getExtras().getParcelable("EXTRA_ITEM");
        TextView textView = (TextView) findViewById(R.id.textUpdateItem);
        Spinner spinner = (Spinner) findViewById(R.id.categoryUpdateItem);
        textView.setText(item.getName());
        this.setCategoryListAdapter();
        spinner.setSelection(this.setSelectedCat());
    }
    public void updateItem(View view){
        EditText itemName = (EditText) findViewById(R.id.textUpdateItem);
        String newName = itemName.getText().toString();
        Object newCatId = this.selectedCatId;
        if (this.item.getName() == newName){
            System.out.println("Mesmo nome, não atualiza");
        } else {
            ItemDatabaseController dbController = new ItemDatabaseController(getBaseContext());
            int updated = dbController.update(this.item.getId(), newName, (long) newCatId);
            if (updated == 1){
                Toast.makeText(this,
                        "Item atualizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Falha ao atualizar",
                        Toast.LENGTH_SHORT).show();
            }
        }
        NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
    }
    public void deleteItem(View view){
        ItemDatabaseController dbController = new ItemDatabaseController(getBaseContext());
        int delete = dbController.delete(this.item.getId());
        if (delete == 1){
            Toast.makeText(this,
                    "Item excluído com sucesso!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Falha ao excluir item",
                    Toast.LENGTH_SHORT).show();
        }
        NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
    }
    private void setCategoryListAdapter(){
        CategoryDatabaseController dbCatController = new CategoryDatabaseController(getBaseContext());
        Cursor cursor = dbCatController.retrieve();
        String from[] = {"name"};
        int to[] = {R.id.categoryName};
        this.adapter = new SimpleCursorAdapter(this, R.layout.category, cursor, from, to, 0);
        Spinner spin = (Spinner) this.findViewById(R.id.categoryUpdateItem);
        spin.setAdapter(this.adapter);
        spin.setOnItemSelectedListener(this);
    }
    private int setSelectedCat(){
        int i;
        Cursor cursor;
        if (this.adapter.getCount() == 0) {
            return -1;
        } else {
            for (i = 0 ; i < this.adapter.getCount() ; i++){
                cursor = this.adapter.getCursor();
                cursor.moveToPosition(i);
                if(cursor.getLong(cursor.getColumnIndex("_id")) == this.item.getCategory()){
                    return i;
                }
            }
        }
        return -1;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
        this.selectedCatId = cursor.getLong(cursor.getColumnIndex("_id"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
