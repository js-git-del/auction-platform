package com.jaesung.auction.auction;

import com.jaesung.auction.auth.AuthUser;
import com.jaesung.auction.common.IdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ResponseEntity<IdResponse> create(@AuthenticationPrincipal AuthUser user,
                                             @Valid @RequestBody CreateAuctionRequest request) {
        Long id = auctionService.create(user.id(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(id));
    }

    @GetMapping
    public List<AuctionSummaryResponse> list(@RequestParam(required = false) AuctionStatus status,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return auctionService.list(status, page, size);
    }

    @GetMapping("/{id}")
    public AuctionDetailResponse detail(@PathVariable Long id) {
        return auctionService.detail(id);
    }
}
