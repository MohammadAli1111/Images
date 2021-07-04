package add_Update_Images.Repositories;

import add_Update_Images.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ImageRepository")
public interface ImageRepository extends JpaRepository<Image,Integer> {

}
