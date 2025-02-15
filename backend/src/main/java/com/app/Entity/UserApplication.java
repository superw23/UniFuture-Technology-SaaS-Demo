//Made by GRPTeam05
package com.app.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_application")
//User entity class to store information about the user
public class UserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//user id
    private String 姓名;
    private String 密码;
    private String 邮箱;
    private String 平台;
    private String 角色;
    private String 图片名;
}
