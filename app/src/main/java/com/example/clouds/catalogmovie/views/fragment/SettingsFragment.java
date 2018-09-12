package com.example.clouds.catalogmovie.views.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.api.ApiBuilder;
import com.example.clouds.catalogmovie.api.ApiRepository;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.models.MovieResponse;
import com.example.clouds.catalogmovie.service.MovieDailyNotif;
import com.example.clouds.catalogmovie.service.MovieNowPlayingNotif;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsPrefernceFragment()).commit();
    }

    public static class SettingsPrefernceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        private SwitchPreference switchReminder;
        private SwitchPreference switchToday;
        private MovieDailyNotif movieDailyNotif = new MovieDailyNotif();
        private MovieNowPlayingNotif movieNowPlayingNotif = new MovieNowPlayingNotif();

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_fragment);


            switchReminder = (SwitchPreference) findPreference(getString(R.string.today_remainder));
            switchReminder.setOnPreferenceChangeListener(this);
            switchToday = (SwitchPreference) findPreference(getString(R.string.release_notif));
            switchToday.setOnPreferenceChangeListener(this);

            Preference myPref = findPreference(getString(R.string.language_change));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean configurationValues = (boolean) newValue;

            if(key.equals(getString(R.string.today_remainder))){
                if(configurationValues){
                    movieDailyNotif.setDailyAlarm(getActivity());
                }else{
                    movieDailyNotif.cancelDailyAlarm(getActivity());
                }
            } else {
                if(configurationValues){
                    setNewReleaseAlarm();
                }else{
                    movieNowPlayingNotif.cancelUpcomingAlarm(getActivity());
                }
            }

            return true;
        }

        private void setNewReleaseAlarm() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String dateNow = dateFormat.format(date);

            ApiRepository api= ApiBuilder.getRetrofitIntance().create(ApiRepository.class);
            Call<MovieResponse> call = api.getOnPlayMovie();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    ArrayList<Movie> newMoviesList = new ArrayList<>();
                    ArrayList<Movie> movies = response.body().movies;

                    for (Movie movie: movies) {
                        if (movie.getReleaseDate().equals(dateNow)) {
                            newMoviesList.add(movie);
                        }
                    }
                    movieNowPlayingNotif.setUpcomingAlarm(getActivity(), newMoviesList);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e("Movie Request", "Error: "+ t.getMessage());
                }
            });
        }

    }
}
