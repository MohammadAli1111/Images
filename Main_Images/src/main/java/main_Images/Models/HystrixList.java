package main_Images.Models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HystrixList {
    List<Image> imageList = new ArrayList<>();
    List<ImageType> imageTypes = new ArrayList<>();

    public HystrixList() {
        try {
            ImageType love = new ImageType("Love");
            imageTypes.add(love);
            ImageType People = new ImageType("People");
            imageTypes.add(People);
            ImageType Computer = new ImageType("Computer");
            imageTypes.add(Computer);
            ImageType Car = new ImageType("Car");
            imageTypes.add(Car);
            ImageType Animal = new ImageType("Animal");
            imageTypes.add(Animal);
            ImageType Other = new ImageType("Other");
            imageTypes.add(Other);
            //_____________________________

            Image image1 = new Image(1, "desktop", convertImageToByte(new File("src/main/resources/static/assets/img/desk.jpg"))
                    , "Computer", 0, 0, 1);
            imageList.add(image1);
            Image image2 = new Image(2, "nature", convertImageToByte(new File("src/main/resources/static/assets/img/nature-3082832_1920.jpg"))
                    , "Other", 0, 0, 1);
            imageList.add(image2);
            Image image3 = new Image(3, "baby", convertImageToByte(new File("src/main/resources/static/assets/img/align-fingers-71282_1280.jpg"))
                    , "People", 0, 0, 2);
            imageList.add(image3);
            Image image4 = new Image(4, "loft", convertImageToByte(new File("src/main/resources/static/assets/img/loft.jpg"))
                    , "Other", 0, 0, 2);
            imageList.add(image4);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }


    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public List<ImageType> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<ImageType> imageTypes) {
        this.imageTypes = imageTypes;
    }


    private byte[] convertImageToByte(File file) throws Exception {

        BufferedImage bImage1 = ImageIO.read(file);
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ImageIO.write(bImage1, "jpg", bos1);
        byte[] bytes = bos1.toByteArray();
        bos1.flush();
        bos1.close();

        return bytes;
    }
}
