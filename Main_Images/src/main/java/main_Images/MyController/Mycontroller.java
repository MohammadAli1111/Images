package main_Images.MyController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import main_Images.MQ.Sender;
import main_Images.Models.*;
import main_Images.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Paths;

@Controller
public class Mycontroller {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Sender sender;

    private String uploadFolder="/resources";



    @GetMapping(value = "/login")
    public ModelAndView getLogin(){
        return new ModelAndView("Login");
    }

    @GetMapping(value = "/register")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("SignUp");
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public String createNewUser(@Valid User user, BindingResult bindingResult) {

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        } else {
            userService.saveUser(user);

        }
        return "redirect:/login";
    }

    // Return the image from the database using HttpServletResponse
    @GetMapping(value = "/Image/display/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @HystrixCommand(fallbackMethod = "getImageDisplay",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") })
    public ResponseEntity<byte[]> fromDatabaseAsHttpServResp(@PathVariable("id") Integer id, HttpServletResponse response)
            throws ServletException, IOException {

        Image image1 = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+id,
                Image.class);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image1.getData_image());

    }


    @GetMapping(value = {"/","/index"})
    @HystrixCommand(fallbackMethod = "getIndex",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") })
    public ModelAndView index(String search,String typeImage)throws IOException{
        ImageList imageList=null;
        ModelAndView modelAndView=new ModelAndView("index");

        ImageTypeList imageTypeList=restTemplate.getForObject("http://Gateway-SERVICE/Edit/getTypes",
                ImageTypeList.class);

        modelAndView.addObject("listImgType",imageTypeList.getImageTypes());

        if(search==null){

            imageList = restTemplate.getForObject("http://Gateway-SERVICE/Show/All",
                    ImageList.class);
            modelAndView.addObject("list",imageList.getImages());
        }else {
            imageList = restTemplate.getForObject("http://Gateway-SERVICE/Edit/byName/"+search+"/typeImage/"
                    +typeImage,
                    ImageList.class);
            modelAndView.addObject("list",imageList.getImages());
        }

            return modelAndView;
    }

    @GetMapping(value = "/Home")
     public ModelAndView Home(String search){
        ImageList imageList=null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findUserByEmail(auth.getName());

        if(search==null){

             imageList = restTemplate.getForObject("http://Gateway-SERVICE/Show/byUserId/"+user.getId(),
                    ImageList.class);
        }else {
            imageList = restTemplate.getForObject(
                    "http://Gateway-SERVICE/Show/byUserId/"+user.getId()+"/imageName/"+search,
                    ImageList.class);
        }

        return new ModelAndView("Home","list",imageList.getImages());
    }


    @GetMapping(value = "/AddImage")
    public ModelAndView AddImage(){
      ModelAndView modelAndView=  new ModelAndView("AddImage","image",new Image());
        ImageTypeList imageTypeList=restTemplate.getForObject("http://Gateway-SERVICE/Edit/getTypes",
                ImageTypeList.class);

        modelAndView.addObject("listImgType",imageTypeList.getImageTypes());
        return modelAndView;
    }

    @PostMapping(value = "/AddImage")
    public String postAddImage(@Valid Image image,@RequestParam("Image") MultipartFile file
    ,HttpServletRequest request){

        try {
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);

            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            File dir = new File(uploadDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();

            byte[] imageData = file.getBytes();
            //username
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Image imageSend=new Image(image.getName(),imageData,image.getImage_category(),user.getId());
            sender.sendMsg(imageSend);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return "redirect:/Home";
    }

    @GetMapping(value = "/Delete/{id}")
    public String Delete(@PathVariable int id) {

        restTemplate.getForObject("http://Gateway-SERVICE/Show/Detete/"+id,
                Image.class);

        return "redirect:/Home";
    }

    //يجيب الصورة من اجل التعديل
    @GetMapping(value = "/UpdateImage")
    public ModelAndView getUpdate(@RequestParam("id")int id){
        //يجيب الصورة بطريقة ديناميكي من خلال اسم البوابة لانها تعرف جيع الخدمات الصغيرة وتعرف البورت
        Image image = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+id,
                Image.class);
        ModelAndView modelAndView=   new ModelAndView("UpdateImage","image",image);
        ImageTypeList imageTypeList=restTemplate.getForObject("http://Gateway-SERVICE/Edit/getTypes",
                ImageTypeList.class);

        modelAndView.addObject("listImgType",imageTypeList.getImageTypes());


        return modelAndView;
    }


    @PostMapping(value = "/UpdateImage")
    public String postUpDateImage(@Valid Image image,@RequestParam("Image") MultipartFile file
            ,HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        try {
            if(file.getOriginalFilename().isEmpty()) {
                Image image1 = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+image.getId(),
                        Image.class);

                Image imageSend = new Image(image.getName(), image1.getData_image(),image.getImage_category(), user.getId());
                imageSend.setId(image.getId());
                sender.sendMsg(imageSend);
            }else{
                String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);

                String fileName = file.getOriginalFilename();
                String filePath = Paths.get(uploadDirectory, fileName).toString();
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();

                byte[] imageData = file.getBytes();

                Image imageSend = new Image(image.getName(), imageData,image.getImage_category(), user.getId());
                imageSend.setId(image.getId());
                sender.sendMsg(imageSend);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return "redirect:/Home";
    }

    //يحمل الصورة على سطح المكتب
    @GetMapping(value = "/Download/{id}")
    public String getDownload(@PathVariable int id) throws IOException {

        Image image = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+id,
                Image.class);

        try {
            String path=System.getProperty("user.home")+"/Desktop";
            File file=new File(path+"/"+image.getName()+".jpg");
            ByteArrayInputStream inputStream=new ByteArrayInputStream(image.getData_image());
            ImageIO.write(ImageIO.read(inputStream),"jpg",file);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

        return "redirect:/index";
    }


    @GetMapping(value = "/like/{id}")
    public String getLike(@PathVariable int id){
        Image image = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+id,Image.class);
        int like=image.getMylike()+1;
        image.setMylike(like);
        sender.sendMsg(image);
        return "redirect:/index";
    }

    @GetMapping(value = "/unlike/{id}")
    public String getUnLike(@PathVariable int id){
        Image image = restTemplate.getForObject("http://Gateway-SERVICE/Show/byId/"+id,Image.class);
        int unlike=image.getUnlike()+1;
        image.setUnlike(unlike);
        sender.sendMsg(image);

        return "redirect:/index";
    }


    //_______________________ Hystrix fall back Method __________


    public ModelAndView getIndex(String search,String typeImage){
        ModelAndView modelAndView=new ModelAndView("index");
        HystrixList list=new HystrixList();

        modelAndView.addObject("listImgType",list.getImageTypes());
        modelAndView.addObject("list",list.getImageList());
        return modelAndView;
    }


    public ResponseEntity<byte[]> getImageDisplay(@PathVariable("id") Integer id, HttpServletResponse response){
        Image image=new Image();
        HystrixList list=new HystrixList();
        for (int i=0;i<list.getImageList().size();i++){
            if(list.getImageList().get(i).getId()==id){
                image=list.getImageList().get(i);
            }
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getData_image());
    }


}
