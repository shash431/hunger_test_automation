package com.tests.pets.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class TestBase {
    @BeforeTest
    public static void init(){
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="/v2";
    }
}
