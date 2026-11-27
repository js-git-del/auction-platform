package com.jaesung.auction.auction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** 시각이 된 경매의 상태를 주기적으로 전환한다. */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionScheduler {

    private final AuctionMapper auctionMapper;

    @Scheduled(fixedDelay = 1000)
    public void updateStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // 시작과 종료가 모두 지난 경매도 SCHEDULED -> ONGOING -> ENDED 순서로 한 번에 처리된다.
        int started = auctionMapper.startDueAuctions(now);
        int ended = auctionMapper.endDueAuctions(now);

        if (started > 0 || ended > 0) {
            log.info("경매 상태 전환: 시작 {}건, 종료 {}건", started, ended);
        }
    }
}
