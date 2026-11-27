package com.jaesung.auction.auction;

import com.jaesung.auction.common.MaskingUtil;

import java.time.LocalDateTime;

/** 상세 화면용. 최고 입찰자 이름은 마스킹해서 내려준다. */
public record AuctionDetailResponse(
        Long id,
        String sellerUsername,
        String title,
        String description,
        int startPrice,
        int bidUnit,
        int currentPrice,
        String highestBidder,
        AuctionStatus status,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public static AuctionDetailResponse from(AuctionDetailRow r) {
        return new AuctionDetailResponse(
                r.getId(),
                r.getSellerUsername(),
                r.getTitle(),
                r.getDescription(),
                r.getStartPrice(),
                r.getBidUnit(),
                r.getCurrentPrice(),
                MaskingUtil.mask(r.getHighestBidderUsername()),
                r.getStatus(),
                r.getStartAt(),
                r.getEndAt());
    }
}
