//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Bilibili;
import com.app.Entity.User;
import com.app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//UserController class passes requests from the client and then let the UserService class deals with them.
public class UserController {
    @Autowired
    private UserService service;

    //添加新达人或管理员
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return service.saveUser(user);
    }

    //Get all users.
    @GetMapping("/users")
    public List<User> findAllUsers(){
        return service.getUsers();
    }

    //Reset the user's password.
    @GetMapping("/resetPassword")
    public User resetPassword(@RequestParam String userName, @RequestParam String oldPassword, @RequestParam String newPassword){
        return service.resetPassword(userName, oldPassword, newPassword);
    }

    //Reset the user's permission.
    @GetMapping("/resetUserPermission")
    public User resetUserPermission(@RequestParam String userName, @RequestParam String newRole){
        return service.resetUserPermission(userName, newRole);
    }

    //Get the current role(such as manager) of the user.
    @GetMapping("/currentRole")
    public String getRole(@RequestParam String userName){return service.getRole(userName);}

    //Get the email of the user.
    @GetMapping("/currentEmail")
    public String getEmail(@RequestParam String userName){return service.getEmail(userName);}

    //Get the password of the user.
    @GetMapping("/getPassword")
    public String getPassword(@RequestParam String userName){return service.getPassword(userName);}

    //Get the phone number of the user.
    @GetMapping("/getPhoneNumber")
    public String getPhoneNumber(@RequestParam String userName){return service.getPhoneNumber(userName);}

    //Set the number of users shown in one page.
    @GetMapping("/userPagination/{offset}/{pageSize}")
    private List<User> getUsersWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<User> usersWithPagination = service.findUsersWithPagination(offset, pageSize);
        return usersWithPagination.getContent();
    }

    //Get all users sorted according to a given field and pagination.
    @GetMapping("/userPaginationAndSort/{offset}/{pageSize}/{field}")
    private List<User> getUsersWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<User> usersWithPagination = service.findUsersWithPaginationAndSorting(offset, pageSize, field);
        return usersWithPagination.getContent();
    }

    //Send the password to the user via his email.
    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String userName, @RequestParam String email){ return service.sendEmail(userName, email); }

    //Check if the user exists.
    @PutMapping("/checkLogin")
    public String findUser(@RequestBody User user){
        return service.findUser(user);
    }

    //Update information of the user.
    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user){
        return service.updateUser(user);
    }

    //Delete the user by his id.
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id){
        return service.deleteUser(id);
    }
}
