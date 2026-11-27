package com.jaesung.auction.auction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

/** DB 없이 등록 규칙(시각 검증, 초기 상태)만 검증한다. */
@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock
    AuctionMapper auctionMapper;

    @InjectMocks
    AuctionService auctionService;

    private CreateAuctionRequest request(LocalDateTime startAt, LocalDateTime endAt) {
        return new CreateAuctionRequest("테스트 경매", "설명", 10_000, 1_000, startAt, endAt);
    }

    @Test
    void 시작_시각이_미래이면_SCHEDULED로_저장된다() {
        LocalDateTime now = LocalDateTime.now();

        auctionService.create(1L, request(now.plusHours(1), now.plusHours(2)));

        ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
        verify(auctionMapper).insert(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(AuctionStatus.SCHEDULED);
        assertThat(captor.getValue().getSellerId()).isEqualTo(1L);
    }

    @Test
    void 시작_시각이_이미_지났으면_ONGOING으로_저장된다() {
        LocalDateTime now = LocalDateTime.now();

        auctionService.create(1L, request(now.minusMinutes(1), now.plusHours(1)));

        ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
        verify(auctionMapper).insert(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(AuctionStatus.ONGOING);
    }

    @Test
    void 종료_시각이_시작_시각보다_빠르면_거부된다() {
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> auctionService.create(1L, request(now.plusHours(2), now.plusHours(1))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 종료_시각이_이미_지났으면_거부된다() {
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> auctionService.create(1L, request(now.minusHours(2), now.minusHours(1))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
