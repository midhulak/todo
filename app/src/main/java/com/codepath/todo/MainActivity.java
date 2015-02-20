package com.codepath.todo;

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


public class MainActivity extends ActionBarActivity  {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
       // items = new ArrayList<String>();
       readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
       setupListViewListener();

    }

    public void onClick(View v){

    }

    private  void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("","coming here="+position);
                  items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onAddItem(View view) {

        EditText etNewItem = (EditText)findViewById(R.id.item_id);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
         writeItems();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        System.out.println("filesdir is="+filesDir);
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

    public void OnDelete(View view) {
        EditText etdelItem = (EditText)findViewById(R.id.delitem_id);
        int delpos = Integer.parseInt(etdelItem.getText().toString());
        String rmvItem = items.get(delpos);
        items.remove(delpos);
        itemsAdapter.notifyDataSetChanged();
        int sizeitem  = items.size();
        Toast.makeText(this, rmvItem+" Deleted Successfully", Toast.LENGTH_SHORT).show();
     //   String sizeitem = items.size()+"";
      //  Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
     // Log.d("items size",items.size()+"");
     writeItems();
    }


}
