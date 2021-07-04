package add_Update_Images.Servicers;

import add_Update_Images.Models.ImageType;
import add_Update_Images.Repositories.ImageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ImageTypeService")
public class ImageTypeService {
    @Autowired
    ImageTypeRepository imageTypeRepository;

    @Transactional
    public void save(ImageType imageType){
        imageTypeRepository.save(imageType);
    }
    @Transactional
    public List<ImageType> findAll(){
        return imageTypeRepository.findAll();
    }
    @Transactional
    public ImageType findByType(String type){
        return imageTypeRepository.findByType(type);
    }
}
