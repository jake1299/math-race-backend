package com.example.math_race.dto.wsMessage.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRaceNameRequest {

    @NotBlank(message = "raceName is required")
    @Size(max = 20, message = "raceName cannot exceed 20 characters")
    @Pattern(regexp = "^(?:.*\\S){3}.*$", message = "raceName must contain at least 3 actual characters (not spaces)")
    private String raceName;
}
