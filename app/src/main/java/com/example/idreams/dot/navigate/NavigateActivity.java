package com.example.idreams.dot.navigate;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.nearby.NearbyActivity;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class NavigateActivity extends BaseActivity implements RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected GoogleMap map;
    protected GoogleApiClient mGoogleApiClient;
    private String LOG_TAG = "NavigateActivity";
    private Polyline polyline;
    private LatLng[] destinations;

    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                        map.moveCamera(center);
                        map.animateCamera(zoom);
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


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                        map.moveCamera(center);
                        map.animateCamera(zoom);
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

        sendRequest();
    }

    private void sendRequest() {
        if (Util.Operations.isNetworkAvailable(this)) {
            destinations = new LatLng[NearbyActivity.mSelectedLocations.size()];
            int i = 0;
            for (String key : NearbyActivity.mSelectedLocations.keySet()) {
                destinations[i] = NearbyActivity.mSelectedLocations.get(key);
                i++;
            }

            Routing routing = new Routing(Routing.TravelMode.WALKING);
            routing.registerListener(this);
            routing.execute(destinations);
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
        CameraUpdate center = CameraUpdateFactory.newLatLng(destinations[0]);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        map.moveCamera(center);

        if (polyline != null)
            polyline.remove();

        polyline = null;
        //adds route to the map.
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(getResources().getColor(R.color.primary_dark));
        polyOptions.width(10);
        polyOptions.addAll(mPolyOptions.getPoints());
        polyline = map.addPolyline(polyOptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(destinations[0]);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        map.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(destinations[0]);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
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
        Toast.makeText(getApplicationContext(), "next site", Toast.LENGTH_LONG);
    }

    public void shareInfo(View view) {

    }
}