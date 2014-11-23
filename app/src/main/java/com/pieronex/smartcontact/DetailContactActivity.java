package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DetailContactActivity extends Activity {

    private String nameinfo, nicknameinfo, telinfo,tagsinfo;
    TextView nameContact;
    TextView nickname;
    TextView phoneNo_mobile;
    TextView tags;
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_detail);
        bindWidget();
        getActionBar().hide();


        //String _firstName = inboundIndex.getStringExtra("name");
        //Toast.makeText(getApplicationContext(), _firstName, Toast.LENGTH_SHORT).show();
        setInfoContact();
        setheader();
//        setCard();


    }




    public void bindWidget(){
        nickname = (TextView)findViewById(R.id.nickname);
        phoneNo_mobile = (TextView)findViewById(R.id.phoneNo);
        tags = (TextView)findViewById(R.id.tags);
        nameContact = (TextView)findViewById(R.id.name);
       // recList = (RecyclerView) findViewById(R.id.cardList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_contact, menu);
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

//    public String getInfoContact() {
//        return infoContact;
//    }

    public void setInfoContact() {
        Bundle data = getIntent().getExtras();
        Account account = (Account)data.getParcelable("account");
        this.nameinfo = account.getFirstName();
        this.nicknameinfo = account.getLastName();
        this.telinfo = account.getPhoneNo();
        this.tagsinfo = account.getEmail();

    }

    public void setheader(){
        nameContact.setText(nameinfo);
        nickname.setText(nicknameinfo);
        phoneNo_mobile.setText(telinfo);
        tags.setText(tagsinfo);
    }

//    public void setCard(){
//        recList.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recList.setLayoutManager(llm);
//
////        ContactAdapter ca = new ContactAdapter(createList(30));
////        recList.setAdapter(ca);
//    }
//
//    private List<ContactInfo> createList(int size) {
//        List<ContactInfo> result = new ArrayList<ContactInfo>();
//        for (int i=1; i <= size; i++) {
//            ContactInfo ci = new ContactInfo();
//            ci.name = ContactInfo.NAME_PREFIX + i;
//            ci.surname = ContactInfo.SURNAME_PREFIX + i;
//            ci.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";
//
//            result.add(ci);
//
//        }
//
//        return result;
//    }
}


//
//class ContactInfo {
//    protected String name;
//    protected String surname;
//    protected String email;
//    protected static final String NAME_PREFIX = "Name_";
//    protected static final String SURNAME_PREFIX = "Surname_";
//    protected static final String EMAIL_PREFIX = "email_";
//}
//
//class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
//
//    private List<ContactInfo> contactList;
//
//    public ContactAdapter(List<ContactInfo> contactList) {
//        this.contactList = contactList;
//    }
//
//    @Override
//    public int getItemCount() {
//        return contactList.size();
//    }
//
//    @Override
//    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
//        ContactInfo ci = contactList.get(i);
//        contactViewHolder.vName.setText(ci.name);
//        contactViewHolder.vSurname.setText(ci.surname);
//        contactViewHolder.vEmail.setText(ci.email);
//        contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
//    }
//
//    @Override
//    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View itemView = LayoutInflater.
//                from(viewGroup.getContext()).
//                inflate(R.layout.cardview, viewGroup, false);
//
//        return new ContactViewHolder(itemView);
//    }
//
//    class ContactViewHolder extends RecyclerView.ViewHolder {
//        protected TextView vName;
//        protected TextView vSurname;
//        protected TextView vEmail;
//        protected TextView vTitle;
//
//        public ContactViewHolder(View v) {
//            super(v);
//            vName =  (TextView) v.findViewById(R.id.txtName);
//            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
//            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
//            vTitle = (TextView) v.findViewById(R.id.title);
//        }
//    }
//}