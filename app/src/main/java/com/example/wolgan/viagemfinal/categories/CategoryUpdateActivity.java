package com.example.wolgan.viagemfinal.categories;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolgan.viagemfinal.R;

import java.util.Objects;

public class CategoryUpdateActivity extends AppCompatActivity {
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_update);
        this.category = getIntent().getExtras().getParcelable("EXTRA_CATEGORY");
        TextView textView = (TextView) findViewById(R.id.textUpdateCategory);
        textView.setText(category.getName());
        System.out.println(category);
    }
    public void updateCategory(View view){
        EditText categoryName = (EditText) findViewById(R.id.textUpdateCategory);
        String newName = categoryName.getText().toString();
        if (this.category.getName().equals(newName)){
            Toast.makeText(this,
                    "Sem alterações!",
                    Toast.LENGTH_SHORT).show();
        } else {
            CategoryDatabaseController dbController = new CategoryDatabaseController(getBaseContext());
            int updated = dbController.update(this.category.getId(), newName);
            if (updated == 1){
                Toast.makeText(this,
                        "Categoria atualizada com sucesso!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Falha ao atualizar",
                        Toast.LENGTH_SHORT).show();
            }
        }
        NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
    }
    public void deleteCategory(View view){
        CategoryDatabaseController dbController = new CategoryDatabaseController(getBaseContext());
        int delete = dbController.delete(this.category.getId());
        if (delete == 1){
            Toast.makeText(this,
                    "Categoria excluída com sucesso!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Falha ao excluir",
                    Toast.LENGTH_SHORT).show();
        }
        NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
    }
}
