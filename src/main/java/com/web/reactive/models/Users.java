package com.web.reactive.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "users",
        "total",
        "skip",
        "limit"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public record Users(
        @JsonProperty("users")
        List<User> users,
        @JsonProperty("total")
        Integer total,
        @JsonProperty("skip")
        Integer skip,
        @JsonProperty("limit")
        Integer limit
) {
}
