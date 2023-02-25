package com.virtualmind.bidding.model;

public class AuctionResponse {
    private String content;

    public AuctionResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
