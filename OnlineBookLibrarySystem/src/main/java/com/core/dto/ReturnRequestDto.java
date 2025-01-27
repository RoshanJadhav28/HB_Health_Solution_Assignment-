package com.core.dto;

import java.util.List;
public class ReturnRequestDto {
    private Long userId;
    private List<String> titles;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
