package com.example.sidsharma.ordersheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button addItem;
    private ListView mListView1;
    private ArrayAdapter listAdapter;
    private CustomAdapter customAdapter;
    private static int count;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayList<CustomData> customItems=new ArrayList<CustomData>();
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        count = 0;
    }
    public class CustomData{
        String mItemName;
        float mPrice;
        public CustomData(String name, float price){
            this.mItemName  = name;
            this.mPrice     = price;
        }
        public String mGetItemName(){
            return mItemName;
        }
        public float mGetItemPrice(){
            return mPrice;
        }
    }
    public class CustomAdapter extends BaseAdapter {
        private LayoutInflater mInflate;
        private ArrayList<CustomData> mCustomObjects;

        public void add(String s, int i) {
            mCustomObjects.add(new CustomData(s, i));
        }

        public void add(CustomData data) {
            mCustomObjects.add(data);
        }

        private class ViewHolder {
            TextView tv_name;
            TextView tv_price;
        }
        public CustomAdapter(Context context, ArrayList<CustomData>  objects) {
            mInflate = LayoutInflater.from(context);
            this. mCustomObjects = objects;
        }
        @Override
        public int getCount() {
            return mCustomObjects.size();
        }

        @Override
        public CustomData getItem(int position) {
            return mCustomObjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder = null;
            if(convertView == null){
                System.out.println("convert view is NULL!");
                convertView = mInflate.inflate(R.layout.activity_listview, null);
                viewholder = new ViewHolder();
                viewholder.tv_name = (TextView) convertView.findViewById(R.id.ItemName);
                // viewholder.tv_name = (TextView) findViewById(R.id.ItemName); //this works only with layout "activity_main"
                if(viewholder.tv_name == null)
                    System.out.println("Failed memory allocation");
                // viewholder.tv_price = (TextView) findViewById(R.id.Price);
                viewholder.tv_price = (TextView) convertView.findViewById(R.id.Price);
                convertView.setTag(viewholder);
            }
            else{
                System.out.println("convert view is valid!");
                viewholder = (ViewHolder) convertView.getTag();
            }
            viewholder.tv_name.setText(mCustomObjects.get(position).mGetItemName());
            viewholder.tv_price.setText(Float.toString(mCustomObjects.get(position).mGetItemPrice()));
            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView1 = (ListView) findViewById(R.id.mListView1);
        addItem = (Button) findViewById(R.id.mButton1);
        //listAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, listItems);
        customAdapter = new CustomAdapter(this, customItems);
        //mListView1.setAdapter(listAdapter);
        mListView1.setAdapter(customAdapter);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Added new Item!" + count, Toast.LENGTH_SHORT);
                toast.show();
                //listAdapter.add("New Item" + count++);
                CustomData data = new CustomData("New Item", count++);
                customAdapter.add(data);
                //listAdapter.notifyDataSetChanged();
                customAdapter.notifyDataSetChanged();
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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
