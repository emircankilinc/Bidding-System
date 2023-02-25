package com.virtualmind.bidding.service.impl;

import com.virtualmind.bidding.config.BidderConfig;
import com.virtualmind.bidding.constant.BidConstants;
import com.virtualmind.bidding.exception.ApplicationException;
import com.virtualmind.bidding.exception.NotFoundException;
import com.virtualmind.bidding.model.AuctionResponse;
import com.virtualmind.bidding.model.BidRequest;
import com.virtualmind.bidding.model.BidResponse;
import com.virtualmind.bidding.service.intf.BiddingService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class BiddingServiceImpl implements BiddingService {

    private final BidderConfig bidderConfig;
    private final RestTemplate restTemplate;

    public BiddingServiceImpl(BidderConfig bidderConfig, RestTemplate restTemplate) {
        this.bidderConfig = bidderConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public AuctionResponse getAuctionResult(long id, @NonNull Map<String, String> attributes) throws NotFoundException, ApplicationException {

        BidRequest bidRequest = new BidRequest(id, attributes);
        List<BidResponse> bidResponses = sendBidRequests(bidRequest);

        BidResponse winningBid = bidResponses.stream()
                .max(Comparator.comparing(BidResponse::getBid))
                .orElse(null);

        if (winningBid == null) {
            throw new NotFoundException("There is no winning bid in this Auction!");
        }

        String content = winningBid.getContent().replace(BidConstants.PRICE, String.valueOf(winningBid.getBid()));

        return new AuctionResponse(content);
    }

    private List<BidResponse> sendBidRequests(BidRequest bidRequest) throws ApplicationException {
        List<BidResponse> bidResponses = new ArrayList<>();
        List<String> bidderUrls = bidderConfig.getBidderUrls();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BidRequest> requestEntity = new HttpEntity<>(bidRequest, headers);

        for (String url : bidderUrls) {
            try {
                ResponseEntity<BidResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, BidResponse.class);
                if (responseEntity != null && responseEntity.getBody() != null) {
                    BidResponse bidResponse = responseEntity.getBody();
                    bidResponses.add(bidResponse);
                }
            } catch (RestClientResponseException e) {
                throw new ApplicationException(String.format("Bidder url : %s returned with an error!", url), e);
            }
        }
        return bidResponses;
    }
}