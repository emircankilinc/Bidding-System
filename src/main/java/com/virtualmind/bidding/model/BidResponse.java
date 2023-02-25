package com.virtualmind.bidding.model;

public class BidResponse {

    private long id;

    private long bid;

    private String content;

    public BidResponse() {
    }

    public BidResponse(long id, long bid, String content) {
        this.id = id;
        this.bid = bid;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
