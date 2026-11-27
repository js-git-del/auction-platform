package com.jaesung.auction.auction;

import com.jaesung.auction.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private static final int MAX_PAGE_SIZE = 50;

    private final AuctionMapper auctionMapper;

    public Long create(Long sellerId, CreateAuctionRequest req) {
        LocalDateTime now = LocalDateTime.now();

        if (!req.endAt().isAfter(req.startAt())) {
            throw new IllegalArgumentException("종료 시각은 시작 시각보다 뒤여야 합니다.");
        }
        if (!req.endAt().isAfter(now)) {
            throw new IllegalArgumentException("종료 시각은 현재 시각보다 뒤여야 합니다.");
        }

        Auction auction = new Auction();
        auction.setSellerId(sellerId);
        auction.setTitle(req.title());
        auction.setDescription(req.description());
        auction.setStartPrice(req.startPrice());
        auction.setBidUnit(req.bidUnit());
        auction.setStartAt(req.startAt());
        auction.setEndAt(req.endAt());
        // 시작 시각이 아직 안 됐으면 예정, 이미 지났으면 바로 진행 중
        auction.setStatus(req.startAt().isAfter(now) ? AuctionStatus.SCHEDULED : AuctionStatus.ONGOING);

        auctionMapper.insert(auction);
        return auction.getId();
    }

    public List<AuctionSummaryResponse> list(AuctionStatus status, int page, int size) {
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        int safePage = Math.max(page, 0);
        return auctionMapper.findAll(status, safeSize, safePage * safeSize).stream()
                .map(AuctionSummaryResponse::from)
                .toList();
    }

    public AuctionDetailResponse detail(Long id) {
        AuctionDetailRow row = auctionMapper.findDetailById(id);
        if (row == null) {
            throw new NotFoundException("경매를 찾을 수 없습니다.");
        }
        return AuctionDetailResponse.from(row);
    }
}
