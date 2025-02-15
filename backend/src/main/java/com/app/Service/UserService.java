//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Bilibili;
import com.app.Entity.User;
import com.app.Repository.BilibiliRepository;
import com.app.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
//UserService class interacts with the UserRepository class to perform actions asked by the UserController class.
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private BilibiliRepository bilibiliRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    //Add a new user in the repository.
    public User saveUser(User user){
        User checker = repository.findBy姓名(user.get姓名());
        if(checker!=null)//user exists, cannot create user with the same name
            return null;
        user.set密码(user.get姓名());
        user.set邮箱(user.get邮箱());
        user.set平台(user.get平台());
        user.set角色(user.get角色());
        return repository.save(user);
    }

    //Get all users from the repository.
    public List<User> getUsers(){
        return repository.findAll();
    }

    //Check if the user exists in the repository. If yes, return the role of the user. Else, return error messages.
    public String findUser(User user){
        User checker = repository.findBy姓名(user.get姓名());
        if(checker == null)
            return "userNameNotFound";
        else{
            if(!checker.get密码().equals(user.get密码()))
                return "passwordNotFound";
        }
        return checker.get平台();//both the user's name and password are valid
    }

    //Delete the user by his id from the repository.
    public String deleteUser(int id){
        repository.deleteById(id);
        return "Customer removed || "+id;
    }

    //Get the role of the current user.
    public String getRole(String userName){
        User existingUser = repository.findBy姓名(userName);
        String role = existingUser.get角色();
        return role;
    }

    //Get the email of the current user.
    public String getEmail(String userName){
        User existingUser = repository.findBy姓名(userName);
        String email = existingUser.get邮箱();
        return email;
    }

    //Get the phone number of the current user.
    public String getPhoneNumber(String userName){
        User existingUser = repository.findBy姓名(userName);
        String phoneNumber = existingUser.get邮箱();
        return phoneNumber;
    }

    //Get the password of the current user.
    public String getPassword(String userName){
        User existingUser = repository.findBy姓名(userName);
        String passWord = existingUser.get密码();
        return passWord;
    }

    //Update information of the user.
    public User updateUser(User user){
        User existingUser = repository.findById(user.getId()).orElse(null);
        existingUser.set姓名(user.get姓名());
        existingUser.set姓名(user.get密码());
        existingUser.set邮箱(user.get邮箱());
        existingUser.set平台(user.get平台());
        return repository.save(existingUser);
    }

    //Reset the user's password.
    public User resetPassword(String userName, String oldPassword, String newPassword){
        User existingUser = repository.findBy姓名(userName);
        if(existingUser == null)
            return null;
        if(!existingUser.get密码().equals(oldPassword))
            return null;
        existingUser.set姓名(userName);
        existingUser.set密码(newPassword);
        return repository.save(existingUser);
    }

    //Reset the user's permission.
    public User resetUserPermission(String userName, String newRole){
        User existingUser = repository.findBy姓名(userName);
        if(existingUser == null)
            return null;
        existingUser.set角色(newRole);
        return repository.save(existingUser);
    }

    //Send the password to the user via his email.
    public String sendEmail(String userName, String email) {
        User checker = repository.findBy姓名(userName);
        if(checker == null)
            return "userNameNotFound";
        String receiver = repository.findBy姓名(userName).get邮箱();
        if(!email.equals(receiver))
            return "emailNotFound";
        String subject = "Retrieve your password";
        String content = "Hi " + userName + ",\n" + "Your password is " + repository.findBy姓名(userName).get密码() + ".";
        emailSenderService.sendEmail(receiver, subject, content);
        return "success";
    }

    //Set the number of users shown in one page.
    public Page<User> findUsersWithPagination(int offset, int pageSize){
        Page<User> users = repository.findAll(PageRequest.of(offset, pageSize));
        return  users;
    }

    //Get all users sorted according to a given field and pagination.
    public Page<User> findUsersWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<User> users = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  users;
    }
}
