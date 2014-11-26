package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class MainActivity extends Activity{

    List<String> contacts = new ArrayList<String>();
    List<Account> accountModel = new ArrayList<Account>();

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




    public void bindWidget() {
        NoContact_LB = (TextView) findViewById(R.id.NoContact); //textView Show when No Contact found
        NoContact_LB.setVisibility(View.INVISIBLE);

        mListView = (ListView) findViewById(R.id.android_list);//ListView
        mListView.setLongClickable(true);

        mSwipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_container);//SwipeRefreshLayout
        mSwipeContainer.setColorSchemeResources(R.color.darkblue);
        mSwipeContainer.setEnabled(false);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.search);//RelativeLayout SearchBar

        mSearchBar = (SearchView) findViewById(R.id.searchButton);//SearchView SearchBar
        mSearchBar.onActionViewCollapsed();
    }

    public void setWidgetEventListener() {
        if (accountModel.size() == 0) {
            NoContact_LB.setVisibility(View.VISIBLE);
        }
        adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, android.R.id.text1, accountModel);
        // Assign adapter to ListView
        mListView.setAdapter(adapter);
        // ListView Item Click Listener

        //Long Click item on list
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            /*view information of the contact at the postion of  "position"*/
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("displayname : ", accountModel.get(position).getDisplayName());
                Log.d("phoneNo : ", accountModel.get(position).getPhoneNo());
                Log.d("email : ", String.valueOf(accountModel.get(position).getEmail(getContentResolver())));
                Log.d("tags : ", accountModel.get(position).getTags());
                Log.d("id : ", accountModel.get(position).getId());
                Log.d("nickname : ", accountModel.get(position).getNickName());

                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,String.valueOf(accountModel.get(position).getId()));
                i.setData(uri);
                startActivity(i);
                return true;
            }
        });

        //When Click Item on list
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*perform calling action when short click is on the listView at that position*/
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
                //searchBar.setOnQueryTextListener(this);

                //searchBar.onActionViewCollapsed();
                mSearchBar.onActionViewExpanded();
                //searchBar.setSubmitButtonEnabled(true);

            }
        });

        //Get String In searchBar to find Contact and filter it to get specific one
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
    /*create accounts by calling ContactModel and return List of Account to accountModel*/
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
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(Intent.ACTION_INSERT);
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                i.setData(uri);
                startActivity(i);
                break;
            case(R.id.action_settings) :
                Intent intent = new Intent(getApplicationContext(), FilterSearch.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {  //  <- waiting fill in request code
//            if (data.hasExtra("myData1")) {
//                Toast.makeText(this, data.getExtras().getString("myData1"),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void readContacts() {
        String mEmail = "", mId = "", mDisplayName = "", mPhoneNo = "", mLastTimeContacted = "", mNickName = "", mMiddleName = "";
        String mGivenName = "", mFamilyName = "", mPrefix = "", mSuffix = "", mAddress = "", mNote = "", mWhereOrg = "", mInstantM = "";

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                mId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                mGivenName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //  ur.getString(cur.getColumnIndex(ContactsContract.Contacts.));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //System.out.println("name : " + mGivenName + ", ID : " + mId);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{mId}, null);
                    while (pCur.moveToNext()) {
                        mPhoneNo = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //System.out.println("phone" + mPhoneNo);
                    }
                    pCur.close();


                    // get email and type

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{mId}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        mEmail = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        // System.out.println("Email " + mEmail + " Email Type : " + emailType);
                    }
                    emailCur.close();

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{mId,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        mNote = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        //  System.out.println("Note " + mNote);
                    }
                    noteCur.close();

                    //Get Postal Address....

                    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{mId,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);
                    while (addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....
                        mAddress = poBox + ", " + street + ", " + city + ", " + state + ", " + postalCode + ", " + country;

                    }
                    addrCur.close();

                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{mId,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                        mInstantM = imName + ", " + imType;
                    }
                    imCur.close();

                    // Get Organizations.........

                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{mId,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                        mWhereOrg = orgWhere + ", " + title;
                    }

                    orgCur.close();

                    String familyName =ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] familyNameParams = new String[]{mId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                    Cursor familyCur = cr.query(ContactsContract.Data.CONTENT_URI, null, familyName, familyNameParams, null);
                    if(familyCur.moveToNext()){
                        mFamilyName = familyCur.getString(familyCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    }
                    //cr.query(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    contacts.add(mGivenName);
//                    accountModel.add(new Account(mGivenName, mFamilyName, "middleName", "mNickName", mPhoneNo, mEmail, "Pic"));

                }

            }
        }
    }

}
