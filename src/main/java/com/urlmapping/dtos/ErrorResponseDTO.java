package com.urlmapping.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseDTO(String alias, @JsonProperty("err_code") String errCode, String description) {
}
