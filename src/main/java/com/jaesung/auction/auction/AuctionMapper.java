package com.jaesung.auction.auction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuctionMapper {

    void insert(Auction auction);

    /** status가 null이면 전체 조회. 최신순. */
    List<Auction> findAll(@Param("status") AuctionStatus status,
                          @Param("size") int size,
                          @Param("offset") int offset);

    AuctionDetailRow findDetailById(Long id);
}
