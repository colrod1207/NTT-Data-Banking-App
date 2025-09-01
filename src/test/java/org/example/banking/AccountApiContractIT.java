package org.example.banking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.atlassian.oai.validator.restassured.OpenApiValidationFilter.openApiValidationFilter;
import static org.hamcrest.Matchers.*;

class AccountApiContractIT {

    private static final String SPEC_PATH = "src/test/resources/openapi.yaml";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080; // el puerto donde arranca tu app
    }

    @Test
    void createAccount_contract_ok() {
        var filter = openApiValidationFilter(SPEC_PATH);

        String body = """
          {
            "customerId": "CUST-001",
            "type": "SAVINGS",
            "currency": "PEN",
            "initialDeposit": 100.0
          }
        """;

        RestAssured.given()
                .filter(filter)
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("/api/v1/accounts")
        .then()
                .statusCode(201)
                .body("customerId", equalTo("CUST-001"))
                .body("type", equalTo("SAVINGS"))
                .body("currency", equalTo("PEN"));
    }

    @Test
    void getAccountById_contract_ok() {
        var filter = openApiValidationFilter(SPEC_PATH);

        RestAssured.given()
                .filter(filter)
        .when()
                .get("/api/v1/accounts/{id}", "ACC-123")
        .then()
                .statusCode(anyOf(is(200), is(404))); // depende de si existe o no
    }
}

