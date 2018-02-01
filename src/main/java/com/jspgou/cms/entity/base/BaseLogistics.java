package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseLogistics
        implements Serializable {
    public static String REF = "Logistics";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_WEB_URL = "webUrl";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_LOGO_PATH = "logoPath";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String webUrl;
    private String logoPath;
    private Integer priority;
    private Boolean courier;
    private String imgUrl;
    private Integer evenSpacing;
    private Double fnt;
    private Double fnz;
    private Double fat;
    private Double faz;
    private Double fpt;
    private Double fpz;
    private Double snt;
    private Double snz;
    private Double sat;
    private Double saz;
    private Double spt;
    private Double spz;
    private Double fnw;
    private Double fnh;
    private Double faw;
    private Double fah;
    private Double fpw;
    private Double fph;
    private Double snw;
    private Double snh;
    private Double saw;
    private Double sah;
    private Double spw;
    private Double sph;
    private String fnwh;
    private String fawh;
    private String fpwh;
    private String snwh;
    private String sawh;
    private String spwh;
    private Set<LogisticsText> logisticsTextSet;

    public BaseLogistics() {
        initialize();
    }

    public BaseLogistics(Long id) {
        setId(id);
        initialize();
    }

    public BaseLogistics(Long id, String name, Integer priority) {
        setId(id);
        setName(name);
        setPriority(priority);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getCourier() {
        return this.courier;
    }

    public void setCourier(Boolean courier) {
        this.courier = courier;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getEvenSpacing() {
        return this.evenSpacing;
    }

    public void setEvenSpacing(Integer evenSpacing) {
        this.evenSpacing = evenSpacing;
    }

    public Double getFnt() {
        return this.fnt;
    }

    public void setFnt(Double fnt) {
        this.fnt = fnt;
    }

    public Double getFnz() {
        return this.fnz;
    }

    public void setFnz(Double fnz) {
        this.fnz = fnz;
    }

    public Double getFat() {
        return this.fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getFaz() {
        return this.faz;
    }

    public void setFaz(Double faz) {
        this.faz = faz;
    }

    public Double getFpt() {
        return this.fpt;
    }

    public void setFpt(Double fpt) {
        this.fpt = fpt;
    }

    public Double getFpz() {
        return this.fpz;
    }

    public void setFpz(Double fpz) {
        this.fpz = fpz;
    }

    public Double getSnt() {
        return this.snt;
    }

    public void setSnt(Double snt) {
        this.snt = snt;
    }

    public Double getSnz() {
        return this.snz;
    }

    public void setSnz(Double snz) {
        this.snz = snz;
    }

    public Double getSat() {
        return this.sat;
    }

    public void setSat(Double sat) {
        this.sat = sat;
    }

    public Double getSaz() {
        return this.saz;
    }

    public void setSaz(Double saz) {
        this.saz = saz;
    }

    public Double getSpt() {
        return this.spt;
    }

    public void setSpt(Double spt) {
        this.spt = spt;
    }

    public Double getSpz() {
        return this.spz;
    }

    public void setSpz(Double spz) {
        this.spz = spz;
    }

    public Double getFnw() {
        return this.fnw;
    }

    public Double getFnh() {
        return this.fnh;
    }

    public Double getFaw() {
        return this.faw;
    }

    public Double getFah() {
        return this.fah;
    }

    public Double getFpw() {
        return this.fpw;
    }

    public Double getFph() {
        return this.fph;
    }

    public Double getSnw() {
        return this.snw;
    }

    public Double getSnh() {
        return this.snh;
    }

    public Double getSaw() {
        return this.saw;
    }

    public Double getSah() {
        return this.sah;
    }

    public Double getSpw() {
        return this.spw;
    }

    public Double getSph() {
        return this.sph;
    }

    public void setFnw(Double fnw) {
        this.fnw = fnw;
    }

    public void setFnh(Double fnh) {
        this.fnh = fnh;
    }

    public void setFaw(Double faw) {
        this.faw = faw;
    }

    public void setFah(Double fah) {
        this.fah = fah;
    }

    public void setFpw(Double fpw) {
        this.fpw = fpw;
    }

    public void setFph(Double fph) {
        this.fph = fph;
    }

    public void setSnw(Double snw) {
        this.snw = snw;
    }

    public void setSnh(Double snh) {
        this.snh = snh;
    }

    public void setSaw(Double saw) {
        this.saw = saw;
    }

    public void setSah(Double sah) {
        this.sah = sah;
    }

    public void setSpw(Double spw) {
        this.spw = spw;
    }

    public void setSph(Double sph) {
        this.sph = sph;
    }

    public String getFnwh() {
        return this.fnwh;
    }

    public void setFnwh(String Fnwh) {
        this.fnwh = Fnwh;
    }

    public String getFawh() {
        return this.fawh;
    }

    public void setFawh(String fawh) {
        this.fawh = fawh;
    }

    public String getFpwh() {
        return this.fpwh;
    }

    public void setFpwh(String fpwh) {
        this.fpwh = fpwh;
    }

    public String getSnwh() {
        return this.snwh;
    }

    public void setSnwh(String snwh) {
        this.snwh = snwh;
    }

    public String getSawh() {
        return this.sawh;
    }

    public void setSawh(String sawh) {
        this.sawh = sawh;
    }

    public String getSpwh() {
        return this.spwh;
    }

    public void setSpwh(String spwh) {
        this.spwh = spwh;
    }

    public Set<LogisticsText> getLogisticsTextSet() {
        return this.logisticsTextSet;
    }

    public void setLogisticsTextSet(Set<LogisticsText> logisticsTextSet) {
        this.logisticsTextSet = logisticsTextSet;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Logistics)) return false;

        Logistics logistics = (Logistics) obj;
        if ((getId() == null) || (logistics.getId() == null)) return false;
        return getId().equals(logistics.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

