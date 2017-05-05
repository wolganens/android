package com.example.wolgan.viagemfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wolgan.viagemfinal.categories.Category;
import com.example.wolgan.viagemfinal.categories.CategoryListActivity;
import com.example.wolgan.viagemfinal.categories.CategoryUpdateActivity;
import com.example.wolgan.viagemfinal.items.ItemListActivity;
import com.example.wolgan.viagemfinal.lists.List;
import com.example.wolgan.viagemfinal.lists.ListCreateActivity;
import com.example.wolgan.viagemfinal.lists.ListDatabaseController;
import com.example.wolgan.viagemfinal.lists.ListUpdateActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListDatabaseController dbController;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setDbController(new ListDatabaseController(this));
        this.cursor = dbController.retrieve();
        this.setAdapter();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListCreateActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            this.menuChangeActivity(CategoryListActivity.class);
        } else if (id == R.id.nav_gallery) {
            this.menuChangeActivity(ItemListActivity.class);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void menuChangeActivity(Class test){
        Intent intent = new Intent(this, test);
        startActivity(intent);
    }
    private void setDbController(ListDatabaseController dbController){
        this.dbController = dbController;
    }
    private void setAdapter(){
        String from[] = {"name"};
        int to[] = {R.id.categoryName};
        this.lista = (ListView) findViewById(R.id.listListView);
        this.lista.setEmptyView(findViewById(R.id.empty));
        this.adapter = new SimpleCursorAdapter(this, R.layout.category, this.cursor, from, to, 0);
        this.lista.setAdapter(this.adapter);
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ListUpdateActivity.class);
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                List list = dbController.find(_id);
                intent.putExtra("EXTRA_LIST", (Parcelable) list);
                startActivity(intent);
            }
        });
    }
}
