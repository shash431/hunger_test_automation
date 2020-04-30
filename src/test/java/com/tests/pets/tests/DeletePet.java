package com.tests.pets.tests;

import automation.restassured.core.JsonJavaValidator;
import automation.restassured.domain.pets.PetNotFound;
import automation.restassured.domain.pets.Root;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.pets.base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Epic("DELETE a Pet")
@DisplayName("DELETE pet")
@Feature("Delete Request")
public class DeletePet extends TestBase {

    public static List<Root> rootList=new ArrayList<>();
    public static ObjectMapper mapper = new ObjectMapper();
    public static Response response = null;

    @BeforeTest
    public void beforeTest() {
        response=given()
            .filter(new AllureRestAssured())
            .when().log().ifValidationFails()
            .queryParam("status", "available")
            .get("/pet/findByStatus")
            .then()
            .extract().response();
        //Parse Json to Javaobject
        try {
            rootList = mapper.readValue(response.body().prettyPrint(), new TypeReference<List<Root>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @DisplayName("DELETE_Pets_By_Id")
    @Story("DELETE Pets By Id")
    @Description("This request includes delete pets by Id")
    @Test
    public void deletePet() {
        String petId=rootList.get(0).getId();
        Response response = given()
            .filter(new AllureRestAssured())
            .when().log().ifValidationFails()
            .contentType(ContentType.JSON)
            .delete("/pet/"+petId)
            .then()
            .extract().response();

        ObjectMapper mapper = new ObjectMapper();
        PetNotFound petNotFound = null;
        try {
            //Parse Json to Javaobject
            petNotFound=mapper.readValue(response.body().prettyPrint(), new TypeReference<PetNotFound>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(response.getStatusCode(), 200);
        assertEquals(petNotFound.getCode(), 200);
        assertEquals(petNotFound.getMessage(), petId);
        assertEquals(petNotFound.getType(), "unknown");
        JsonJavaValidator jsonJavaValidator = new JsonJavaValidator();
        jsonJavaValidator.validateSchema("delete.json", response.body().prettyPrint());
    }
}