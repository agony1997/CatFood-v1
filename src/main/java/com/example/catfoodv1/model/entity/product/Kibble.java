package com.example.catfoodv1.model.entity.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("KIBBLE")
public class Kibble extends Product{

}
