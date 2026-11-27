package com.jaesung.auction.auction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class AuctionSchedulerTest {

    @Mock
    AuctionMapper auctionMapper;

    @InjectMocks
    AuctionScheduler scheduler;

    @Test
    void 시작_처리_후_종료_처리_순서로_호출한다() {
        scheduler.updateStatuses();

        InOrder order = inOrder(auctionMapper);
        order.verify(auctionMapper).startDueAuctions(any(LocalDateTime.class));
        order.verify(auctionMapper).endDueAuctions(any(LocalDateTime.class));
    }
}
