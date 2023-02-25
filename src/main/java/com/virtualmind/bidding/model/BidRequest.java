package com.virtualmind.bidding.model;

import java.util.Map;

public class BidRequest {

    private long id;

    private Map<String, String> attributes;

    public BidRequest(long id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
