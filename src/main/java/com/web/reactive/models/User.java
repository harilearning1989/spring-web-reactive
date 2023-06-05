package com.web.reactive.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "firstName",
        "lastName",
        "maidenName",
        "age",
        "gender",
        "email",
        "phone",
        "username",
        "password",
        "birthDate",
        "image",
        "bloodGroup",
        "height",
        "weight",
        "eyeColor",
        "domain",
        "ip",
        "macAddress",
        "university",
        "ein",
        "ssn",
        "userAgent"
})
public record User(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("maidenName")
        String maidenName,
        @JsonProperty("age")
        Integer age,
        @JsonProperty("gender")
        String gender,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("username")
        String username,
        @JsonProperty("password")
        String password,
        @JsonProperty("birthDate")
        String birthDate,
        @JsonProperty("image")
        String image,
        @JsonProperty("bloodGroup")
        String bloodGroup,
        @JsonProperty("height")
        Integer height,
        @JsonProperty("weight")
        Double weight,
        @JsonProperty("eyeColor")
        String eyeColor,
        @JsonProperty("domain")
        String domain,
        @JsonProperty("ip")
        String ip,
        @JsonProperty("macAddress")
        String macAddress,
        @JsonProperty("university")
        String university,
        @JsonProperty("ein")
        String ein,
        @JsonProperty("ssn")
        String ssn,
        @JsonProperty("userAgent")
        String userAgent
) {
}
