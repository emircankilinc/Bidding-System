package com.virtualmind.bidding.service.intf;

import com.virtualmind.bidding.exception.ApplicationException;
import com.virtualmind.bidding.exception.NotFoundException;
import com.virtualmind.bidding.model.AuctionResponse;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface BiddingService {
    AuctionResponse getAuctionResult(long id, @NonNull Map<String, String> attributes) throws NotFoundException, ApplicationException;
}
