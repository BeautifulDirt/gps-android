package com.example.gps;

import java.util.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends Activity {
	 
	  TextView tvEnabledGPS;
	  //TextView tvStatusGPS;
	  TextView tvLocationGPS;
	  TextView tvEnabledNet;
	  //TextView tvStatusNet;
	  TextView tvLocationNet;
	  TextView tvTitleGPS;
	  TextView tvTitleNet;
	  Button btn;
	 
	  private LocationManager locationManager;
	  StringBuilder sbGPS = new StringBuilder();
	  StringBuilder sbNet = new StringBuilder();
	 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_main);
	    Typeface type = Typeface.createFromAsset(getAssets(),"fonts/bankgothic.ttf"); 
	    
	    tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
	    //tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
	    tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
	    tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
	    //tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
	    tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);
	    tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);
	    tvTitleGPS = (TextView) findViewById(R.id.tvTitleGPS);
	    tvTitleNet = (TextView) findViewById(R.id.tvTitleNet);
	    btn = (Button) findViewById(R.id.btnLocationSettings);
	    
	    tvEnabledGPS.setTypeface(type);
	    //tvStatusGPS.setTypeface(type);
	    tvLocationGPS.setTypeface(type);
	    tvEnabledNet.setTypeface(type);
	    //tvStatusNet.setTypeface(type);
	    tvLocationNet.setTypeface(type);
	    tvTitleGPS.setTypeface(type);
	    tvTitleNet.setTypeface(type);
	    btn.setTypeface(type);
	 
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	  }
	 
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	        1000 * 10, 10, locationListener);
	    locationManager.requestLocationUpdates(
	        LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
	        locationListener);
	    checkEnabled();
	  }
	 
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(locationListener);
	  }
	 
	  private LocationListener locationListener = new LocationListener() {
	 
	    @Override
	    public void onLocationChanged(Location location) {
	      showLocation(location);
	    }
	 
	    @Override
	    public void onProviderDisabled(String provider) {
	      checkEnabled();
	    }
	 
	    @Override
	    public void onProviderEnabled(String provider) {
	      checkEnabled();
	      showLocation(locationManager.getLastKnownLocation(provider));
	    }
	 
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	      if (provider.equals(LocationManager.GPS_PROVIDER)) {
	        //tvStatusGPS.setText("Статус: " + String.valueOf(status));
	      } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
	        //tvStatusNet.setText("Статус: " + String.valueOf(status));
	      }
	    }
	  };
	 
	  private void showLocation(Location location) {
	    if (location == null)
	      return;
	    if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
	      tvLocationGPS.setText(formatLocation(location));
	    } else if (location.getProvider().equals(
	        LocationManager.NETWORK_PROVIDER)) {
	      tvLocationNet.setText(formatLocation(location));
	    }
	  }
	 
	  private String formatLocation(Location location) {
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    if (location == null)
	      return "";
	    return String.format(
	        "Координаты: широта = %1$.4f, долгота = %2$.4f, время = %3$s",
	        location.getLatitude(), location.getLongitude(), sdf.format(new Date(
	            location.getTime())));
	  }
	 
	  private void checkEnabled() {
	    tvEnabledGPS.setText("Доступ: "
	        + locationManager
	            .isProviderEnabled(LocationManager.GPS_PROVIDER));
	    tvEnabledNet.setText("Доступ: "
	        + locationManager
	            .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	  }
	 
	  public void onClickLocationSettings(View view) {
	    startActivity(new Intent(
	        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	  };
	 
	}
