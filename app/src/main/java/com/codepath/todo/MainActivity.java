package com.codepath.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity   {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
   private  final int  REQUEST_CODE = 20;
    @Override
    //OnnCreate Activity is going to call when it loads
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        editListener();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String editItem = data.getExtras().getString("editItem");
            String position = data.getExtras().getString("position");
            items.set(Integer.parseInt(position),editItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

        }
    }


    private  void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delItem = items.get(position);
                  items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, delItem+ " Deleted Successfully", Toast.LENGTH_SHORT).show();
                writeItems();
                return true;
            }
        });
    }


    private  void editListener(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                // put "extras" into the bundle for access in the second activity
                String itemName = items.get(position);
                i.putExtra("position", position+"");
                i.putExtra("itemName", itemName);
                i.putExtra("code", 400);
                // brings up the second activity
                startActivityForResult(i, REQUEST_CODE);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {

        EditText etNewItem = (EditText)findViewById(R.id.item_id);
        String itemText = etNewItem.getText().toString();
        Log.i("","item text="+itemText);
        if(items.size() == 6){
            Toast.makeText(this,"MAX Limit Exceeded",Toast.LENGTH_SHORT).show();
        }
        if(itemText.equals("") ){
            Toast.makeText(this,"Please enter Item Name to Add",Toast.LENGTH_SHORT).show();
        }
           else {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
        }


    }

    private void readItems(){
        File filesDir = getFilesDir();
         File todoFile = new File(filesDir, "chatu.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e){
            items= new ArrayList<String>();
        }

    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"chatu.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }




}
