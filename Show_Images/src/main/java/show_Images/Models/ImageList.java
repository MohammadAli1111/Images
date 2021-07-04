package show_Images.Models;

import java.util.List;

public class ImageList {
    List<Image> images;

    public ImageList() {
    }

    public ImageList(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
