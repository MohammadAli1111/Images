package add_Update_Images.Models;

import java.util.List;

public class ImageTypeList {
    List<ImageType> imageTypes;

    public ImageTypeList() {
    }

    public ImageTypeList(List<ImageType> imageTypes) {
        this.imageTypes = imageTypes;
    }

    public List<ImageType> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<ImageType> imageTypes) {
        this.imageTypes = imageTypes;
    }
}
