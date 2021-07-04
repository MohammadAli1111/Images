package add_Update_Images.Controller;

import add_Update_Images.Models.ImageList;
import add_Update_Images.Models.ImageType;
import add_Update_Images.Models.ImageTypeList;
import add_Update_Images.Servicers.ImageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/Edit")
public class EditController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ImageTypeService imageTypeService;

    @GetMapping("/getTypes")
    public ImageTypeList getTypes(){
        return new ImageTypeList(imageTypeService.findAll());

    }

    @GetMapping("/byName/{name}/typeImage/{typeImage}")
    public ImageList getImageForUser(@PathVariable String name,@PathVariable String typeImage){
       ImageType  imageType= imageTypeService.findByType(typeImage);
       ImageList imageList=null;
       if(imageType==null){//typeImage:All -search by image name
           imageList = restTemplate.getForObject("http://Gateway-SERVICE/Show/byName/"+name,
                   ImageList.class);
       }else {

           imageList = restTemplate.getForObject("http://Gateway-SERVICE/Show/byName/"+name
                           +"/category/"+imageType.getType(),
                   ImageList.class);
       }
        return imageList;
    }



}
