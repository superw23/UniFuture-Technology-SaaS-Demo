//Made by GRPTeam05
package com.app.Entity;

import com.app.Configuration.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "SALE_TBL")
//Sale entity class to store information about the sale
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //primary key
    private int num;
    private int returnNum;
    private int productId;
    @JsonSerialize(using = DoubleSerializer.class)
    private double price;
    @JsonSerialize(using = DoubleSerializer.class)
    private double returnPrice;
    @JsonSerialize(using = DoubleSerializer.class)
    private double total;
    @JsonSerialize(using = DoubleSerializer.class)
    private double returnTotal;
    private String name;
    private String date;
    private String type;
    private String supplierName;
    private String customerName;
}
