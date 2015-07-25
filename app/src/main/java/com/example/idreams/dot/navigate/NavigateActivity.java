package com.example.idreams.dot.navigate;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.SettingsActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class NavigateActivity extends BaseActivity implements
        RoutingListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    protected GoogleMap map;
    protected GoogleApiClient mGoogleApiClient;
    private LatLng currentLagLng;
    private String LOG_TAG = "NavigateActivity";
    private Polyline polyline;
    private LatLng[] destinations;
    private String[] destinationsName;
    private LatLng[] currentDestinations = new LatLng[2];
    private int start = 0;
    private int end = 1;

    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        MapsInitializer.initialize(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        map = mapFragment.getMap();

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(24, 121));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        map.moveCamera(center);
        map.animateCamera(zoom);

        updateCurrentLocation();
        sendRequest();
    }

    // First, get destinations info from nearby activity,
    // and then call routing method.
    private void sendRequest() {
        if (Util.Operations.isNetworkAvailable(this)) {
            destinations = new LatLng[NearbyActivity.sSelectedLocations.size()];
            destinationsName = new String[NearbyActivity.sSelectedLocationsName.size()];
            int i = 0;
            for (String key : NearbyActivity.sSelectedLocations.keySet()) {
                destinations[i] = NearbyActivity.sSelectedLocations.get(key);
                destinationsName[i] = NearbyActivity.sSelectedLocationsName.get(i);
                i++;
            }

            routeToNext();
        } else {
            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingFailure() {
        // The Routing request failed
        Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(currentDestinations[0]);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        map.moveCamera(center);
        map.animateCamera(zoom);

        if (polyline != null)
            polyline.remove();

        polyline = null;
        // adds route to the map.
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(getResources().getColor(R.color.blue));
        polyOptions.width(10);
        polyOptions.addAll(mPolyOptions.getPoints());
        polyline = map.addPolyline(polyOptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(currentDestinations[0]);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        options.title(destinationsName[start]);
        options.snippet("第" + (start+1) + "站");
        Marker start = map.addMarker(options);
        start.showInfoWindow();

        // End marker
        options = new MarkerOptions();
        options.position(currentDestinations[1]);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        options.title(destinationsName[end]);
        options.snippet("第" + (end+1) + "站");
        Marker end = map.addMarker(options);
        // end.showInfoWindow();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(LOG_TAG, connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void nextSite(View view) {
        Toast.makeText(getApplicationContext(), "next site", Toast.LENGTH_LONG).show();
        if (start == end - 1 && end < destinations.length - 1) {
            start++;
            end++;
        } else {
            start = 0;
            end = 1;
        }
        routeToNext();
    }

    private void routeToNext() {
        map.clear();
        Routing routing = new Routing(Routing.TravelMode.DRIVING);
        routing.registerListener(this);
        currentDestinations[0] = destinations[start];
        currentDestinations[1] = destinations[end];
        routing.execute(currentDestinations);
    }

    public void currentPlace(View view) {
        updateCurrentLocation();

//  TODO has to resolve the problem of async and drawing marker.
//                        map.clear();
//                        map.moveCamera(center);
//                        map.animateCamera(zoom);
//                        map.addMarker(new MarkerOptions()
//                                .position(currentLagLng)
//                                .title("目前位置"));
    }

    private void updateCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Use network to get current location
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        currentLagLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate center = CameraUpdateFactory.newLatLng(currentLagLng);
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

        // Use GPS to get current location
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        currentLagLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate center = CameraUpdateFactory.newLatLng(currentLagLng);
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }

    private void shareCurrentLocation() {
        CallbackManager callbackManager;
        final ShareDialog shareDialog;
        String app_id = getResources().getString(R.string.app_id);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("我正要前往" + destinationsName[start])
                    .setContentDescription(
                            "我還有" + (destinations.length - end) + "站還需努力～")
                    .setContentUrl(Uri.parse("https://developers.facebook.com/apps/" + app_id + "/dashboard/"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigate_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_item_share) {
            shareCurrentLocation();
        }

        return super.onOptionsItemSelected(item);
    }
}