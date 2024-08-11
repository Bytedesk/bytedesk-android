package com.bytedesk.core.util;

/**
 * 自定义消息格式
 *
 * @author bytedesk.com on 2019/3/6
 */
public class JsonCustom {

    // id
    private long id;
    // 自定义类型，包含但不限于：红包，商品等
    private String type;
    // 红包金额 或者 商品价格
    private String price;
    // 商品网址
    private String url;
    // 商品标题
    private String title;
    // 红包附言 或者 商品详情
    private String content;
    // 商品图片URL
    private String imageUrl;
    // 类别
    private String categoryCode;
    // 自定义扩展字段，如果上述字段不能满足开发者需求，则可使用此字段
    private String extra;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
