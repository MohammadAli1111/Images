package show_Images.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import show_Images.Models.Image;
import show_Images.Models.ImageList;
import show_Images.Servicers.ImageService;

@RestController
@RequestMapping("/Show")
public class MyController {

    @Autowired
    ImageService imageService;
    @GetMapping("/All")
    public ImageList getAllImage(){
        return  new ImageList(imageService.showAll());
    }

    @GetMapping("/byId/{id}")
    public Image getImageById(@PathVariable("id") int id){
        return imageService.findById(id);
    }
    @GetMapping("/byName/{name}")
    public ImageList getImagesByName(@PathVariable("name") String name){
        return new ImageList(imageService.findByName(name));
    }
    @GetMapping("/byName/{name}/category/{type}")
    public ImageList getImagesByNameAndType(@PathVariable("name") String name,@PathVariable String type){
        return new ImageList(imageService.findByNameAndImage_category(name,type));
    }

    @GetMapping("/byUserId/{id}")
    public ImageList getImageByUserId(@PathVariable("id") int id){
        return new ImageList(imageService.findByUser_Id(id));
    }
    @GetMapping("/byUserId/{id}/imageName/{name}")
    public ImageList getImageByUserIdAndName(@PathVariable("id") int id,@PathVariable("name") String name){
        return new ImageList(imageService.findByUser_IdAndName(id,name));
    }
    @GetMapping("/Detete/{id}")
    public void Delete(@PathVariable("id") int id){
        imageService.delete(id);
    }
}
