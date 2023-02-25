package com.virtualmind.bidding.service;

import com.virtualmind.bidding.config.BidderConfig;
import com.virtualmind.bidding.exception.ApplicationException;
import com.virtualmind.bidding.exception.NotFoundException;
import com.virtualmind.bidding.model.AuctionResponse;
import com.virtualmind.bidding.model.BidResponse;
import com.virtualmind.bidding.service.impl.BiddingServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class BiddingServiceImplTest {

    private static final List<String> urlList = Arrays.asList("http://bidder1.com", "http://bidder2.com");

    @Mock
    private BidderConfig bidderConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BiddingServiceImpl biddingService;

    @Test
    public void getAuctionResult() throws NotFoundException, ApplicationException {
        long id = 1;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("a", "5");

        List<BidResponse> bidResponses = new ArrayList<>();
        bidResponses.add(new BidResponse(1L, 100L, "a:$price"));
        bidResponses.add(new BidResponse(2L, 200L, "b:$price"));

        Mockito.when(bidderConfig.getBidderUrls()).thenReturn(urlList);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(HttpEntity.class), Mockito.eq(BidResponse.class)))
                .thenReturn(new ResponseEntity<>(new BidResponse(1L, 100L, "a:$price"), HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(new BidResponse(2L, 200L, "b:$price"), HttpStatus.OK));

        AuctionResponse auctionResponse = biddingService.getAuctionResult(id, attributes);

        Assert.assertEquals("b:$price", auctionResponse.getContent());
    }

    @Test(expected = NotFoundException.class)
    public void getAuctionResult_withNoBids() throws NotFoundException, ApplicationException {
        long auctionId = 1234L;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("a", "5");
        attributes.put("c", "10");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Mockito.when(bidderConfig.getBidderUrls()).thenReturn(urlList);

        biddingService.getAuctionResult(auctionId, attributes);
    }

    @Test(expected = ApplicationException.class)
    public void getAuctionResult_withErrorResponse() throws NotFoundException, ApplicationException {
        long id = 1;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("a", "5");

        Mockito.when(bidderConfig.getBidderUrls()).thenReturn(urlList);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(HttpEntity.class), Mockito.eq(BidResponse.class)))
                .thenThrow(new RestClientResponseException("Error", HttpStatus.BAD_REQUEST.value(), "Error", null, null, null));

        biddingService.getAuctionResult(id, attributes);
    }

}
