/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author shady
 */
public class AllExams {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
   

    
    
}
