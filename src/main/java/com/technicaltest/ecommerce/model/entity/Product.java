package com.technicaltest.ecommerce.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    private Long id;

    private String name;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Price> pricesList = new ArrayList<>();

    public Product(Long id, String name, List<Price> pricesList) {
        this.id = id;
        this.name = name;
        this.pricesList = pricesList;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Price> getPricesList() {
        return pricesList;
    }

    public void setPricesList(List<Price> pricesList) {
        this.pricesList = pricesList;
    }
}
