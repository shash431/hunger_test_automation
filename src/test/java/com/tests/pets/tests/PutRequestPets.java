package com.tests.pets.tests;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import automation.restassured.core.JsonJavaValidator;
import automation.restassured.core.JsonRequestBodyBuilder;
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
import org.json.simple.JSONObject;

import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Epic("PUT to update the Pet")
@DisplayName("PUT pet")
@Feature("Put Request")
public class PutRequestPets extends TestBase {

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

    @DisplayName("PUT_Pets")
    @Story("Update an existing pet")
    @Description("This request includes updates an existing pet")
    @Test
    public void updatePet() {
        String petId=rootList.get(0).getId();
        String photoUrlValues="https://pbs.twimg.com/profile_images/948294484596375552/RyGNqDEM_400x400.jpg;https://pbs.twimg.com/profile_images/948294484596375552/RyGNqDEM_400x400.jpg";
        JsonRequestBodyBuilder jsonRequestBodyBuilder=new JsonRequestBodyBuilder();
        JSONObject root =jsonRequestBodyBuilder.postBodyAddOrUpdatePet(petId,"testAdd","available",
            0,"testCategory",
            photoUrlValues,
            "0;1","tag1;tag2");

        Response response = given()
            .when().log().ifValidationFails()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(root)
            .put("/pet")
            .then()
            .extract().response();

        Root pet = null;
        try {
            //Parse Json to Javaobject
            pet = mapper.readValue(response.body().prettyPrint(), new TypeReference<Root>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(pet.getId().matches("[0-9]*"));
        pet.getTags().stream().forEach(x -> assertTrue(x.getId().matches("[0-9]*")));
        assertTrue(pet.getStatus().matches("available"));
        assertTrue(pet.getName().matches("testAdd"));
        assertTrue(pet.getId().matches(petId));
        assertTrue(pet.getCategory().getName().matches("testCategory"));

        JsonJavaValidator jsonJavaValidator = new JsonJavaValidator();
        jsonJavaValidator.validateSchema("pet.json", response.body().prettyPrint());


    }



}