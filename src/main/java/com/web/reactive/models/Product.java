package com.web.reactive.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "products",
        "total",
        "skip",
        "limit"
})
public record Product(
    @JsonProperty("products")
    List<Products> products,
    @JsonProperty("total")
    Integer total,
    @JsonProperty("skip")
    Integer skip,
    @JsonProperty("limit")
    Integer limit
) {
}
