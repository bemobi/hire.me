package com.urlmapping.dtos;

public class TopVisitedURLDTO  {
    private String originalURL;
    private Integer clicks;

    public TopVisitedURLDTO(String originalURL, Integer clicks) {
        this.originalURL = originalURL;
        this.clicks = clicks;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }
}
