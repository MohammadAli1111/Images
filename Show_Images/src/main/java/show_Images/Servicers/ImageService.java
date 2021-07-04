package show_Images.Servicers;

import show_Images.Models.Image;
import show_Images.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ImageService")
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public List<Image> showAll(){
        return imageRepository.findAll();
    }

    @Transactional
    public Image findById(int id){
        return imageRepository.findById(id);
    }
    @Transactional
    public List<Image> findByName(String name){
        return imageRepository.findByName(name);
    }
    @Transactional
    public List<Image> findByNameAndImage_category(String name,String t){
        return imageRepository.findByNameAndAndImagecategory(name,t);
    }

    @Transactional
    public List<Image> findByUser_Id(int user_id){
        return imageRepository.findByUserid(user_id);
    }
    @Transactional
    public List<Image> findByUser_IdAndName(int user_id,String name){
        return imageRepository.findByUseridAndName(user_id,name);
    }
    @Transactional
    public void delete(int id){
        imageRepository.deleteById(id);
    }



}
