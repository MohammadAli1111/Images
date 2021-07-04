package add_Update_Images.Models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Lob
    @Column(name = "data_image",length = Integer.MAX_VALUE, nullable = true)
    private byte[] data_image;

    private  String imagecategory;

    private int  mylike;

    private int unlike;

    private int userid;

    public Image() {
    }

    public Image(String name, byte[] data_image, String category) {
        this.name = name;
        this.data_image = data_image;
        this.imagecategory = category;
    }

    public Image(String name, byte[] data_image, String category, int like, int unlike) {
        this.name = name;
        this.data_image = data_image;
        this.imagecategory = category;
        this.mylike = like;
        this.unlike = unlike;
    }

    public Image(String name, byte[] data_image, String category, int like, int unlike, int userid) {
        this.name = name;
        this.data_image = data_image;
        this.imagecategory = category;
        this.mylike = like;
        this.unlike = unlike;
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData_image() {
        return data_image;
    }

    public void setData_image(byte[] data_image) {
        this.data_image = data_image;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getImage_category() {
        return imagecategory;
    }

    public void setImage_category(String image_category) {
        this.imagecategory = image_category;
    }

    public int getMylike() {
        return mylike;
    }

    public void setMylike(int mylike) {
        this.mylike = mylike;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data_image=" + Arrays.toString(data_image) +
                ", image_category='" + imagecategory + '\'' +
                ", mylike=" + mylike +
                ", unlike=" + unlike +
                ", userid=" + userid +
                '}';
    }
}
