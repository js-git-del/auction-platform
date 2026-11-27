package com.jaesung.auction.auction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateAuctionRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 2000) String description,
        @NotNull @Min(1) Integer startPrice,
        @NotNull @Min(1) Integer bidUnit,
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime endAt
) {
}
