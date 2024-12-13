package com.technicaltest.ecommerce.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Currency {
    @Id
    private String code;
    private String name;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private List<Price> pricesList = new ArrayList<>();

    public Currency() {
    }

    public Currency(String code, String name, List<Price> pricesList) {
        this.code = code;
        this.name = name;
        this.pricesList = pricesList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
