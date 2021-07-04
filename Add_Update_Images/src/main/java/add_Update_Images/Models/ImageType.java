package add_Update_Images.Models;

import javax.persistence.*;

@Entity
@Table
public class ImageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;

    public ImageType() {
    }

    public ImageType(String type) {
        this.type = type;
    }

    public ImageType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
