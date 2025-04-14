package com.urlmapping.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Statistics(@JsonProperty("time_taken")String timeTaken) {
}
