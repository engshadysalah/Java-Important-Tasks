/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services;

import com.optimal.services.wrappers.AllExams;
import com.optimal.services.wrappers.CenterPriceListWrapper;
import com.optimal.services.wrappers.RadiologistVaildation;
import com.optimal.services.wrappers.ResposneTimeResult;
import com.optimal.services.wrappers.ResposneTimeSearch;
import com.optimal.services.wrappers.SaveUserWrapper;
import com.optimal.services.wrappers.StudyDetails;
import com.optimal.services.wrappers.TeleRadiologist;
import com.optimal.services.wrappers.TokenData;
import com.optimal.services.wrappers.UserWrapper;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

/**
 *
 * @author mahmoud
 */
public class Services {

    public static String servicesURL = "https://192.168.1.204:9988";

    public static TokenData getAccessToken() {
        ClientConfig clientConfig = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("trusted-optiadv-app", "@ptimalADV0003");
        clientConfig.register(LoggingFilter.class);
        clientConfig.register(feature);
        Client client = SSLClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(servicesURL).path("/oauth/token");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Form form = new Form();
        form.param("grant_type", "client_credentials");
        Response response = invocationBuilder.post(Entity.form(form));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(TokenData.class);
        }
        return null;

    }

    public static Invocation.Builder createAuthorizedBuilder(WebTarget webTarget) {
        TokenData tokenData = null;
        try {
            tokenData = getAccessToken();
        } catch (ProcessingException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tokenData.tokenType :: " + tokenData.tokenType);
        System.out.println("tokenData.accessToken :: " + tokenData.accessToken);
        return webTarget.request()
                .header("Authorization",
                        tokenData.tokenType + " " + tokenData.accessToken);

    }

    public static TeleRadiologist getRadiologistDetails(String userDeviceId, String userName) {
        RadiologistVaildation radValidation = new RadiologistVaildation(userDeviceId, userName);
        Client client = SSLClientBuilder.newClient();
        WebTarget webTarget = client.target(servicesURL).path("/radiologist/details/");
        Response response = createAuthorizedBuilder(webTarget).accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(radValidation, MediaType.APPLICATION_JSON));
        System.out.println("response.getStatus() :: " + response.getStatus());

        return response.readEntity(TeleRadiologist.class);

    }

    public static List<CenterPriceListWrapper> getCenterPriceList() {
        String stationUID = "484a0e4c-f97a-4284-91b4-64055df9f266";
        TokenData tokenData = null;
        try {
            tokenData = getAccessToken();
        } catch (ProcessingException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }

        Client client = SSLClientBuilder.newClient();
        WebTarget webTarget = client.target(servicesURL).path("/reports/centerPriceList/" + stationUID);
        Response response = webTarget.request()
                .header("Authorization", "bearer " + tokenData.accessToken)
                .get();

        try {
            List<CenterPriceListWrapper> centerPriceList = response.readEntity(new GenericType<List<CenterPriceListWrapper>>() {
            });

            for (CenterPriceListWrapper centerPrice : centerPriceList) {
                System.out.println("::categoryName:: " + centerPrice.categoryName
                        + " ::examName:: " + centerPrice.examName
                        + " ::price:: " + centerPrice.price
                );

            }

            return centerPriceList;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }

    public static StudyDetails getStudyDetails(int studyId) {

        TokenData tokenData = null;
        try {
            tokenData = getAccessToken();
        } catch (ProcessingException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }

        Client client = SSLClientBuilder.newClient();
        WebTarget webTarget = client.target(servicesURL).path("/studies/getStudyDetails/" + studyId);
        Response response = webTarget.request()
                .header("Authorization", "bearer " + tokenData.accessToken)
                .get();

        // response.getStatus();
        StudyDetails studyDetails = null;
        try {

            studyDetails = response.readEntity(StudyDetails.class);

            System.out.println(
                    "{ \" Patient Name \" : " + studyDetails.getPatientName()
                    + ",  \" getDescription: \" " + studyDetails.getDescription()
                    + ",  \" getDirPath : \" " + studyDetails.getDirPath()
                    + ",  \" getExamName : \" " + studyDetails.getExamName()
                    + ",  \" getModality : \" " + studyDetails.getModality()
                    + ",  \"  getRadName : \" " + studyDetails.getRadName()
                    + ",  \" getStatus : \" " + studyDetails.getStatus()
                    + ",  \"  getCreationDatetime : \" " + studyDetails.getCreationDatetime()
                    + ",  \"getDicomStudyUid : \" " + studyDetails.getDicomStudyUid()
                    + ",  \" getInternalStudyUid :\" " + studyDetails.getInternalStudyUid()
                    + "}"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return studyDetails;

    }

    public static List<AllExams> getAllExams() {

        TokenData tokenData = null;
        try {
            tokenData = getAccessToken();
        } catch (ProcessingException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }

        Client client = SSLClientBuilder.newClient();
        WebTarget webTarget = client.target(servicesURL).path("/exam/list");
        Response response = webTarget.request()
                .header("Authorization", "bearer " + tokenData.accessToken)
                .get();

        try {
            List<AllExams> examseList = response.readEntity(new GenericType<List<AllExams>>() {
            });

            for (AllExams allExams : examseList) {
                System.out.println("::Exam ID:: " + allExams.getId()
                        + " ::examName:: " + allExams.getName()
                );

            }

            return examseList;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }

    public static List<ResposneTimeResult> getResponseTime(String stationUID, Date fromDate, Date toDate, String modality) {

        ResposneTimeSearch resposneTimeSearch = new ResposneTimeSearch(stationUID, fromDate, toDate, modality);
        Client client = SSLClientBuilder.newClient();

        WebTarget webTarget = client.target(servicesURL).path("/reports/responseTime");
        Response response = createAuthorizedBuilder(webTarget).accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(resposneTimeSearch, MediaType.APPLICATION_JSON));

        System.out.println("response.getStatus() :: " + response.getStatus());

        List<ResposneTimeResult> resposneTimeResultsList = null;
        try {

            resposneTimeResultsList = response.readEntity(new GenericType<List<ResposneTimeResult>>() {
            });

            for (ResposneTimeResult resposneTimeResult : resposneTimeResultsList) {
                System.out.println("::StudyName:: " + resposneTimeResult.getStudyDesc()
                        + " ::Patient Id:: " + resposneTimeResult.getPatientId()
                        + " ::Upload Date:: " + resposneTimeResult.getUploadDate()
                        + " ::Report Date:: " + resposneTimeResult.getReportDate()
                );

                return resposneTimeResultsList;

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }

    // with : createAuthorizedBuilder : Aouth 2.0 : not done , need to add Authorized to service
    public static void updateUse(int userId, String userName, String newPassword, String oldPassword) {

        UserWrapper userWrapper = new UserWrapper(userId, userName, newPassword, oldPassword);

        Client client = SSLClientBuilder.newClient();
        WebTarget webTarget = client.target(servicesURL).path("/edit/User");

        Response response = createAuthorizedBuilder(webTarget).accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(userWrapper, MediaType.APPLICATION_JSON));

        System.out.println("response.getStatus() :: " + response.getStatus());

    }

    // without : createAuthorizedBuilder : done
    public static void updateUser(int userId, String userName, String newPassword, String oldPassword) {

        UserWrapper userWrapper = new UserWrapper(userId, userName, newPassword, oldPassword);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8081/res" + "/edit/user");
        //        WebTarget webTarget = client.target("http://localhost:8081/user" + "/editUserLogin");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(userWrapper, MediaType.APPLICATION_JSON));

        System.out.println("response.getStatus() :: " + response.getStatus());
        System.out.println(response.readEntity(String.class));

    }

    // Done
    //        isUserNameDuplicated(5, "gemy", "111", "123456");
    public static void isUserNameDuplicated(String userName) {

        // UserWrapper userWrapper = new UserWrapper(userId, userName, newPassword, oldPassword);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8081/res" + "/edit/isUserNameDuplicated/" + userName + "");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        System.out.println("response.getStatus() :: " + response.getStatus());

        System.out.println(response.readEntity(String.class));

    }

    // Done save data to USer
    public static void saveUse(SaveUserWrapper user) {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8081/res" + "/save/user");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

        System.out.println("response.getStatus() :: " + response.getStatus());

    }

    // Done :: delete user
    public static void deleteUSer(int id) {

        // UserWrapper userWrapper = new UserWrapper(userId, userName, newPassword, oldPassword);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8081/res" + "/delete/user/" + id + "");

        Invocation.Builder invocationBuilder = webTarget.request();
        Response response = invocationBuilder.delete();

        System.out.println("response.getStatus() :: " + response.getStatus());

        System.out.println(response.readEntity(String.class));

    }

    // getSpecificUser :: problem
    public static void getSpecificUser(int userId) {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8081/res" + "/get/user/" + userId + "");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        SaveUserWrapper readEntity = response.readEntity(SaveUserWrapper.class);

//        System.out.println("FullNAme :: " + readEntity.getFullName());
        System.out.println("response.getStatus() :: " + response.getStatus());
        //  System.out.println(response.readEntity(String.class));

    }

    public static void main(String[] args) {

        //  updateUser(5, "gemy", "123456", "111");
        //isUserNameDuplicated("shady");
        // saveUse(new SaveUserWrapper(4, "cccc", "ccc", "ccc", 2));
        //deleteUSer(41);
        getSpecificUser(30);

        // getCenterPriceList();
        //getStudyDetails(8);
        //getAllExams();
        // getResponseTime("484a0e4c-f97a-4284-91b4-64055df9f266", new Date(1 / 1 / 2000), new Date(1 / 1 / 2018), "CT");
    }
}
