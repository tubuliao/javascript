package com.isoftstone.tyw.dto.info;

import com.isoftstone.tyw.entity.info.Base;

public class BaseSearchDto extends Base {
    private String content;

    private String segItem;

    private Integer likeCount;

    private Integer favCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSegItem() {
        return segItem;
    }

    public void setSegItem(String segItem) {
        this.segItem = segItem;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

}
