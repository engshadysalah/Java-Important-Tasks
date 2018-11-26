/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 *
 * @author shady
 */
public class StudyDetails {

    
    @JsonProperty("id")
    private Integer id;
   
    @JsonProperty("patientName")
    private String patientName;
  
    @JsonProperty("description")
    private String description;
 
    @JsonProperty("dirPath")
    private String dirPath;
   
    @JsonProperty("examName")
    private String examName;
  
    @JsonProperty("modality")
    private String modality;
   
    @JsonProperty("radName")
    private String radName;
   
    @JsonProperty("status")
    private Integer status;
   
    @JsonProperty("creationDatetime")
    private Date creationDatetime;
  
    @JsonProperty("locked")
    private boolean locked = false;
 
    @JsonProperty("dicomStudyUid")
    String dicomStudyUid;
   
    @JsonProperty("internalStudyUid")
    String internalStudyUid;

    
    
    public StudyDetails() {
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getRadName() {
        return radName;
    }

    public void setRadName(String radName) {
        this.radName = radName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getDicomStudyUid() {
        return dicomStudyUid;
    }

    public void setDicomStudyUid(String dicomStudyUid) {
        this.dicomStudyUid = dicomStudyUid;
    }

    public String getInternalStudyUid() {
        return internalStudyUid;
    }

    public void setInternalStudyUid(String internalStudyUid) {
        this.internalStudyUid = internalStudyUid;
    }

}
