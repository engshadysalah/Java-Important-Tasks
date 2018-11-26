/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author mahmoud
 */
public class CenterPriceListWrapper {
    

    @JsonProperty("categoryName")
    public String categoryName;
    @JsonProperty("examName")
    public String examName;
    @JsonProperty("price")
    public float price;

    public CenterPriceListWrapper() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
    
}
