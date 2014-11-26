package com.pieronex.smartcontact;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * <h1>Main Activity</h1>
 * This class acts as a controller between the models and the views.
 *
 *
 *
 * @author Puriwat Khantiviriya
 * @author Sasawat Chanate
 * @author Thitiwat Watanajaturaporn
 * @version 1.0
 * @since   2014-11-25
 */


public class MainActivity extends Activity {

    List<String> contacts = new ArrayList<String>();
    List<Account> accountModel = new ArrayList<Account>();
    private boolean[] booleanFromChild;

    //ArrayAdapter<Account> adapter;
    private ArrayAdapter adapter;
    private ListView mListView;
    private TextView NoContact_LB;
    private SwipeRefreshLayout mSwipeContainer;

    //SearchBar
    private SearchView mSearchBar;
    private RelativeLayout mRelativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidget();//Init Activity Element
        displayContacts();//show Contact
        setWidgetEventListener();//Set Widget
    }

    /**
     * Binds widgets from the view with different variables
     *
     */
    public void bindWidget() {
        NoContact_LB = (TextView) findViewById(R.id.NoContact); //textView Show when No Contact found

        mListView = (ListView) findViewById(R.id.android_list);//ListView
        mListView.setLongClickable(true);

        mSwipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_container);//SwipeRefreshLayout
        mSwipeContainer.setColorSchemeResources(R.color.darkblue);
        mSwipeContainer.setEnabled(false);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.search);//RelativeLayout SearchBar

        mSearchBar = (SearchView) findViewById(R.id.searchButton);//SearchView SearchBar
        mSearchBar.onActionViewCollapsed();

    }

    /**
     * Set event listener for widgets
     *
     */
    public void setWidgetEventListener() {
        if (accountModel.size() != 0) {
            NoContact_LB.setVisibility(View.INVISIBLE);
        }
        adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, android.R.id.text1, accountModel);
        // Assign adapter to ListView
        mListView.setAdapter(adapter);
        // ListView Item Click Listener

        //Long Click item on list
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("displayName ", accountModel.get(position).getDisplayName());
                Log.d("phoneNo ", accountModel.get(position).getPhoneNo());
                Log.d("email ", String.valueOf(accountModel.get(position).getEmail(getContentResolver())));
                Log.d("nickname ", accountModel.get(position).getNickName());
                Log.d("tags ", accountModel.get(position).getTags());
                Log.d("id ", accountModel.get(position).getId());

                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(accountModel.get(position).getId()));
                i.setData(uri);
                startActivity(i);
                return true;
            }
        });

        //When Click Item on list
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uri = "tel:" + accountModel.get(position).getPhoneNo();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                startActivity(callIntent);
            }
        });

        //make relative layout become searchbar
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Debug
                Log.d("search", "Work!!!");
//                searchBar.setOnQueryTextListener(this);

                //searchBar.onActionViewCollapsed();
                mSearchBar.onActionViewExpanded();
                //searchBar.setSubmitButtonEnabled(true);

            }
        });

        //Get String In searchBar to find Contact
        mSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("KEY", "0");
                ContactModel.applyFilter(adapter, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("KEY", newText);
                ContactModel.applyFilter(adapter, newText);
                return false;
            }
        });

        //When Refresh Page
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        ContactModel.loadAccounts(getContentResolver());

                        runOnUiThread(new Runnable() {
                            public void run() {
                                //update the grid here
                                ContactModel.applyFilter(adapter, "");
                                mSwipeContainer.setRefreshing(false);
                            }
                        });
                        Looper.myLooper().quit();
                    }
                }).start();


            }
        });

        //Check Scroll on Top to enable mSwipeContainer
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(mListView != null && mListView.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = mListView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mListView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeContainer.setEnabled(enable);
            }
        });
    }

    /**
     * Retrieve list of accounts
     *
     */
    private void displayContacts() {
        accountModel = new ArrayList<Account>(ContactModel.getAccounts(getContentResolver()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(Intent.ACTION_INSERT);
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                i.setData(uri);
                startActivity(i);
                break;

            case R.id.form2:
                try {
                    showDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Show "about" dialog
     *
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showDialog() throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setIcon(R.drawable.yinyang);
        builder.setTitle("About us");
        builder.setView(R.layout.about_fragment);

        builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
//            String ss = data.getStringExtra("result");
            booleanFromChild = data.getBooleanArrayExtra("result");
            Log.d("data from child: ", booleanFromChild[0]? "true":"false");

        }

    }

}
