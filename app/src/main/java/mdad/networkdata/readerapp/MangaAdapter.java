package mdad.networkdata.readerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaAdapter extends BaseAdapter {

    private Context context;
    private List<Manga> mangaList;

    public MangaAdapter(Context context, List<Manga> mangaList) {
        this.context = context;
        this.mangaList = mangaList;
    }

    @Override
    public int getCount() {
        return mangaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mangaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mangaList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_manga, parent, false);
        }

        Manga manga = mangaList.get(position);

        ImageView imageView = convertView.findViewById(R.id.coverImageView);
        TextView textView = convertView.findViewById(R.id.titleTextView);

        Picasso.get().load(manga.getCoverImageUrl()).into(imageView);
        textView.setText(manga.getName());

        return convertView;
    }
}
