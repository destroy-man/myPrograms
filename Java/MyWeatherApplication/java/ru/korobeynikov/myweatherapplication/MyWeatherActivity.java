package ru.korobeynikov.myweatherapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.korobeynikov.myweatherapplication.databinding.ActivityMyWeatherBinding;
import ru.korobeynikov.myweatherapplication.json.Root;
import ru.korobeynikov.myweatherapplication.json.WebApi;

public class MyWeatherActivity extends AppCompatActivity {

    private ActivityMyWeatherBinding binding;
    private Retrofit retrofit;
    private DBHelper dbHelper;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_weather);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/").build();
        dbHelper = new DBHelper(this);
        city = getCity();
        getWeather(city);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_city)
            showInputDialog();
        return false;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_city);
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Обновить", (dialogInterface, i) -> {
            city = input.getText().toString();
            changeCity();
            getWeather(city);
        });
        builder.show();
    }

    private void getWeather(String city) {
        WebApi webApi = retrofit.create(WebApi.class);
        Observable<Root> observable = webApi.root(city, getString(R.string.open_weather_maps_app_id));
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull Root root) {
                String city = root.getName() + ", " + root.getSys().getCountry();
                String dateUpdate = DateFormat.getDateTimeInstance().format(new Date(root.getDt() * 1000));
                String temp =
                        String.format(Locale.getDefault(), "%.2f", root.getMain().getTemp()) + " ℃";
                String details = root.getWeather().get(0).getDescription() +
                        "\nHumidity: " + root.getMain().getHumidity() + "%" +
                        "\nPressure: " + root.getMain().getPressure() + " hPa";
                binding.cityField.setText(city);
                binding.updatedField.setText(dateUpdate);
                binding.temperatureField.setText(temp);
                binding.detailsField.setText(details);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getApplicationContext(), getText(R.string.place_not_found),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private String getCity() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("city", null, null, null, null,
                null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                city = c.getString(nameColIndex);
            } while (c.moveToNext());
        } else {
            city = "Moscow";
            ContentValues cv = new ContentValues();
            cv.put("name", city);
            db.insert("city", null, cv);
        }
        c.close();
        return city;
    }

    private void changeCity() {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", city);
        db.update("city", cv, "id = ?", new String[]{"1"});
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}