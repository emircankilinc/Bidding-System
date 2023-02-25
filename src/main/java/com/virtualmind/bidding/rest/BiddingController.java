package com.virtualmind.bidding.rest;


import com.virtualmind.bidding.exception.ApplicationException;
import com.virtualmind.bidding.exception.InvalidRequestException;
import com.virtualmind.bidding.exception.NotFoundException;
import com.virtualmind.bidding.model.AuctionResponse;
import com.virtualmind.bidding.service.intf.BiddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BiddingController {

    private final BiddingService biddingService;

    public BiddingController(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAuctionResponse(@PathVariable("id") long id, @RequestParam Map<String, String> attributes) throws InvalidRequestException, NotFoundException, ApplicationException {

        if (attributes == null || attributes.isEmpty()) {
            throw new InvalidRequestException("Attributes parameter is required.");
        }

        AuctionResponse auctionResponse = biddingService.getAuctionResult(id, attributes);
        return ResponseEntity.ok(auctionResponse.getContent());
    }
}