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
@AllArgsConstructor//automatically create constructors
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT_TBL")//automatically generate the table with the name of "PRODUCT_TBL" in database
//Product entity class to store information about the product
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//the primary key of this table is id
    private int quantity;
    @JsonSerialize(using = DoubleSerializer.class)
    private double purchasePrice;
    @JsonSerialize(using = DoubleSerializer.class)
    private double sellPrice;
    private String name;
    private String type;
    private String supplierName;
    private String remarks;//product description
}
