package com.example.wolgan.viagemfinal.lists;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;

public class ListCreateActivity extends AppCompatActivity {

    private ListDatabaseController dbListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_create);
        this.dbListController = new ListDatabaseController(this);
    }
    public void createList(View view) {
        EditText listNameView = (EditText) findViewById(R.id.createlistName);
        String itemName = listNameView.getText().toString();
        long insert = this.dbListController.insert(itemName);
        if ( insert > 0 ) {
            Toast.makeText(this,
                    "Item cadastrado com sucesso!",
                    Toast.LENGTH_SHORT).show();
            NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
        }
    }
}
