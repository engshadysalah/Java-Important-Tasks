/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom.getalldicomtags;

/**
 *
 * @author root
 */
public class DicomTags {
 
    private String tagName;
    private String tagAddr;
    private String tagVR;
    private String tagValue;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    
    public DicomTags() {
    }
    
    public DicomTags(String tagName, String tagAddr, String tagVR, String tagValue) {
        this.tagName = tagName;
        this.tagAddr = tagAddr;
        this.tagVR = tagVR;
        this.tagValue = tagValue;
    }

    
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagAddr() {
        return tagAddr;
    }

    public void setTagAddr(String tagAddr) {
        this.tagAddr = tagAddr;
    }

    public String getTagVR() {
        return tagVR;
    }

    public void setTagVR(String tagVR) {
        this.tagVR = tagVR;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

}
