//Made by GRPTeam05
package com.app.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "SUPPLIER_TBL")
//Supplier entity class to store information about the supplier
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String company;
    private String address;
    private String contact;
    private String phoneNumber;
    private String remarks;
}
