package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klaudia.medicalcenter.RetrofitModel.PlaceDetails;
import com.example.klaudia.medicalcenter.Remote.IGoogleApiService;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDetailsActivity extends AppCompatActivity {

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.place_open_hours)
    TextView opening_hours;
    @BindView(R.id.place_address)
    TextView place_adress;
    @BindView(R.id.place_name)
    TextView place_name;
    @BindView(R.id.map_show)
    Button viewOnMap;
    @BindView(R.id.telephon_number)
    Button telefonNumber;
    @BindView(R.id.tooltipBtn)
    FloatingActionButton tooltipBtn;

    IGoogleApiService mService;
    PlaceDetails myPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        ButterKnife.bind(this);

        mService = Common.getGoogleAPIService();

        place_name.setText("");
        opening_hours.setText("");
        place_adress.setText("");
        telefonNumber.setText("");

        tooltipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltips();
            }
        });

        viewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });

        if (Common.currentResults.getPhotos() != null && Common.currentResults.getPhotos().length > 0) {
            Picasso.with(this)
                    .load(getPhotoOfPlace(Common.currentResults.getPhotos()[0].getPhoto_reference(), 1000))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(photo);
        }

        if (Common.currentResults.getRating() != null && !TextUtils.isEmpty(Common.currentResults.getRating())) {
            ratingBar.setRating(Float.parseFloat(Common.currentResults.getRating()));
        } else {
            ratingBar.setVisibility(View.GONE);
        }

        if (Common.currentResults.getOpening_hours() != null) {
            opening_hours.setText(this.getString(R.string.status)+" ");

            if (Common.currentResults.getOpening_hours().getOpen_now() == "true") {
                opening_hours.append(this.getString(R.string.status_open));
            } else {
                opening_hours.append(this.getString(R.string.status_closed));
            }
        } else {
            opening_hours.setVisibility(View.GONE);
        }

        mService.getDetailsPlace(getPlaceUrl(Common.currentResults.getPlace_id()))
                .enqueue(new Callback<PlaceDetails>() {
                    @Override
                    public void onResponse(Call<PlaceDetails> call, Response<PlaceDetails> response) {
                        myPlace = response.body();
                        place_adress.setText(myPlace.getResult().getFormattedAddress());
                        place_name.setText(myPlace.getResult().getName());
                        telefonNumber.setText(myPlace.getResult().getFormattedPhoneNumber());
                    }

                    @Override
                    public void onFailure(Call<PlaceDetails> call, Throwable t) {

                    }
                });

        telefonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + myPlace.getResult().getFormattedPhoneNumber().trim()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(ViewDetailsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Activity not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tooltips() {
        ViewTooltip
                .on(this, telefonNumber)
                .autoHide(true, 2000)
                .clickToHide(false)
                .align(ViewTooltip.ALIGN.CENTER)
                .position(ViewTooltip.Position.TOP)
                .text("Wybierz, aby zadzwonić")
                .textColor(Color.WHITE)
                .color(getResources().getColor(R.color.colorAccent))
                .corner(40)
                .arrowWidth(15)
                .arrowHeight(25)
                .distanceWithView(0)
                .show();
    }

    private String getPlaceUrl(String place_id) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid=" + place_id);
        url.append("&key=" + getResources().getString(R.string.browser_key));
        return url.toString();
    }

    private String getPhotoOfPlace(String photo_reference, int maxWidth) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth=" + maxWidth);
        url.append("&photoreference=" + photo_reference);
        url.append("&key=" + getResources().getString(R.string.browser_key));
        return url.toString();
    }
}
