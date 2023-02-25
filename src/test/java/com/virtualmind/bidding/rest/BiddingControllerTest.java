package com.virtualmind.bidding.rest;

import com.virtualmind.bidding.exception.InvalidRequestException;
import com.virtualmind.bidding.model.AuctionResponse;
import com.virtualmind.bidding.service.intf.BiddingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class BiddingControllerTest {

    @Mock
    private BiddingService biddingService;

    @InjectMocks
    private BiddingController biddingController;

    @Test
    public void getAuctionResponse_withValidParams_returnsContent() throws Exception {
        long id = 123L;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("a", "5");
        attributes.put("c", "10");
        AuctionResponse auctionResponse = new AuctionResponse("a:750");
        Mockito.when(biddingService.getAuctionResult(id, attributes)).thenReturn(auctionResponse);

        ResponseEntity<String> responseEntity = biddingController.getAuctionResponse(id, attributes);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("a:750", responseEntity.getBody());
    }

    @Test(expected = InvalidRequestException.class)
    public void getAuctionResponse_withEmptyParams_throwsInvalidRequestException() throws Exception {
        long id = 123L;
        Map<String, String> attributes =  Collections.emptyMap();

        biddingController.getAuctionResponse(id, attributes);
    }
}
