package springboot.ltq.entity;

public class JdOrderPrice {
    private Integer id;

    private Long createTime;

    private String sku;

    private String skuName;

    private Integer jdProductId;

    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getJdProductId() {
        return jdProductId;
    }

    public void setJdProductId(Integer jdProductId) {
        this.jdProductId = jdProductId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}