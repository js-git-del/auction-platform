package com.jaesung.auction.auction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** 상세 조회용: 경매 + 판매자/최고 입찰자 이름(users 조인) */
@Getter
@Setter
@NoArgsConstructor
public class AuctionDetailRow {
    private Long id;
    private Long sellerId;
    private String sellerUsername;
    private String title;
    private String description;
    private int startPrice;
    private int bidUnit;
    private int currentPrice;
    private Long highestBidderId;
    private String highestBidderUsername;
    private AuctionStatus status;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
