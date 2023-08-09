package com.jolly.k8sdemo.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class Response {
    String text;
    String description;
}
