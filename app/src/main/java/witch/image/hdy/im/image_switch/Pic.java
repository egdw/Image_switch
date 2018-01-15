package witch.image.hdy.im.image_switch;

/**
 * Created by hdy on 15/01/2018.
 */

public class Pic {
    private String name;
    private String url;

    public Pic(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
