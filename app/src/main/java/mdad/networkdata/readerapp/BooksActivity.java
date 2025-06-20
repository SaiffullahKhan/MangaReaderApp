package mdad.networkdata.readerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksActivity extends AppCompatActivity {

    private static final String URL_GET_MANGAS = MainActivity.ipAddress + "/fetch_books.php";
    private static final String URL_DELETE_BOOK = MainActivity.ipAddress + "/delete_book.php";
    private static final String URL_UPDATE_BOOK = MainActivity.ipAddress + "/update_book.php";
    private GridView mangaGrid;
    private EditText searchBar;
    private MangaAdapter mangaAdapter;
    private List<Manga> mangaList = new ArrayList<>();
    private Button deleteButton;
    private Button updateButton;
    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books);


        searchBar = findViewById(R.id.searchBar);
        mangaGrid = findViewById(R.id.mangaGrid);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        logoutButton = findViewById(R.id.logoutButton);


        mangaAdapter = new MangaAdapter(this, mangaList);
        mangaGrid.setAdapter(mangaAdapter);

        loadMangas("");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        // this is for on click goes to the other page on select manga_id
        mangaGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Manga selectedManga = mangaList.get(position);
                Intent intent = new Intent(BooksActivity.this, MangaDetailsActivity.class);
                intent.putExtra("mangaId", selectedManga.getId());
                startActivity(intent);
            }
        });




        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BooksActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    // this loads up my image and book name from mysql
    private void loadMangas(String query) {
        String url = URL_GET_MANGAS + "?search=" + query;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mangaList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject manga = response.getJSONObject(i);
                                int id = manga.getInt("id");
                                String name = manga.getString("name");
                                String coverImageUrl = manga.getString("cover_image_url");
                                mangaList.add(new Manga(id, name, coverImageUrl));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mangaAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BooksActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }





    // the dialog for when u click the delte button takes info from db
    private void showDeleteDialog() {
        final List<String> bookNames = new ArrayList<>();
        final List<Integer> bookIds = new ArrayList<>();

        // Fetch book names and IDs
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_MANGAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                bookNames.add(name);
                                bookIds.add(id);
                            }

                            CharSequence[] bookArray = bookNames.toArray(new CharSequence[0]);
                            new AlertDialog.Builder(BooksActivity.this)
                                    .setTitle("Select a book to delete")
                                    .setItems(bookArray, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int selectedBookId = bookIds.get(which);
                                            deleteBook(selectedBookId);
                                        }
                                    })
                                    .show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BooksActivity.this, "Error fetching books", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    // code for deletion of a book ( manga_id )
    private void deleteBook(final int bookId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BooksActivity.this, response, Toast.LENGTH_SHORT).show();
                        loadMangas("");  // Reload the list after deletion
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BooksActivity.this, "Error deleting book", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("manga_id", String.valueOf(bookId));  // Updated parameter name
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





    // dialog when clicked on update button takes info from db
    private void showUpdateDialog() {
        final List<String> bookNames = new ArrayList<>();
        final List<Integer> bookIds = new ArrayList<>();

        // Fetch book names and IDs
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_MANGAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                bookNames.add(name);
                                bookIds.add(id);
                            }

                            CharSequence[] bookArray = bookNames.toArray(new CharSequence[0]);
                            new AlertDialog.Builder(BooksActivity.this)
                                    .setTitle("Select a book to update")
                                    .setItems(bookArray, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final int selectedBookId = bookIds.get(which);
                                            showUpdateNameDialog(selectedBookId);
                                        }
                                    })
                                    .show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BooksActivity.this, "Error fetching books", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void showUpdateNameDialog(final int mangaId) {
        final EditText input = new EditText(this);
        input.setHint("Enter new book name");

        new AlertDialog.Builder(this)
                .setTitle("Update Book Name")
                .setView(input)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();
                        updateBookName(mangaId, newName);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



    private void updateBookName(final int mangaId, final String newName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BooksActivity.this, response, Toast.LENGTH_SHORT).show();
                        loadMangas("");  // Reload the list after updating
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BooksActivity.this, "Error updating book", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("manga_id", String.valueOf(mangaId));  // Use manga_id instead of book_id
                params.put("new_name", newName);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
