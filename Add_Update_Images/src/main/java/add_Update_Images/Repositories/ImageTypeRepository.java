package add_Update_Images.Repositories;

import add_Update_Images.Models.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ImageTypeRepository")
public interface ImageTypeRepository extends JpaRepository<ImageType,Integer> {
    ImageType findByType(String type);
}
