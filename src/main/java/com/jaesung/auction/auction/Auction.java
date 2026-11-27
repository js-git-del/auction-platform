package com.jaesung.auction.auction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** auctions 테이블 한 행 */
@Getter
@Setter
@NoArgsConstructor
public class Auction {
    private Long id;
    private Long sellerId;
    private String title;
    private String description;
    private int startPrice;
    private int bidUnit;
    private int currentPrice;
    private Long highestBidderId;
    private AuctionStatus status;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
}
