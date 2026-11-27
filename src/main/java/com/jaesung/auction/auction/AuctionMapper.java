package com.jaesung.auction.auction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AuctionMapper {

    void insert(Auction auction);

    /** status가 null이면 전체 조회. 최신순. */
    List<Auction> findAll(@Param("status") AuctionStatus status,
                          @Param("size") int size,
                          @Param("offset") int offset);

    AuctionDetailRow findDetailById(Long id);

    /** 시작 시각이 지난 SCHEDULED 경매를 ONGOING으로 바꾸고, 바뀐 행 수를 돌려준다. */
    int startDueAuctions(@Param("now") LocalDateTime now);

    /** 종료 시각이 지난 ONGOING 경매를 ENDED로 바꾸고, 바뀐 행 수를 돌려준다. */
    int endDueAuctions(@Param("now") LocalDateTime now);
}
