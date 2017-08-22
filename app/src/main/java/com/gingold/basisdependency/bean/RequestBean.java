package com.gingold.basisdependency.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *新站点列表获取
 */

public class RequestBean {
    /**
     * DeptId : 17115
     * DistributionCode : rfd
     * ExpressCompanylevel : 503
     * currentpage : 1
     * pagesize : 12
     */
    @Expose
    @SerializedName("DeptId")
    public int DeptId;

    @Expose
    @SerializedName("DistributionCode")
    public String DistributionCode;

    @Expose
    @SerializedName("ExpressCompanylevel")
    public int ExpressCompanylevel;

    @Expose
    @SerializedName("currentpage")
    public int currentpage;

    @Expose
    @SerializedName("pagesize")
    public int pagesize;
}
