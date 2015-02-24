package com.codepath.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditItemActivity extends ActionBarActivity   {
    private EditText editItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemName = getIntent().getStringExtra("itemName");
        editItem = (EditText)findViewById(R.id.editItem_Id);
        editItem.append(itemName);
        Button savebtn = (Button)findViewById(R.id.savebtn_id);
        savebtn.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                String  position = getIntent().getStringExtra("position");
                String item = editItem.getText().toString();
                if(item.equals("") ){
                    Toast.makeText(EditItemActivity.this, "Item is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent data = new Intent();
                    // Pass relevant data back as a result
                    data.putExtra("position", position);
                    data.putExtra("editItem", item);
                    data.putExtra("code", 200); // ints work too
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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


}
