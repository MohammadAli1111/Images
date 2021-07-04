package add_Update_Images;

import add_Update_Images.Models.ImageType;
import add_Update_Images.Servicers.ImageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    ImageTypeService imageTypeService;
    @Override
    public void run(String... args) throws Exception {


        if(imageTypeService.findAll().isEmpty()) {
            ImageType love =new ImageType("Love");
            imageTypeService.save(love);

            ImageType People =new ImageType("People");
            imageTypeService.save(People);

            ImageType Computer =new ImageType("Computer");
            imageTypeService.save(Computer);

            ImageType Car =new ImageType("Car");
            imageTypeService.save(Car);

            ImageType Animal =new ImageType("Animal");
            imageTypeService.save(Animal);

            ImageType Other =new ImageType("Other");
            imageTypeService.save(Other);


        }




    }
}
