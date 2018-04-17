package com.gingold.basisdependency.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class TestBean {

    /**
     * distributioncode : qlkd
     * goodsname : 你
     * goodsnum : 6
     */
    @Expose
    @SerializedName("distributioncode")
    public String distributioncode;
    @Expose
    @SerializedName("goodsname")
    public String goodsname;
    @Expose
    @SerializedName("goodsnum")
    public String goodsnum;
}
