package com.web.reactive.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "price",
        "discountPercentage",
        "rating",
        "stock",
        "brand",
        "category",
        "thumbnail",
        "images"
})
public record Products(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("price")
        Integer price,
        @JsonProperty("discountPercentage")
        Double discountPercentage,
        @JsonProperty("rating")
        Double rating,
        @JsonProperty("stock")
        Integer stock,
        @JsonProperty("brand")
        String brand,
        @JsonProperty("category")
        String category,
        @JsonProperty("thumbnail")
        String thumbnail,
        @JsonProperty("images")
        List<String> images
) {
}
