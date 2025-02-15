package com.app.Service;

import com.app.Entity.Bilibili;
import com.app.Entity.User;
import com.app.Entity.UserApplication;
import com.app.Repository.BilibiliRepository;
import com.app.Repository.UserApplicationRepository;
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
/**
 * @Classname UserApplicationService
 * @Description TODO
 * @Date 16/8/2023 下午2:38
 * @Created by SuperW
 */
public class UserApplicationService {
    @Autowired
    private UserApplicationRepository repository;
    @Autowired
    private BilibiliRepository bilibiliRepository;
    //Add a new user in the repository.
    public UserApplication saveApplication(UserApplication application){
        User checker = repository.findBy姓名(application.get姓名());
        if(checker!=null)//user exists, cannot create user with the same name
            return null;
        application.set密码(application.get姓名());
        application.set邮箱(application.get邮箱());
        application.set平台(application.get平台());
        application.set角色(application.get角色());
        application.set图片名(application.get图片名());
        return repository.save(application);
    }

    //Get all users from the repository.
    public List<UserApplication> getApplication(){
        return repository.findAll();
    }

    //Get all users sorted according to a given field and pagination.
    public Page<UserApplication> findUsersWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<UserApplication> applications = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return applications;
    }

    //Delete the user by his id from the repository.
    public String deleteUser(int id){
        repository.deleteById(id);
        return "Customer removed || "+id;
    }
}
