package com.ideasfrombrain.search_based_launcher_v2;


import java.util.ArrayList;

import java.util.List;


import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
//import android.hardware.Camera;
//import android.hardware.Camera.Parameters;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;


import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
//import android.widget.Button;

//import android.widget.ToggleButton;

import android.widget.ViewAnimator;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;



public class MainActivity extends Activity implements OnItemClickListener, TextWatcher, OnItemLongClickListener, OnClickListener {
	
	
    
    
    static String app_package_name="com.ideasfrombrain.search_based_launcher_v2";
	
    //EditText myEditText;
    //ListView myListView;
    int tmpArg;
    boolean tmpYes;
    boolean isFlashOn=false;
    int checkedRadioButton=0;
   // boolean lastWifiState=false;
  //  boolean lastBlueState=false;
    boolean Autostart=true;
    boolean NewerAndroid=true;
    boolean HasCam = false;
    //PackageManager pm;
    
    public class pkgInfo {
    	public String[] Nick=  new String[300]; 
        public String[] Name =  new String[300];
        public String[] Activity = new String[300] ;
        public int last;
    }
    public class pkgListInfo {
    	public ArrayList<String> Nick = new ArrayList<String>(200);
    	public ArrayList<String> Name = new ArrayList<String>(200);
    	public ArrayList<String> Activity = new ArrayList<String>(200);
    }    
    
    pkgListInfo pkg = new pkgListInfo();
    pkgListInfo pkgFiltered = new pkgListInfo();
    pkgListInfo pkgExtra = new pkgListInfo();
    pkgListInfo pkgHide = new pkgListInfo();
    
    pkgListInfo pkgRecent = new pkgListInfo();
    
    EditText DialogInput;
     
   // ArrayAdapter<String> adapter ;
    //ArrayAdapter<String> FilteredAdapter;
    
    private final BroadcastReceiver mApplicationsReceiver = new BroadcastReceiver() {
		@Override
        public void onReceive(Context context, Intent intent) {
			Log.d("DEBUG", "recieved Broadcast");
			final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			
			//if("android.intent.action.AIRPLANE_MODE".equals(intent.getAction()))  {
				 //final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				 
				 
				
				//if ((wifi!=null)  && (lastWifiState)) { /*wifi.setWifiEnabled(true);*/ startActivity(new Intent(wifi.ACTION_REQUEST_ENABLE)); }
				//if ((mBluetoothAdapter!=null)  && (lastBlueState)) {startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)); /*mBluetoothAdapter.enable();*/ }
				//lastBlueState=false;
				//lastWifiState =false;
			//}
			if(wifi!=null) {
				final TextView myWifiButton = (TextView) findViewById(R.id.button2);
				if(wifi.isWifiEnabled()) {
					myWifiButton.setBackgroundColor(-16711936);
				}
				else {
					myWifiButton.setBackgroundColor(-12303292);
				}
				//loadApps();
					
			}
        }
    };

    private final BroadcastReceiver mPkgApplicationsReceiver = new BroadcastReceiver() {
		@Override
        public void onReceive(Context context, Intent intent) {
				checkedRadioButton=1;	
				loadApps();
					boolean found = false;
					//ArrayList<String> pkgRecentFound = new ArrayList<String>(200);
					EscapeToBeSure: for(int i=0; i<pkgRecent.Name.size(); i++  ) 
        	    	{				
	    	        	for(int j=0;j<pkg.Name.size(); j++) {
	    	        		if(pkg.Activity.get(j).equals(pkgRecent.Activity.get(i))){
	    	        				found = true;
	    	        				//pkgRecentFound.add()
	    	        		}
	    	        	}
	    	        	if(!found) {
	    	        		pkgRecent.Activity.remove(i);
	    	        		pkgRecent.Name.remove(i);
	    	        		pkgRecent.Nick.remove(i);
	    	        		i--;
	    	        	}
	    	        	else {
	    	        		found=false;
	    	        	}
	    	        	if(i>=pkgRecent.Name.size()) {break EscapeToBeSure;}
        	    	}
					EscapeToBeSure2: for(int i=0; i<pkgHide.Name.size(); i++  ) 
        	    	{				
	    	        	for(int j=0;j<pkg.Name.size(); j++) {
	    	        		if(pkg.Activity.get(j).equals(pkgHide.Activity.get(i))){
	    	        				found = true;
	    	        				//pkgHideFound.add()
	    	        		}
	    	        	}
	    	        	if(!found) {
	    	        		pkgHide.Activity.remove(i);
	    	        		pkgHide.Name.remove(i);
	    	        		pkgHide.Nick.remove(i);
	    	        		i--;
	    	        	}
	    	        	else {
	    	        		found=false;
	    	        	}
	    	        	if(i>=pkgHide.Name.size()) {break EscapeToBeSure2;}
        	    	}
					EscapeToBeSure3: for(int i=0; i<pkgExtra.Name.size(); i++  ) 
        	    	{				
	    	        	for(int j=0;j<pkg.Name.size(); j++) {
	    	        		if(pkg.Activity.get(j).equals(pkgExtra.Activity.get(i))){
	    	        				found = true;
	    	        				//pkgExtraFound.add()
	    	        		}
	    	        	}
	    	        	if(!found) {
	    	        		pkgExtra.Activity.remove(i);
	    	        		pkgExtra.Name.remove(i);
	    	        		pkgExtra.Nick.remove(i);
	    	        		i--;
	    	        	}
	    	        	else {
	    	        		found=false;
	    	        	}
	    	        	if(i>=pkgExtra.Name.size()) {break EscapeToBeSure3;}
        	    	}
        	    
        	    SaveExtRemLists(false);
				checkedRadioButton=0;	
				loadApps();
				afterTextChanged(null);
		}
    };
    
    private void registerIntentReceivers() {
        IntentFilter pkgFilter = new IntentFilter( );
        pkgFilter.addAction( Intent.ACTION_PACKAGE_ADDED );
        pkgFilter.addAction( Intent.ACTION_PACKAGE_REMOVED );
        pkgFilter.addDataScheme("package");
        registerReceiver(mPkgApplicationsReceiver, pkgFilter);
        
        IntentFilter filter = new IntentFilter( );
        filter.addAction( Intent.ACTION_AIRPLANE_MODE_CHANGED );
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mApplicationsReceiver, filter);
        
    }
    
    public void loadApps() {
    	Log.d("DEBUG", "start loading apps");

		//ActivityIsssssssnfo acInfo;  
    		
		Log.d("DEBUG", "activity arrays prepared");
        


        
      //  try {
        	
           final Intent main=new Intent(Intent.ACTION_MAIN, null);
           final PackageManager pm = getPackageManager();
           
           int i=0;
           int j=0;
           String tmpNick="";
          
            if(pkg.Name.size()!=0 ) {pkg.Name.clear(); pkg.Activity.clear(); pkg.Nick.clear();}
        
        	
           switch(checkedRadioButton) {
           case 0:
        	        // LAUCHABLES VARIANT OF LOAD APPS
        	        
   	        
		   	        for(i=0; i<pkgExtra.Name.size(); i++  ) 
		   	    	{
		   	        	pkg.Name.add(pkgExtra.Name.get(i));
		   	        	pkg.Nick.add(pkgExtra.Nick.get(i));
		   	        	pkg.Activity.add(pkgExtra.Activity.get(i));
		   	    	}
        	            
        	        main.addCategory(Intent.CATEGORY_LAUNCHER); // will show only Regular Apps
        	        final List<ResolveInfo> launchables=pm.queryIntentActivities(main, 0);
        	        
        	        for(ResolveInfo launchable : launchables) 
        	    	{
        	        	//acInfo=launchable.activityInfo;         	        	
        	        	if (ItemNumInHide(launchable.activityInfo.name)==-1) 
        	        	{
            	        	pkg.Name.add(launchable.activityInfo.packageName);
            	        	pkg.Activity.add(launchable.activityInfo.name);
            	        	pkg.Nick.add(launchable.activityInfo.loadLabel(pm).toString());//tmpSplit[length-2]+ ":" +tmpSplit[length-1];
            	        	//Log.d("DEBUG", pkg.Nick[i] );
        	        	}


        	    	}
        	        

        	        //pkg.last=pkg.last + pkgExtra.Name.size();
        	break;
            case 1:
        		
        			final List<ResolveInfo> launchables2=pm.queryIntentActivities(main, 0);
        	        
        	        
        	        //pkg.last=launchables2.size()-1;
        	        //for(int i=0; i<=pkg.last; i++)
        			int length=0;
        	        for(ResolveInfo launchable : launchables2) 
        	    	{
        	        	String[] tmpSplit = new String[10];
        	        	
        	        	
        	        	//acInfo=launchable.activityInfo; 
        	        	
        	            tmpSplit=launchable.activityInfo.name.split("\\.");
 					    length=tmpSplit.length;

        	        	pkg.Name.add(launchable.activityInfo.packageName);
        	        	pkg.Activity.add(launchable.activityInfo.name);
        	        	tmpNick="";
        	        	tmpNick=tmpSplit[1];
        	        	for(j=2; j<length; j++) {
        	        		tmpNick=tmpNick + ":" + tmpSplit[j];
        	        	}
        	        	
        	        	pkg.Nick.add(tmpNick);
        	        	//pkg.Nick.add(tmpSplit[length-2]+ ":" +tmpSplit[length-1]);
        	    	}


            break;
            case 2:
        		
    			
        		//final List<ResolveInfo> launchables3=pm.queryIntentActivities(main, 0);
    	        
    	        
    	       // pkg.last=pkgExtra.Name.size()-1;
    	        for(i=0; i<pkgExtra.Name.size(); i++  ) 
    	    	{
    	        	
    	            //tmpSplit=acInfo.name.split("\\.");
					//    length=tmpSplit.length;

    	        	pkg.Name.add(pkgExtra.Name.get(i));
    	        	pkg.Nick.add(pkgExtra.Nick.get(i));
    	        	pkg.Activity.add(pkgExtra.Activity.get(i));
    	    	}

           break;
            case 3:
        		
    	        
    	        
    	        //pkg.last=pkgHide.Name.size()-1;
    	        for(i=0; i<pkgHide.Name.size(); i++  ) 
    	    	{
    	        	
    	            //tmpSplit=acInfo.name.split("\\.");
					//    length=tmpSplit.length;

    	        	pkg.Name.add(pkgHide.Name.get(i));
    	        	pkg.Nick.add(pkgHide.Nick.get(i));
    	        	pkg.Activity.add(pkgHide.Activity.get(i));
    	    	}

           break;
           }
           pkg.Name.add(app_package_name+".Menu" );
       	   pkg.Nick.add(" Menu-Launcher");
       	   pkg.Activity.add(app_package_name+".Menu");
    //     }	
     //    catch (Exception e) {
   	//			Log.d("DEBUG",e.getStackTrace().toString());
   	//	 }

     //REFILTER   
     //afterTextChanged(myEditText.getText()) ;

     }
        
        
    public void LoadExtRemLists(boolean check) {
    	try{
        	final Context myContext =  getApplicationContext();
        	LoadList(pkgExtra.Name,"pkgExtra.Name", myContext);
        	LoadList(pkgExtra.Nick,"pkgExtra.Nick", myContext);
        	LoadList(pkgExtra.Activity,"pkgExtra.Activity", myContext);
        	LoadList(pkgHide.Name,"pkgHide.Name", myContext);
        	LoadList(pkgHide.Nick,"pkgHide.Nick", myContext);
        	LoadList(pkgHide.Activity,"pkgHide.Activity", myContext);	
        	LoadList(pkgRecent.Name,"pkgRecent.Name", myContext);
        	LoadList(pkgRecent.Nick,"pkgRecent.Nick", myContext);
        	LoadList(pkgRecent.Activity,"pkgRecent.Activity", myContext);
    	}
    	catch(Exception e) {
    		//Log.d("DEBUG","excep. during load" + e.getStackTrace().toString());
    		SaveExtRemLists(false);
    	}
    }
    
    public void SaveExtRemLists(boolean check) {
    	final Context myContext =  getApplicationContext();
    	SaveList(pkgExtra.Name,"pkgExtra.Name", myContext);
    	SaveList(pkgExtra.Nick,"pkgExtra.Nick", myContext);
    	SaveList(pkgExtra.Activity,"pkgExtra.Activity", myContext);
    	SaveList(pkgHide.Name,"pkgHide.Name", myContext);
    	SaveList(pkgHide.Nick,"pkgHide.Nick", myContext);
    	SaveList(pkgHide.Activity,"pkgHide.Activity", myContext);
    	SaveList(pkgRecent.Name,"pkgRecent.Name", myContext);
    	SaveList(pkgRecent.Nick,"pkgRecent.Nick", myContext);
    	SaveList(pkgRecent.Activity,"pkgRecent.Activity", myContext);
    }

    public boolean SaveList(List<String> list, String listName, Context mContext) { 
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);  
        SharedPreferences.Editor editor = prefs.edit();  
        editor.putInt(listName +"_size", list.size());  
        for(int i=0;i<list.size();i++)  
            editor.putString(listName + "_" + i, list.get(i));  
        return editor.commit();  
    } 

    

    
    public void LoadList(ArrayList<String> List, String ListName, Context mContext) {  
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);  
        int size = prefs.getInt(ListName + "_size", 0);  
        //List = new ArrayList<String>(20);
        List.clear();
        for(int i=0;i<size;i++)  
            List.add(prefs.getString(ListName + "_" + i, null));  
    }  
    
    @Override
    public boolean onKeyUp(int keycode, KeyEvent event ) {
     if(keycode == KeyEvent.KEYCODE_MENU){
    	 
    	 //startActivityForResult((new Intent(this, Menu.class).putExtra(  "checkedRadioButton", checkedRadioButton)), 1);
    	 myShowNext(false);
     }
     else if(keycode == KeyEvent.KEYCODE_SEARCH ) {
    	 //onSearchRequested();
    	 startSearch("",false,null,true);
    	 /*
    	 try {
    		 
    	 	final Intent intent = new Intent(Intent.ACTION_MAIN, null);
     		intent.addCategory(Intent.CATEGORY_LAUNCHER);
     		intent.setComponent(new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity"));
     	
     	
     	
     		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  |
            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
     		startActivity( intent);
    	 }
    	 catch (Exception e ){}*/
     }
     else if(keycode == KeyEvent.KEYCODE_BACK ) {
    	  ViewAnimator mViewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
    	 	if(mViewAnimator.getDisplayedChild()==1) {
    	 		myShowNext(false);
    	 	}
     }
     else {
    	 return super.onKeyUp(keycode,event);  
     }
         return true;
    }
   

    
 
    
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        savedInstanceState
        	//setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        	
//            final PackageManager pm = getPackageManager();
            
            setContentView(R.layout.activity_main);
            
            
           //myViewAnimator = (ViewAnimator)findViewById(R.id.container);
           // myViewAnimator.setInAnimation(null);  // Skipping this will cause trouble
           // myViewAnimator.setOutAnimation(null);
            
           
            final ListView myListView = (ListView) findViewById(R.id.listView1);
            myListView.setOnItemClickListener(this);
            myListView.setOnItemLongClickListener(this);

        	final EditText myEditText = (EditText) findViewById(R.id.editText1);
           
        	myEditText.addTextChangedListener(this);
        	
        	final TextView myToggleButton =(TextView) findViewById(R.id.toggleButton0) ;
        	myToggleButton.setOnClickListener(this);
        	Autostart=false; onClick(null);
        	
        	
        	

        	final PackageManager pm = getPackageManager();
        	HasCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);

        	
        	//-------------------------------- WIFI ------------------------------------------------
        	final TextView myWifiButton = (TextView) findViewById(R.id.button2);
        	final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        	//if ((wifi!=null) &&  (wifi.isWifiEnabled())) {myWifiButton.setChecked(true); }
        	if (wifi == null) {
        		myWifiButton.setVisibility(4);
        		
            } 
        	else {
				if(wifi.isWifiEnabled()) {
					myWifiButton.setBackgroundColor(-16711936);
				}
				else {
					myWifiButton.setBackgroundColor(-12303292);
				}
        	}
        	
            myWifiButton.setOnClickListener(new View.OnClickListener()
            {

				public void onClick(View arg0) {
	                final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	                if(wifi!=null) { wifi.setWifiEnabled(!wifi.isWifiEnabled());}
					
				}

            });
        	
          //-------------------------------- BLUE ------------------------------------------------
            
            final TextView myBlueButton = (TextView) findViewById(R.id.button4);        	
            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
           if (mBluetoothAdapter == null) {
                myBlueButton.setVisibility(4);
            } 
            

            myBlueButton.setOnClickListener(new View.OnClickListener()
            {
            	
				public void onClick(View arg0) {
	              

					final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
					
					if ((mBluetoothAdapter!=null)  && (!mBluetoothAdapter.isEnabled())) {
						
						mBluetoothAdapter.enable();
		            }
					else if (mBluetoothAdapter!=null)   {
						mBluetoothAdapter.disable();

					} 
				}

            });
            
            
        	//-------------------------------- FLASH -------------------------------------------
        	
        	final TextView myFlashButton = (TextView) findViewById(R.id.button1);

        	myFlashButton.setVisibility(4); // FIX AND COMMENT THIS+repair return!!!!!!!
        	if (!(HasCam)) {
        		myFlashButton.setVisibility(4);
        	}
            myFlashButton.setOnClickListener(new View.OnClickListener()
            {

				public void onClick(View arg0) {
					   //Camera mCamera;	
					/*   String value;	
					   if (isFlashOn) // we are being ask to turn it on
					    {
					        value = Camera.Parameters.FLASH_MODE_TORCH;
					    }
					    else  // we are being asked to turn it off
					    {
					        value =  Camera.Parameters.FLASH_MODE_AUTO;
					    }

					
					    try{    
					    
					    	Camera cam = Camera.open();     
					    	Parameters p = cam.getParameters();
					    	p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					    	cam.setParameters(p);
					    	cam.startPreview();

					    	String nowMode=p.getFlashMode();
					    	
					        if (isFlashOn && nowMode.equals(Camera.Parameters.FLASH_MODE_TORCH))
					        {
					        	isFlashOn=true;
					        }
					        if (! isFlashOn && nowMode.equals(Camera.Parameters.FLASH_MODE_AUTO))
					        {
					        	isFlashOn=true;
					        }
					        isFlashOn= false;
					    }
					    catch (Exception ex)
					    {
					        // nothing to do
					    	Log.d("DEBUG", "error");
					    }
	                	 */
	                
					
				}

            });            
            
            
        	//-------------------------------- Contacts -------------------------------------------
        	
        	/*
        	final Button myContButton = (Button) findViewById(R.id.button5);
        	
        	myContButton.setOnClickListener(new View.OnClickListener()
            {

				public void onClick(View arg0) {
						
					startActivity( (new Intent(Intent.ACTION_VIEW ,  Uri.parse("content://contacts/people/"))));	
					
				}

            });*/                
       
        	
             
        	//-------------------------------- Camera -------------------------------------------
        	

        	
        	final TextView myCamButton = (TextView) findViewById(R.id.button6);
        
        	if (!(HasCam)) {
        		myCamButton.setVisibility(4);
        	}
        	
        	myCamButton.setOnClickListener(new View.OnClickListener()
            {

				public void onClick(View arg0) {
						
					startActivity( (new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)));	
					
				}

            });                
                   
            
            
        	
            //---------------MENU CODE
            
            
            
   	   	 findViewById(R.id.donateButton).setOnClickListener(new View.OnClickListener() {
   				public void onClick(View v) {
   					Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ app_package_name));
   					marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
   					startActivity(marketIntent);
   				}
   			});
   	    	 
   	       	findViewById(R.id.donateButton).setOnClickListener(new View.OnClickListener() {
   	 			public void onClick(View v) {
   	 				Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ app_package_name + "_donate"));
   	 				marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
   	 				startActivity(marketIntent);
   	 			}
   	 		});
   	       	
   	       	
   	       	((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
   				
   				public void onCheckedChanged(RadioGroup group, int checkedId) {

   					switch (checkedId) {
   						case R.id.radio0 : checkedRadioButton=0; break;
   						case R.id.radio1 : checkedRadioButton=1; break;
   						case R.id.radio2 : checkedRadioButton=2; break;
   						case R.id.radio3 : checkedRadioButton=3; break;
   					}
   					//Log.d("DEBUG", "cid " + checkedRadioButton);
   					
   					//MainActivity.this.setContentView(R.layout.activity_main);
   					myShowNext(true);
   				}
   				
   	       	});   

   	        
   	    

   	       	//android version check
   	       	String Aversion = android.os.Build.VERSION.RELEASE;
        	if(savedInstanceState==null) {
        		if (Aversion.startsWith("1.") ||
        				Aversion.startsWith("2.0") ||
        				Aversion.startsWith("2.1"))
        			 { NewerAndroid = false;}
        		else {
        				NewerAndroid = true;
        		}
        		
        		LoadExtRemLists(false);
        		loadApps();
        	}
        	else {
                checkedRadioButton = savedInstanceState.getInt( "checkedRadioButton");
                NewerAndroid = savedInstanceState.getBoolean( "NewerAndroid");
                
                pkg.Name=savedInstanceState.getStringArrayList("pkg.Name");
                pkg.Nick=savedInstanceState.getStringArrayList("pkg.Nick");
                pkg.Activity=savedInstanceState.getStringArrayList("pkg.Activity");
                
                pkgExtra.Name=savedInstanceState.getStringArrayList("pkgExtra.Name");
                pkgExtra.Nick=savedInstanceState.getStringArrayList("pkgExtra.Nick");
                pkgExtra.Activity=savedInstanceState.getStringArrayList("pkgExtra.Activity");
                
                pkgHide.Name=savedInstanceState.getStringArrayList("pkgHide.Name");
                pkgHide.Nick=savedInstanceState.getStringArrayList("pkgHide.Nick");
                pkgHide.Activity=savedInstanceState.getStringArrayList("pkgHide.Activity");
                
                pkgRecent.Name=savedInstanceState.getStringArrayList("pkgRecent.Name");
                pkgRecent.Nick=savedInstanceState.getStringArrayList("pkgRecent.Nick");
                pkgRecent.Activity=savedInstanceState.getStringArrayList("pkgRecent.Activity");
                
        		 //REFILTER   
        	     //afterTextChanged(myEditText.getText()) ;
        	}
        	registerIntentReceivers();        
        
        
    

    }

    
    public void myShowNext(Boolean DoLoadApps) {
    	final EditText myEditText=(EditText) findViewById(R.id.editText1);
    	myEditText.setBackgroundColor(-12303292); // because of menu in applist
       	ViewAnimator mViewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
    	//mViewAnimator.setInAnimation(null);  // Skipping this will cause trouble
       // mViewAnimator.setOutAnimation(null);
   	 	mViewAnimator.showNext();
    	
   	 	if(mViewAnimator.getDisplayedChild()==0) {
   	 		//final TextView myToggleButton = (TextView) findViewById(R.id.toggleButton0);
   	 		if (DoLoadApps) {loadApps();}
   	 		
   	 		if(checkedRadioButton > 0) { 
   	 			myEditText.append(" ") ; 
   	 			Autostart=true; onClick(null);
   	 			TextView b;
   	 			b = (TextView) findViewById(R.id.button1);
   	 			b.setVisibility(4);
   	 			b = (TextView) findViewById(R.id.button2);
	 			b.setVisibility(4);
	 			b = (TextView) findViewById(R.id.button4);
   	 			b.setVisibility(4);
   	 			//b = (TextView) findViewById(R.id.button5);
	 			//b.setVisibility(4);
	 			b = (TextView) findViewById(R.id.button6);
   	 			b.setVisibility(4);
   	 			
   	 		}
   	 		else {
   	 			
   	 			final TextView myWifiButton = (TextView) findViewById(R.id.button2);
   	 			final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
   	 			if (wifi != null) {
   	 				myWifiButton.setVisibility(0);
   	 			}
   	        	/*final TextView myFlashButton = (TextView) findViewById(R.id.button1);

   	        	if (HasCam) {
   	        		// myMobButton.setVisibility(0);
   	        	}*/
   	        	
   	        	
   	        	
   	        	
   	        	
   	            final TextView myBlueButton = (TextView) findViewById(R.id.button4);        	
   	            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
   	            if (mBluetoothAdapter != null) {
   	                myBlueButton.setVisibility(0);
   	            }
   	            
   	        	final TextView myCamButton = (TextView) findViewById(R.id.button6);
   	         
   	        	if (HasCam) {
   	        		myCamButton.setVisibility(0);
   	        	}
   	 			myEditText.setText(""); 
   	 		Autostart=false; onClick(null);
   	 		}
   	 		
   	 		//myEditText.requestFocus();
   	 	}
   	 	else {
   	 		RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
   	 		mRadioGroup.requestFocus();
   	 	}
    	
 
    	
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    // Save UI state changes to the savedInstanceState.
    // This bundle will be passed to onCreate if the process is
    // killed and restarted.

        savedInstanceState.putInt( "checkedRadioButton", checkedRadioButton);
        savedInstanceState.putBoolean( "NewerAndroid", NewerAndroid);
        
        savedInstanceState.putStringArrayList("pkg.Name", pkg.Name);
        savedInstanceState.putStringArrayList("pkg.Nick", pkg.Nick);
        savedInstanceState.putStringArrayList("pkg.Activity", pkg.Activity);

        savedInstanceState.putStringArrayList("pkgExtra.Name", pkgExtra.Name);
        savedInstanceState.putStringArrayList("pkgExtra.Nick", pkgExtra.Nick);
        savedInstanceState.putStringArrayList("pkgExtra.Activity", pkgExtra.Activity);
        
        savedInstanceState.putStringArrayList("pkgHide.Name", pkgHide.Name);
        savedInstanceState.putStringArrayList("pkgHide.Nick", pkgHide.Nick);
        savedInstanceState.putStringArrayList("pkgHide.Activity", pkgHide.Activity);
        
        savedInstanceState.putStringArrayList("pkgRecent.Name", pkgRecent.Name);
        savedInstanceState.putStringArrayList("pkgRecent.Nick", pkgRecent.Nick);
        savedInstanceState.putStringArrayList("pkgRecent.Activity", pkgRecent.Activity);
        
        super.onSaveInstanceState(savedInstanceState);
    }

    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

     }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        final EditText myEditText=(EditText) findViewById(R.id.editText1);
        myEditText.setBackgroundColor(-12303292);
    	//final TextView myToggleButton = (TextView) findViewById(R.id.toggleButton0);
		if((checkedRadioButton == 0)  && Autostart) {
			myEditText.setText("");
		 }
		//myEditText.requestFocus();
		
		
		if(!NewerAndroid) {
			final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null){
			   imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	    	}
		}
	
			
    }
    
    
    @Override
    public void onDestroy() {
        unregisterReceiver(mApplicationsReceiver);
        unregisterReceiver(mPkgApplicationsReceiver);
    	super.onDestroy();	
    }
 
    
	//@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final EditText myEditText=(EditText) findViewById(R.id.editText1);
		myEditText.setBackgroundColor(-16711936);

		int tmpint = ItemNumInRecent(pkgFiltered.Activity.get(arg2));
		if((pkgRecent.Nick.size() >=10) && (tmpint==-1)){
			pkgRecent.Name.remove(0);
    		pkgRecent.Nick.remove(0);
    		pkgRecent.Activity.remove(0);   		
		}
		else if  (tmpint!=-1) {
			pkgRecent.Name.remove(tmpint);
    		pkgRecent.Nick.remove(tmpint);
    		pkgRecent.Activity.remove(tmpint);
		}
		final Context myContext =  getApplicationContext();
    	SaveList(pkgRecent.Name,"pkgRecent.Name", myContext);
    	SaveList(pkgRecent.Nick,"pkgRecent.Nick", myContext);
    	SaveList(pkgRecent.Activity,"pkgRecent.Activity", myContext);
		
		String tmpNickBefore = pkgFiltered.Nick.get(arg2);
		pkgRecent.Name.add(pkgFiltered.Name.get(arg2));
			if((tmpNickBefore.matches("R:.*"))  ) {tmpNickBefore=tmpNickBefore.substring(2,tmpNickBefore.length());}
		pkgRecent.Nick.add( tmpNickBefore);
		pkgRecent.Activity.add(pkgFiltered.Activity.get(arg2)); 
		
		if(!NewerAndroid) {
			final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null){
			   imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	    	}
		}
		
		
		if((app_package_name+".Menu").equals(pkgFiltered.Name.get(arg2))) {
			myShowNext(false);
		}
		else {
		 try{
	
	        	final Intent intent = new Intent(Intent.ACTION_MAIN, null);
	        	intent.addCategory(Intent.CATEGORY_LAUNCHER);
	        	/*if("".equals(myEditText.getText())) {
	        		//final ComponentName cn = ;
	        		pkgRecent.Name.add(pkg.Name.get(arg2));
	        		pkgRecent.Nick.add("R:" + pkg.Nick.get(arg2));
	        		pkgRecent.Activity.add(pkg.Activity.get(arg2));
	        		intent.setComponent(new ComponentName(pkg.Name.get(arg2) , pkg.Activity.get(arg2)));
	        		
	        	}
	        	else {*/
	        		//final ComponentName cn = ));

	        		
	        		intent.setComponent(new ComponentName(pkgFiltered.Name.get(arg2) , pkgFiltered.Activity.get(arg2)));
	        	//}
	        	
	        	
	        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  |
	                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	        	startActivity( intent);
	        	



		 }	
		 catch(Exception e) {
			// TODO Auto-generated catch block
			Log.d("DEBUG", e.getMessage());
			myEditText.setBackgroundColor(-12303292);
			
			if(!NewerAndroid) {
				final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null){
				   imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		    	}
			}
		  }
		}
		
	}

	




	//@Override
	public void afterTextChanged(Editable arg0) {
		pkgFiltered.Name.clear();
		pkgFiltered.Nick.clear();
		pkgFiltered.Activity.clear();
		final ListView myListView = (ListView) findViewById(R.id.listView1);
		//final ToggleButton myToggleButton = (ToggleButton) findViewById(R.id.toggleButton0);
		
		
		//if( ("".equals(myEditText.getText())) || (" ".equals(myEditText.getText())) ) {

		//	myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, pkg.Nick));
		
		//}
		//else {
			
			final EditText myEditText=(EditText) findViewById(R.id.editText1);
			
	
			
			String FilterText= myEditText.getText().toString().toLowerCase().replace(" ", ".*") + ".*"; // space is replaced with REGEX  symbols ".*"
			

			int tmpsize = pkgRecent.Name.size();
			for(int i=0; i<tmpsize; i++)
			{
				//Log.d("DEBUG", pkgRecent.Name.get(tmpsize-1-i));
		           if (pkgRecent.Nick.get(tmpsize-1-i).toLowerCase().matches(FilterText) )  
				   {
		        	   pkgFiltered.Name.add(pkgRecent.Name.get(tmpsize-1-i));
		        	   pkgFiltered.Activity.add(pkgRecent.Activity.get(tmpsize-1-i));
		        	   pkgFiltered.Nick.add("R:" + pkgRecent.Nick.get(tmpsize-1-i));
				   }
			}
			
			for(int i=0; i<pkg.Name.size(); i++)
			{
			   
	           if ((pkg.Nick.get(i).toLowerCase().matches(FilterText) ) && (ItemNumInRecent(pkg.Activity.get(i))==-1))  
			   {
					  pkgFiltered.Name.add(pkg.Name.get(i));
					  pkgFiltered.Activity.add(pkg.Activity.get(i));
					  pkgFiltered.Nick.add(pkg.Nick.get(i));
				  //Log.d("DEBUG", pkg.Nick[i]);
			   }
	           
		   }
		   if(((pkgFiltered.Name.size())==1) && Autostart) {
				this.onItemClick(myListView, myListView, 0, 0) ;
				//myEditText.setText("");
			 }
			 else {
				 //FilteredAdapter = 
		    	 myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, pkgFiltered.Nick));
			}
			
		//}
		

	}



	//@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}


   /* private class ApplicationsIntentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d("DEBUG", "activity arrays prepared1");
			MainActivity.this.loadApps();
		}
    }*/


	//@Override
	//public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//		MainActivity.this.loadApps();
//	}


	//@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		final int tmpArg = arg2;
		final String tmpActivity = pkgFiltered.Activity.get(tmpArg);
		final String tmpName= pkgFiltered.Name.get(tmpArg);
		String tmpNickBefore= pkgFiltered.Nick.get(tmpArg);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(pkgFiltered.Nick.get(tmpArg));

		if((tmpActivity==app_package_name+".Menu")  ) {return false;}
		if((tmpNickBefore.matches("R:.*"))  ) {tmpNickBefore=tmpNickBefore.substring(2,tmpNickBefore.length());}
		final String tmpNick=tmpNickBefore;
			
		
        switch(checkedRadioButton) {
        case 0:
        	int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);

			if((tmpItemNumInExtra==-1) && (MainActivity.this.ItemNumInHide(tmpActivity)==-1 ) ) {
				DialogInput =   new EditText(this);
				DialogInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
				DialogInput.setText(tmpNick);
				
				dialog.setView(DialogInput);
				dialog.setMessage("Hide this application from applications list, rename application (add to Hide and Extra list with diferent names) or uninstall it?");
				dialog.setCancelable(true);
				dialog.setPositiveButton("Hide", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  //int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);
				        	  if(MainActivity.this.ItemNumInHide(tmpActivity)==-1 )  {
				        		  pkgHide.Name.add(tmpName);
				        		  pkgHide.Nick.add(tmpNick);
				        		  pkgHide.Activity.add(tmpActivity);
				        		  
				        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
				        		  if(tmpItemNumInRecent!=-1) {
				        			  pkgRecent.Nick.remove(tmpItemNumInRecent);
				        			  pkgRecent.Name.remove(tmpItemNumInRecent);
				        			  pkgRecent.Activity.remove(tmpItemNumInRecent);
				        		  }
				        		  
				        		  SaveExtRemLists(false);
				        		  loadApps();
				        		  afterTextChanged(null);
				        		  dialog.dismiss();
				        	  }
				           }
				       });
				
				dialog.setNeutralButton("Uninstall", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + tmpName));
						startActivity(intent);
						//loadApps();
						afterTextChanged(null);
						dialog.dismiss();
					}
				});
				dialog.setNegativeButton("Rename", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
			        	  if(MainActivity.this.ItemNumInHide(tmpActivity)==-1 )  {
			        		  pkgHide.Name.add(tmpName);
			        		  pkgHide.Nick.add(tmpNick);
			        		  pkgHide.Activity.add(tmpActivity);
			        		  
			        		  pkgExtra.Nick.add(DialogInput.getText().toString());
			        		  pkgExtra.Name.add(tmpName);
			        		  pkgExtra.Activity.add(tmpActivity);
			        		  
			        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
			        		  if(tmpItemNumInRecent!=-1) {
			        			  pkgRecent.Nick.set(tmpItemNumInRecent,DialogInput.getText().toString());
			        		  }
			        		  
			        		  SaveExtRemLists(false);
			        		  loadApps();
			        		  afterTextChanged(null);
			        		  dialog.dismiss();
			        	  }
					}
				});
			}
			else if((tmpItemNumInExtra!=-1) && (MainActivity.this.ItemNumInHide(tmpActivity)!=-1 ) ) {
				DialogInput =   new EditText(this);
				DialogInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
				DialogInput.setText(tmpNick);
				
				dialog.setView(DialogInput);
				dialog.setMessage("This application is in both add and hide lists, thus is probably renamed.");
				dialog.setCancelable(true);
				dialog.setPositiveButton("Hide", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);
				        	  if(MainActivity.this.ItemNumInHide(tmpActivity)==-1 )  {
				        		  //pkgHide.Name.add(tmpName);
				        		  //pkgHide.Nick.add(tmpNick);
				        		  //pkgHide.Activity.add(tmpActivity);
				        		  pkgExtra.Nick.remove(tmpItemNumInExtra);
				        		  pkgExtra.Name.remove(tmpItemNumInExtra);
				        		  pkgExtra.Activity.remove(tmpItemNumInExtra);
				        		  
				        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
				        		  if(tmpItemNumInRecent!=-1) {
				        			  pkgRecent.Nick.remove(tmpItemNumInRecent);
				        			  pkgRecent.Name.remove(tmpItemNumInRecent);
				        			  pkgRecent.Activity.remove(tmpItemNumInRecent);
				        		  }
				        		  
				        		  SaveExtRemLists(false);
				        		  loadApps();
				        		  afterTextChanged(null);
				        		  dialog.dismiss();
				        	  }
				           }
				       });
				
				dialog.setNeutralButton("Uninstall", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + tmpName));
						startActivity(intent);
						//loadApps();
						afterTextChanged(null);
						dialog.dismiss();
					}
				});
				dialog.setNegativeButton("Rename", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
							  int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);
			        		  //pkgHide.Name.add(tmpName);
			        		  //pkgHide.Nick.add(tmpNick);
			        		  //pkgHide.Activity.add(tmpActivity);
			        		  
			        		  pkgExtra.Nick.remove(tmpItemNumInExtra);
			        		  pkgExtra.Name.remove(tmpItemNumInExtra);
			        		  pkgExtra.Activity.remove(tmpItemNumInExtra);
			        		  
			        		  pkgExtra.Nick.add(DialogInput.getText().toString());
			        		  pkgExtra.Name.add(tmpName);
			        		  pkgExtra.Activity.add(tmpActivity);
			        		  
			        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
			        		  if(tmpItemNumInRecent!=-1) {
			        			  pkgRecent.Nick.set(tmpItemNumInRecent,DialogInput.getText().toString());
			        		  }
			        		  
			        		  SaveExtRemLists(false);
			        		  loadApps();
			        		  afterTextChanged(null);
			        		  dialog.dismiss();
			        	  
					}
				});
			}
			else if(tmpItemNumInExtra!=-1) {
				
				dialog.setMessage("Remove activity " + tmpActivity +  " from extra added (to applications list) list ?");
				dialog.setCancelable(true);
				dialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);
				        		  pkgExtra.Activity.remove(tmpItemNumInExtra);
				        		  pkgExtra.Name.remove(tmpItemNumInExtra);
				        		  pkgExtra.Nick.remove(tmpItemNumInExtra);
				        		  
				        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
				        		  if(tmpItemNumInRecent!=-1) {
				        			  pkgRecent.Nick.remove(tmpItemNumInRecent);
				        			  pkgRecent.Name.remove(tmpItemNumInRecent);
				        			  pkgRecent.Activity.remove(tmpItemNumInRecent);
				        		  }
				        		  
				        		  SaveExtRemLists(false) ;
				        		  loadApps();
				        		  afterTextChanged(null);
				        		  dialog.dismiss();
				        		  
				           }
				       });
			}

			dialog.create().show();
        				
		break;
        case 1:

        	
        	
        	
			DialogInput =   new EditText(this);
			DialogInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			DialogInput.setText(pkgFiltered.Nick.get(tmpArg));
			
			dialog.setView(DialogInput);
			dialog.setMessage("Add this activity to applications list?");
			dialog.setCancelable(true);
			dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
				        	  pkgExtra.Name.add(pkgFiltered.Name.get(tmpArg));
				   			  pkgExtra.Nick.add(DialogInput.getText().toString() );
				   			  pkgExtra.Activity.add(pkgFiltered.Activity.get(tmpArg));
				   			  SaveExtRemLists(true);
				   			  loadApps();
				   			  afterTextChanged(null);
				   			  dialog.dismiss();
			           }
			       });

			dialog.create().show();        	
        	
		break;
        case 2:	
			DialogInput =   new EditText(this);
			DialogInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			DialogInput.setText(pkgFiltered.Nick.get(tmpArg));
			
			dialog.setView(DialogInput);
			dialog.setMessage("Remove this (extra added list of all activities) activity from applications list?");
			dialog.setCancelable(true);
			dialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
				        	  int tmpItemNumInExtra=MainActivity.this.ItemNumInExtra(tmpActivity);
				        	  if(tmpItemNumInExtra!=-1) {
				        		  pkgExtra.Activity.remove(tmpItemNumInExtra);
				        		  pkgExtra.Name.remove(tmpItemNumInExtra);
				        		  pkgExtra.Nick.remove(tmpItemNumInExtra);
				        		  
				        		  int tmpItemNumInRecent = ItemNumInRecent(tmpActivity);
				        		  if(tmpItemNumInRecent!=-1) {
				        			  pkgRecent.Nick.remove(tmpItemNumInRecent);
				        			  pkgRecent.Name.remove(tmpItemNumInRecent);
				        			  pkgRecent.Activity.remove(tmpItemNumInRecent);
				        		  }
				        		  
				        		  MainActivity.this.SaveExtRemLists(false) ;
				        		  loadApps();
				        		  afterTextChanged(null);
				        		  dialog.dismiss();
				        	  }
			           }
			       });

			dialog.create().show();            	
        	
        break;	
        case 3:    	
			DialogInput =   new EditText(this);
			DialogInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			DialogInput.setText(pkgFiltered.Nick.get(tmpArg));
			
			dialog.setView(DialogInput);
			dialog.setMessage("Remove this activity (hidden app) from hidden applications list?");
			dialog.setCancelable(true);
			dialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
				        	  int tmpItemNumInHide=MainActivity.this.ItemNumInHide(tmpActivity);
				        	  if(tmpItemNumInHide!=-1) {
				        		  pkgHide.Activity.remove(tmpItemNumInHide);
				        		  pkgHide.Name.remove(tmpItemNumInHide);
				        		  pkgHide.Nick.remove(tmpItemNumInHide);
				        		  MainActivity.this.SaveExtRemLists(false) ;
				        		  loadApps();
				        		  afterTextChanged(null);
				        		  dialog.dismiss();
				        	  }
			           }
			       });

			dialog.create().show();   
			break;
        }	   
		     
			
		return false;
	}

	public int ItemNumInExtra(String Activity) { // NOTE: if not found returns -1
		for(int i=0; i<pkgExtra.Activity.size(); i++) {
			if(Activity.equals(pkgExtra.Activity.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int ItemNumInHide(String Activity) { // NOTE: if not found returns -1
		for(int i=0; i<pkgHide.Activity.size(); i++) {
			if(Activity.equals(pkgHide.Activity.get(i))) {	
				return i;
			}
		}
		return -1;
	}	
	public int ItemNumInRecent(String Activity) { // NOTE: if not found returns -1
		for(int i=0; i<pkgRecent.Activity.size(); i++) {
			if(Activity.equals(pkgRecent.Activity.get(i))) {	
				return i;
			}
		}
		return -1;
	}	
	
	
	//@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View arg0) {
		Autostart=!Autostart;
		final TextView myToggleButton = (TextView) findViewById(R.id.toggleButton0);
		if(Autostart) {
			myToggleButton.setBackgroundColor(-16711936);
		}
		else {
			myToggleButton.setBackgroundColor(-12303292);
		}
		
	}


}