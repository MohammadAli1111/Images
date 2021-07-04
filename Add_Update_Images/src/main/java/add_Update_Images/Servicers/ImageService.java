package add_Update_Images.Servicers;

import add_Update_Images.Models.Image;
import add_Update_Images.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ImageService")
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public void save(Image image){
        imageRepository.save(image);
    }

}
