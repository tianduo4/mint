package com.jspgou.core.entity.base;

import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.EmailSender;
import com.jspgou.core.entity.Ftp;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.MessageTemplate;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public abstract class BaseWebsite
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Website";
    public static String PROP_CONTROL_NAME_MINLEN = "controlNameMinlen";
    public static String PROP_RGT = "rgt";
    public static String PROP_CONTROL_ADMIN_IPS = "controlAdminIps";
    public static String PROP_LOCALE_ADMIN = "localeAdmin";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_CLOSE = "close";
    public static String PROP_CURRENT_SYSTEM = "currentSystem";
    public static String PROP_SUFFIX = "suffix";
    public static String PROP_FRONT_CONTENT_TYPE = "frontContentType";
    public static String PROP_PASSWORD = "password";
    public static String PROP_MOBILE = "mobile";
    public static String PROP_DOMAIN_ALIAS = "domainAlias";
    public static String PROP_LOCALE_FRONT = "localeFront";
    public static String PROP_NAME = "name";
    public static String PROP_CONTROL_FRONT_IPS = "controlFrontIps";
    public static String PROP_GLOBAL = "global";
    public static String PROP_HOST = "host";
    public static String PROP_DOMAIN = "domain";
    public static String PROP_RES_PATH = "resPath";
    public static String PROP_BASE_DOMAIN = "baseDomain";
    public static String PROP_PHONE = "phone";
    public static String PROP_CLOSE_REASON = "closeReason";
    public static String PROP_COPYRIGHT = "copyright";
    public static String PROP_RECORD_CODE = "recordCode";
    public static String PROP_EMAIL = "email";
    public static String PROP_ENCODING = "encoding";
    public static String PROP_FRONT_ENCODING = "frontEncoding";
    public static String PROP_SHORT_NAME = "shortName";
    public static String PROP_LFT = "lft";
    public static String PROP_PARENT = "parent";
    public static String PROP_COMPANY = "company";
    public static String PROP_PERSONAL = "personal";
    public static String PROP_CONTROL_RESERVED = "controlReserved";
    public static String PROP_ADMIN = "admin";
    public static String PROP_ID = "id";
    public static String PROP_USERNAME = "username";
    public static String PROP_RELATIVE_PATH = "relativePath";
    public static String PROP_UPLOAD_FTP = "uploadFtp";
    public static String PROP_RESOURCES_PATH = "resourcesPath";

    private int hashCode = -2147483648;
    private Long id;
    private String domain;
    private String name;
    private String additionalTitle;
    private String currentSystem;
    private String suffix;
    private Integer lft;
    private Integer rgt;
    private Date createTime;
    private String baseDomain;
    private String domainAlias;
    private String shortName;
    private String closeReason;
    private Boolean close;
    private Boolean relativePath;
    private String frontEncoding;
    private String frontContentType;
    private String localeFront;
    private String localeAdmin;
    private String controlReserved;
    private Integer controlNameMinlen;
    private String controlFrontIps;
    private String controlAdminIps;
    private String company;
    private String copyright;
    private String recordCode;
    private String email;
    private String phone;
    private String mobile;
    private String version;
    private Boolean restart;
    private Boolean pageSync;
    private Boolean resouceSync;
    EmailSender emailSender;
    private Admin admin;
    private Website parent;
    private Global global;
    private Set<Website> child;
    private String tplMobileSolution;
    private String tplSolution;
    private String resourcesPath;
    private Ftp uploadFtp;
    private Ftp syncPageFtp;
    private Map<String, String> attr;
    private Map<String, MessageTemplate> messages;

    public BaseWebsite() {
        initialize();
    }

    public BaseWebsite(Long id) {
        setId(id);
        initialize();
    }

    public BaseWebsite(Long id, Global global, String domain, String name, String currentSystem, String suffix, Integer lft, Integer rgt, Date createTime, Boolean close, Boolean relativePath, String frontEncoding, String frontContentType, String localeFront, String localeAdmin, Integer controlNameMinlen, String company, String copyright, String recordCode, String email, String phone, String mobile, String resourcesPath) {
        setId(id);
        setGlobal(global);
        setDomain(domain);
        setName(name);
        setCurrentSystem(currentSystem);
        setSuffix(suffix);
        setLft(lft);
        setRgt(rgt);
        setCreateTime(createTime);
        setClose(close);
        setRelativePath(relativePath);
        setFrontEncoding(frontEncoding);
        setFrontContentType(frontContentType);
        setLocaleFront(localeFront);
        setLocaleAdmin(localeAdmin);
        setControlNameMinlen(controlNameMinlen);
        setCompany(company);
        setCopyright(copyright);
        setRecordCode(recordCode);
        setEmail(email);
        setPhone(phone);
        setMobile(mobile);
        setResourcesPath(resourcesPath);
        initialize();
    }

    protected void initialize() {
    }

    public Boolean getPageSync() {
        return this.pageSync;
    }

    public void setPageSync(Boolean pageSync) {
        this.pageSync = pageSync;
    }

    public Boolean getResouceSync() {
        return this.resouceSync;
    }

    public void setResouceSync(Boolean resouceSync) {
        this.resouceSync = resouceSync;
    }

    public Ftp getSyncPageFtp() {
        return this.syncPageFtp;
    }

    public void setSyncPageFtp(Ftp syncPageFtp) {
        this.syncPageFtp = syncPageFtp;
    }

    public String getResourcesPath() {
        return this.resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalTitle() {
        return this.additionalTitle;
    }

    public void setAdditionalTitle(String additionalTitle) {
        this.additionalTitle = additionalTitle;
    }

    public String getCurrentSystem() {
        return this.currentSystem;
    }

    public void setCurrentSystem(String currentSystem) {
        this.currentSystem = currentSystem;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getLft() {
        return this.lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return this.rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Ftp getUploadFtp() {
        return this.uploadFtp;
    }

    public void setUploadFtp(Ftp uploadFtp) {
        this.uploadFtp = uploadFtp;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBaseDomain() {
        return this.baseDomain;
    }

    public void setBaseDomain(String baseDomain) {
        this.baseDomain = baseDomain;
    }

    public String getDomainAlias() {
        return this.domainAlias;
    }

    public void setDomainAlias(String domainAlias) {
        this.domainAlias = domainAlias;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCloseReason() {
        return this.closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public Boolean getClose() {
        return this.close;
    }

    public void setClose(Boolean close) {
        this.close = close;
    }

    public Boolean getRelativePath() {
        return this.relativePath;
    }

    public void setRelativePath(Boolean relativePath) {
        this.relativePath = relativePath;
    }

    public String getFrontEncoding() {
        return this.frontEncoding;
    }

    public void setFrontEncoding(String frontEncoding) {
        this.frontEncoding = frontEncoding;
    }

    public String getFrontContentType() {
        return this.frontContentType;
    }

    public void setFrontContentType(String frontContentType) {
        this.frontContentType = frontContentType;
    }

    public String getLocaleFront() {
        return this.localeFront;
    }

    public void setLocaleFront(String localeFront) {
        this.localeFront = localeFront;
    }

    public String getLocaleAdmin() {
        return this.localeAdmin;
    }

    public void setLocaleAdmin(String localeAdmin) {
        this.localeAdmin = localeAdmin;
    }

    public String getControlReserved() {
        return this.controlReserved;
    }

    public void setControlReserved(String controlReserved) {
        this.controlReserved = controlReserved;
    }

    public Integer getControlNameMinlen() {
        return this.controlNameMinlen;
    }

    public void setControlNameMinlen(Integer controlNameMinlen) {
        this.controlNameMinlen = controlNameMinlen;
    }

    public String getControlFrontIps() {
        return this.controlFrontIps;
    }

    public void setControlFrontIps(String controlFrontIps) {
        this.controlFrontIps = controlFrontIps;
    }

    public String getControlAdminIps() {
        return this.controlAdminIps;
    }

    public void setControlAdminIps(String controlAdminIps) {
        this.controlAdminIps = controlAdminIps;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getRecordCode() {
        return this.recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public EmailSender getEmailSender() {
        return this.emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Website getParent() {
        return this.parent;
    }

    public void setParent(Website parent) {
        this.parent = parent;
    }

    public Global getGlobal() {
        return this.global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public Set<Website> getChild() {
        return this.child;
    }

    public void setChild(Set<Website> child) {
        this.child = child;
    }

    public Map<String, String> getAttr() {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public Map<String, MessageTemplate> getMessages() {
        return this.messages;
    }

    public void setMessages(Map<String, MessageTemplate> messages) {
        this.messages = messages;
    }

    public String getTplMobileSolution() {
        return this.tplMobileSolution;
    }

    public void setTplMobileSolution(String tplMobileSolution) {
        this.tplMobileSolution = tplMobileSolution;
    }

    public String getTplSolution() {
        return this.tplSolution;
    }

    public void setTplSolution(String tplSolution) {
        this.tplSolution = tplSolution;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Website)) return false;

        Website website = (Website) obj;
        if ((getId() == null) || (website.getId() == null)) return false;
        return getId().equals(website.getId());
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

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setRestart(Boolean restart) {
        this.restart = restart;
    }

    public Boolean getRestart() {
        return this.restart;
    }
}

