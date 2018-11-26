/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

import java.util.Date;

/**
 *
 * @author shady
 */
public class ResposneTimeSearch {
    
    
 private   String stationUID;
 private Date fromDate;
 private Date toDate;
 private String modality;

    public ResposneTimeSearch() {
    }

    public ResposneTimeSearch(String stationUID, Date fromDate, Date toDate, String modality) {
        this.stationUID = stationUID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.modality = modality;
    }

    public String getStationUID() {
        return stationUID;
    }

    public void setStationUID(String stationUID) {
        this.stationUID = stationUID;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }


}
