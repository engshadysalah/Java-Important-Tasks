/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Mohamed Shehata
 */
public class TokenData {

    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public int expiresIn;
    @JsonProperty("scope")
    public String scope;
    @JsonProperty("jti")
    public String jti;

}
