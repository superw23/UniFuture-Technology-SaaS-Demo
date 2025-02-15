package com.app.Controller;

/**
 * @Classname UserApplicationController
 * @Description TODO
 * @Date 16/8/2023 下午1:54
 * @Created by SuperW
 */

import com.app.Entity.Bilibili;
import com.app.Entity.User;
import com.app.Entity.UserApplication;
import com.app.Service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
//这是一个注解（annotation），用于标识这个类是一个Spring控制器。它告诉Spring框架该类将处理HTTP请求，并将响应数据以JSON格式返回。
public class UserApplicationController {

    private final Path uploadPath = Paths.get("src/main/resources/static/pic"); // 确保图片在这个文件夹下
    @Autowired
    private UserApplicationService service;

    //添加新达人或管理员
    @PostMapping("/addApplication")
    public UserApplication addUser(@RequestBody UserApplication application){
        return service.saveApplication(application);
    }

    @PostMapping("/uploadProfileImage")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String newFilename = originalFilename;

            String filePath = uploadPath + File.separator + newFilename; // 完整文件路径

            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("Profile image uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile image.");
        }
    }



    //Get all applications.
    @GetMapping("/applications")
    public List<UserApplication> findAllUsers(){
        return service.getApplication();
    }

    //Get all users sorted according to a given field and pagination.
    @GetMapping("/applicationPaginationAndSort/{offset}/{pageSize}/{field}")
    private List<UserApplication> getApplicationWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<UserApplication> usersWithPagination = service.findUsersWithPaginationAndSorting(offset, pageSize, field);
        return usersWithPagination.getContent();
    }

    //Delete the user by his id.
    @DeleteMapping("/deleteApplication/{id}")
    public String deleteUser(@PathVariable int id){
        return service.deleteUser(id);
    }

    @GetMapping("/application/image")
    @ResponseBody
    public Resource getImage(@RequestParam String imageName) throws MalformedURLException {
        Path imagePath = uploadPath.resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());
        return resource;
    }
}
