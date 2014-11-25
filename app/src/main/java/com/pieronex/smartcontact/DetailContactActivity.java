//package com.pieronex.smartcontact;
//
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.RectF;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.SpannableString;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class DetailContactActivity extends Activity {
//
//    private String nameinfo, nicknameinfo, telinfo, tagsinfo, id, emailInfo;
//    TextView nameContact;
//
//    TextView phoneNo_mobile;
//    TextView tags;
//    TextView idTag;
//    TextView email;
//
//    RecyclerView recList;
//    Account account;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.contact_detail);
//        account = null;
//        bindWidget();
//        getActionBar().hide();
//
//
//        setInfoContact();
//
//       // readContacts(id);
//
//        setHeader();
////        setCard();
//
//        phoneNo_mobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Calling", phoneNo_mobile.getText().toString());
//                String uri = "tel:" + phoneNo_mobile.getText().toString();
//                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
//                startActivity(callIntent);
//
//            }
//        });
//
//
//    }
//
//    public void setHeader() {
//
//        Log.d("InfoContact:->  ", nameinfo + ", " + nicknameinfo + ", " + tagsinfo + ", " + account.getEmail(getContentResolver()));
//
////        nameContact.setText(nameinfo);
////        phoneNo_mobile.setText(telinfo);
////        idTag.setText("ID: "+id);
////        email.setText("Email: " + emailInfo);
//
//        nameContact.setText(account.getDisplayName());
//        phoneNo_mobile.setText(account.getPhoneNo());
//        idTag.setText("ID: " + account.getId());
//        email.setText("Email: " + account.getEmail(getContentResolver()));
//    }
//
//    ////  now try to show contact name info //////////
//    public void setInfoContact() {
//        Bundle data = getIntent().getExtras();
//        account = (Account) data.getParcelable("account");
//
//        this.nameinfo = account.getDisplayName();
//        this.telinfo = account.getPhoneNo();
//        this.id = account.getId();
//        if(account.getEmail(getContentResolver()).size()!=0)
//            emailInfo = account.getEmail(getContentResolver()).get(0);
//
//
//    }
//
//    public void bindWidget() {
//        //  nickname = (TextView)findViewById(R.id.nickname);
//        phoneNo_mobile = (TextView) findViewById(R.id.phoneNo);//
//        idTag = (TextView) findViewById(R.id.tags);//
//        nameContact = (TextView) findViewById(R.id.name);
//        email = (TextView) findViewById(R.id.e_mail);
//
//        // recList = (RecyclerView) findViewById(R.id.cardList);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detail_contact, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.AddButton) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
////    public String getInfoContact() {
////        return infoContact;
////    }
//
//
//    public void readContacts(String id) {
//        String mEmail = "", mId = "", mDisplayName = "", mPhoneNo = "", mLastTimeContacted = "", mNickName = "", mMiddleName = "";
//        String mGivenName = "", mFamilyName = "", mPrefix = "", mSuffix = "", mAddress = "", mNote = "", mWhereOrg = "", mInstantM = "";
//
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
////                mId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                mGivenName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                //  ur.getString(cur.getColumnIndex(ContactsContract.Contacts.));
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    //System.out.println("name : " + mGivenName + ", ID : " + mId);
//
//                    // get the phone number
//                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        mPhoneNo = pCur.getString(
//                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        //System.out.println("phone" + mPhoneNo);
//                    }
//                    pCur.close();
//
//
////                    Cursor emailCur = this.cr.query(
////                            Contacts.ContactMethods.CONTENT_EMAIL_URI,
////                            null,
////                            Contacts.ContactMethods.PERSON_ID + " = ?",
////                            new String[]{id}, null);
////                    while (emailCur.moveToNext()) {
////                        // This would allow you get several email addresses
////                        Email e = new Email(emailCur.getString(emailCur.getColumnIndex(Contacts.ContactMethods.DATA))
////                                ,emailCur.getString(emailCur.getColumnIndex(Contacts.ContactMethods.CONTENT_EMAIL_TYPE))
////                        );
////                        emails.add(e);
////                    }
////                    emailCur.close();
////
//
//                    // get email and type
//
//                    Cursor emailCur = cr.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//
//
//                    while (emailCur.moveToNext()) {
//                        // This would allow you get several email addresses
//                        // if the email addresses were stored in an array
//                        mEmail = emailCur.getString(
//                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                        String emailType = emailCur.getString(
//                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
//                        account.setEmail(mEmail);
//                        // System.out.println("Email " + mEmail + " Email Type : " + emailType);
////                    }
//                        emailCur.close();
//
//                        // Get note.......
////                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
////                    String[] noteWhereParams = new String[]{id,
////                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
////                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
////                    if (noteCur.moveToFirst()) {
////                        mNote = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
////                        //  System.out.println("Note " + mNote);
////                    }
////                    noteCur.close();
////
////                    //Get Postal Address....
////
////                    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
////                    String[] addrWhereParams = new String[]{id,
////                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
////                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
////                            null, null, null, null);
////                    while (addrCur.moveToNext()) {
////                        String poBox = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
////                        String street = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
////                        String city = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
////                        String state = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
////                        String postalCode = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
////                        String country = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
////                        String type = addrCur.getString(
////                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
////
////                        // Do something with these....
////                        mAddress = poBox + ", " + street + ", " + city + ", " + state + ", " + postalCode + ", " + country;
////
////                    }
////                    addrCur.close();
////
////                    // Get Instant Messenger.........
////                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
////                    String[] imWhereParams = new String[]{id,
////                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
////                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
////                            null, imWhere, imWhereParams, null);
////                    if (imCur.moveToFirst()) {
////                        String imName = imCur.getString(
////                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
////                        String imType;
////                        imType = imCur.getString(
////                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
////                        mInstantM = imName + ", " + imType;
////                    }
////                    imCur.close();
////
////                    // Get Organizations.........
////
////                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
////                    String[] orgWhereParams = new String[]{id,
////                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
////                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
////                            null, orgWhere, orgWhereParams, null);
////                    if (orgCur.moveToFirst()) {
////                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
////                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
////                        mWhereOrg = orgWhere + ", " + title;
////                    }
////
////                    orgCur.close();
////
////                    String familyName =ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
////                    String[] familyNameParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
////                    Cursor familyCur = cr.query(ContactsContract.Data.CONTENT_URI, null, familyName, familyNameParams, null);
////                    if(familyCur.moveToNext()){
////                        mFamilyName = familyCur.getString(familyCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
////                    }
//
//                        account.setDisplayName("given name");
//                        account.setAddress("address ");
//                        //account.setEmail(mEmail);
//                        account.setPhoneNo("phone no");
//
//                        Log.d("In reading mode: ", "waiting ");
//                        //  Toast.makeText(DetailContactActivity.this, "Succeed receiving", Toast.LENGTH_SHORT).show();
//                        // account.set
//                        //cr.query(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
//                        //contacts.add(mGivenName);
//                        // accountModel.add(new Account(mGivenName, mFamilyName, "middleName", "mNickName", mPhoneNo, mEmail, "Pic"));
//
//                    }
//
//                }
//            }
//        }
//    }
//}
////
////    public void setCard(){
////        recList.setHasFixedSize(true);
////        LinearLayoutManager llm = new LinearLayoutManager(this);
////        llm.setOrientation(LinearLayoutManager.VERTICAL);
////        recList.setLayoutManager(llm);
////
//////        ContactAdapter ca = new ContactAdapter(createList(30));
//////        recList.setAdapter(ca);
////    }
////
////    private List<ContactInfo> createList(int size) {
////        List<ContactInfo> result = new ArrayList<ContactInfo>();
////        for (int i=1; i <= size; i++) {
////            ContactInfo ci = new ContactInfo();
////            ci.name = ContactInfo.NAME_PREFIX + i;
////            ci.surname = ContactInfo.SURNAME_PREFIX + i;
////            ci.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";
////
////            result.add(ci);
////
////        }
////
////        return result;
////    }
////}
//
//
////
////class ContactInfo {
////    protected String name;
////    protected String surname;
////    protected String email;
////    protected static final String NAME_PREFIX = "Name_";
////    protected static final String SURNAME_PREFIX = "Surname_";
////    protected static final String EMAIL_PREFIX = "email_";
////}
////
////class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
////
////    private List<ContactInfo> contactList;
////
////    public ContactAdapter(List<ContactInfo> contactList) {
////        this.contactList = contactList;
////    }
////
////    @Override
////    public int getItemCount() {
////        return contactList.size();
////    }
////
////    @Override
////    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
////        ContactInfo ci = contactList.get(i);
////        contactViewHolder.vName.setText(ci.name);
////        contactViewHolder.vSurname.setText(ci.surname);
////        contactViewHolder.vEmail.setText(ci.email);
////        contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
////    }
////
////    @Override
////    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
////        View itemView = LayoutInflater.
////                from(viewGroup.getContext()).
////                inflate(R.layout.cardview, viewGroup, false);
////
////        return new ContactViewHolder(itemView);
////    }
////
////    class ContactViewHolder extends RecyclerView.ViewHolder {
////        protected TextView vName;
////        protected TextView vSurname;
////        protected TextView vEmail;
////        protected TextView vTitle;
////
////        public ContactViewHolder(View v) {
////            super(v);
////            vName =  (TextView) v.findViewById(R.id.txtName);
////            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
////            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
////            vTitle = (TextView) v.findViewById(R.id.title);
////        }
////    }
////}