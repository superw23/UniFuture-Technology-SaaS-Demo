package com.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Bilibili")
//Bilibili贝壳转入明细表
public class Bilibili {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String 转入时间;
    private String 转入单号;
    private String 贝壳;
    private String 来源;
    private String 状态;
}
