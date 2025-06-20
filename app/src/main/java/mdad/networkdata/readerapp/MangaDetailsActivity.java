package mdad.networkdata.readerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MangaDetailsActivity extends AppCompatActivity {

    private static final String URL_GET_MANGA_IMAGES = MainActivity.ipAddress + "/fetch_manga_images.php";
    private List<String> imageUrls = new ArrayList<>();
    private ImagePagerAdapter imagePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manga_details);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        imagePagerAdapter = new ImagePagerAdapter(imageUrls);
        viewPager.setAdapter(imagePagerAdapter);

        int mangaId = getIntent().getIntExtra("mangaId", -1);
        if (mangaId != -1) {
            loadMangaImages(mangaId);
        }
    }

    private void loadMangaImages(int mangaId) {
        String url = URL_GET_MANGA_IMAGES + "?mangaId=" + mangaId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                imageUrls.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject image = response.getJSONObject(i);
                                    String imageUrl = image.getString("image_url");
                                    imageUrls.add(imageUrl);
                                }
                                imagePagerAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("MangaDetailsActivity", "No images found for manga ID: " + mangaId);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MangaDetailsActivity", "Error loading manga images", error);
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
