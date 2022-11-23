package com.jiang.blog.model.pojo;

import java.util.Date;

public class PictureUrl {
    private Integer id;

    private Integer isBanner;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String url;

    private String shortUrl;

    private String category;

    private String fileType;

    private String path;

    private String large;

    private String original;

    private String small;

    private Long dimensionX;

    private Long dimensionY;

    private Long fileSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(Integer isBanner) {
        this.isBanner = isBanner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl == null ? null : shortUrl.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large == null ? null : large.trim();
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original == null ? null : original.trim();
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small == null ? null : small.trim();
    }

    public Long getDimensionX() {
        return dimensionX;
    }

    public void setDimensionX(Long dimensionX) {
        this.dimensionX = dimensionX;
    }

    public Long getDimensionY() {
        return dimensionY;
    }

    public void setDimensionY(Long dimensionY) {
        this.dimensionY = dimensionY;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}