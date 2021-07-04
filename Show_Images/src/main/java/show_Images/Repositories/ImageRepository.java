package show_Images.Repositories;

import show_Images.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ImageRepository")
public interface ImageRepository extends JpaRepository<Image,Integer> {


   List<Image> findByName(String name);
   List<Image> findByNameAndAndImagecategory(String name,String typ);
   Image findById(int id);
   List<Image> findByUserid(int id);
   List<Image> findByUseridAndName(int id,String name);
}
