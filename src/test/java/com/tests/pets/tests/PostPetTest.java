package com.tests.pets.tests;

import automation.restassured.core.JsonJavaValidator;
import automation.restassured.core.JsonRequestBodyBuilder;
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
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("POST a new pet and update the Pet")
@DisplayName("POST pet")
@Feature("Post Request")

public class PostPetTest extends TestBase {

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

  @DisplayName("POST_Pets")
  @Story("Add a new pet to the store")
  @Description("This request includes Post new pets")
  @Test
  public void postPet() {

    String photoUrlValues="https://pbs.twimg.com/profile_images/948294484596375552/RyGNqDEM_400x400.jpg;https://pbs.twimg.com/profile_images/948294484596375552/RyGNqDEM_400x400.jpg";
    JsonRequestBodyBuilder jsonRequestBodyBuilder=new JsonRequestBodyBuilder();
    JSONObject root =jsonRequestBodyBuilder.postBodyAddOrUpdatePet("0","testAdd","available",
        0,"testCategory",
        photoUrlValues,
        "0;1","tag1;tag2");

    Response response = given()
        .when().log().ifValidationFails()
        .filter(new AllureRestAssured())
        .contentType(ContentType.JSON)
        .body(root)
        .post("/pet")
        .then().assertThat().
            statusCode(200).and().
            contentType(ContentType.JSON).and()
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
    assertTrue(pet.getCategory().getName().matches("testCategory"));
    JsonJavaValidator jsonJavaValidator = new JsonJavaValidator();
    jsonJavaValidator.validateSchema("pet.json", response.body().prettyPrint());

  }

  @DisplayName("POST_Pets")
  @Story("Updates a pet in the store with form data")
  @Description("This request includes updates a pet")
  @Test
  public void postPetFormData() {
    String petId=rootList.get(1).getId();

    Response response = given()
        .when().log().ifValidationFails()
        .filter(new AllureRestAssured())
        .queryParam("status", "available")
        .queryParam("name","testUpdateFormData")
        .post("/pet/"+petId)
        .then()
        .extract().response();

    System.out.println("response body:" + response.body().prettyPrint());
    System.out.println("response Code:" + response.getStatusCode());
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
