package mdad.networkdata.readerapp;

public class Manga {
    private int id;
    private String name;
    private String coverImageUrl;

    public Manga(int id, String name, String coverImageUrl) {
        this.id = id;
        this.name = name;
        this.coverImageUrl = coverImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }
}
