package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopPlug;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseShopPlug
        implements Serializable {
    public static String REF = "CmsPlug";
    public static String PROP_NAME = "name";
    public static String PROP_FILE_CONFLICT = "fileConflict";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_USED = "used";
    public static String PROP_UPLOAD_TIME = "uploadTime";
    public static String PROP_UNINSTALL_TIME = "uninstallTime";
    public static String PROP_AUTHOR = "author";
    public static String PROP_ID = "id";
    public static String PROP_INSTALL_TIME = "installTime";
    public static String PROP_PLUG_PERMS = "plugPerms";
    public static String PROP_PATH = "path";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String path;
    private String description;
    private String author;
    private Date uploadTime;
    private Date installTime;
    private Date uninstallTime;
    private boolean fileConflict;
    private boolean used;
    private String plugPerms;
    private boolean plugRepair;

    public BaseShopPlug() {
        initialize();
    }

    public BaseShopPlug(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopPlug(Long id, String name, String path, Date uploadTime, boolean fileConflict, boolean used) {
        setId(id);
        setName(name);
        setPath(path);
        setUploadTime(uploadTime);
        setFileConflict(fileConflict);
        setUsed(used);
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getInstallTime() {
        return this.installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public Date getUninstallTime() {
        return this.uninstallTime;
    }

    public void setUninstallTime(Date uninstallTime) {
        this.uninstallTime = uninstallTime;
    }

    public boolean isFileConflict() {
        return this.fileConflict;
    }

    public void setFileConflict(boolean fileConflict) {
        this.fileConflict = fileConflict;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getPlugPerms() {
        return this.plugPerms;
    }

    public void setPlugPerms(String plugPerms) {
        this.plugPerms = plugPerms;
    }

    public boolean getPlugRepair() {
        return this.plugRepair;
    }

    public void setPlugRepair(boolean plugRepair) {
        this.plugRepair = plugRepair;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopPlug)) return false;

        ShopPlug shopPlug = (ShopPlug) obj;
        if ((getId() == null) || (shopPlug.getId() == null)) return false;
        return getId().equals(shopPlug.getId());
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

