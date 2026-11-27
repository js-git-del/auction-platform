package com.jaesung.auction.auction;

import java.time.LocalDateTime;

/** 목록 화면용 */
public record AuctionSummaryResponse(
        Long id,
        String title,
        int currentPrice,
        AuctionStatus status,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public static AuctionSummaryResponse from(Auction a) {
        return new AuctionSummaryResponse(
                a.getId(), a.getTitle(), a.getCurrentPrice(), a.getStatus(), a.getStartAt(), a.getEndAt());
    }
}
