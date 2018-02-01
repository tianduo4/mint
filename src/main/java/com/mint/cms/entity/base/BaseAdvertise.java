package com.mint.cms.entity.base;

import com.mint.cms.entity.Adspace;
import com.mint.cms.entity.Advertise;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public abstract class BaseAdvertise
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Advertise";
    public static String PROP_NAME = "name";
    public static String PROP_WEIGHT = "weight";
    public static String PROP_ADSPACE = "adspace";
    public static String PROP_ENABLE = "enable";
    public static String PROP_CLICK_COUNT = "clickCount";
    public static String PROP_DSIPLAY_COUNT = "displayCount";
    public static String PROP_ID = "id";
    public static String PROP_END_TIME = "endTime";
    public static String PROP_START_TIME = "startTime";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private Integer weight;
    private Integer displayCount;
    private Integer clickCount;
    private Date startTime;
    private Date endTime;
    private Boolean enabled;
    private Adspace adspace;
    private Map<String, String> attr;

    public BaseAdvertise() {
        initialize();
    }

    public BaseAdvertise(Integer id) {
        setId(id);
        initialize();
    }

    public BaseAdvertise(Integer id, Adspace adspace) {
        setId(id);
        setAdspace(adspace);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDisplayCount() {
        return this.displayCount;
    }

    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

    public Integer getClickCount() {
        return this.clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Adspace getAdspace() {
        return this.adspace;
    }

    public void setAdspace(Adspace adspace) {
        this.adspace = adspace;
    }

    public Map<String, String> getAttr() {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Advertise)) return false;

        Advertise advertise = (Advertise) obj;
        if ((getId() == null) || (advertise.getId() == null)) return false;
        return getId().equals(advertise.getId());
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

