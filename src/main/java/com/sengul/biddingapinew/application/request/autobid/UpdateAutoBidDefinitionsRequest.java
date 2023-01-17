package com.sengul.biddingapinew.application.request.autobid;

import com.sengul.biddingapinew.domain.model.AutoBidDefinition;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAutoBidDefinitionsRequest {

    @NotNull(message = "definitions cannot be null")
    @NotEmpty(message = "definitions cannot be empty string")
    private List<AutoBidDefinition> definitions;
}