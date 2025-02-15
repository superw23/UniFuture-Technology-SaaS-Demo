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
@Builder
@Entity(name="Purchase")
@Table(name = "PURCHASE_TBL")
//Purchase entity class to store information about the purchase
public class Purchase {
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
    private String type;
    private String supplierName;
    private String remarks;
    private String date;
}
