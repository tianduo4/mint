package com.mint.common.developer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleGenerator {
    private static final Logger log = LoggerFactory.getLogger(ModuleGenerator.class);
    public static final String SPT = File.separator;
    public static final String ENCODING = "UTF-8";
    private Properties prop = new Properties();
    private String packName;
    private String fileName;
    private File daoImplFile;
    private File daoFile;
    private File managerFile;
    private File managerImplFile;
    private File actionFile;
    private File pageListFile;
    private File pageEditFile;
    private File pageAddFile;
    private File daoImplTpl;
    private File daoTpl;
    private File managerTpl;
    private File managerImplTpl;
    private File actionTpl;
    private File pageListTpl;
    private File pageEditTpl;
    private File pageAddTpl;

    public ModuleGenerator(String s, String s1) {
        this.prop = new Properties();
        this.packName = s;
        this.fileName = s1;
    }

    private void loadProperties() {
        try {
            log.debug("packName=" + this.packName);
            log.debug("fileName=" + this.fileName);
            FileInputStream fileinput = new FileInputStream(getFilePath(this.packName, this.fileName));
            this.prop.load(fileinput);
            String entityUp = this.prop.getProperty("Entity");
            log.debug("entityUp:" + entityUp);
            if ((entityUp == null) || (entityUp.trim().equals(""))) {
                log.warn("Entity not specified, exit!");
                return;
            }
            String entityLow = entityUp.substring(0, 1).toLowerCase() +
                    entityUp.substring(1);
            log.debug("entityLow:" + entityLow);
            this.prop.put("entity", entityLow);
            if (log.isDebugEnabled()) {
                Set ps = this.prop.keySet();
                for (Iterator localIterator = ps.iterator(); localIterator.hasNext(); ) {
                    Object o = localIterator.next();
                    log.debug(o + "=" + this.prop.get(o));
                }
            }
        } catch (FileNotFoundException filenotfoundexception) {
            filenotfoundexception.printStackTrace();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private void prepareFile() {
        String daoImplFilePath = getFilePath(this.prop.getProperty("dao_impl_p"),
                this.prop.getProperty("Entity") + "DaoImpl.java");
        this.daoImplFile = new File(daoImplFilePath);
        log.debug("daoImplFile:" + this.daoImplFile.getAbsolutePath());

        String daoFilePath = getFilePath(this.prop.getProperty("dao_p"), this.prop
                .getProperty("Entity") +
                "Dao.java");
        this.daoFile = new File(daoFilePath);
        log.debug("daoFile:" + this.daoFile.getAbsolutePath());

        String managerFilePath = getFilePath(this.prop.getProperty("manager_p"),
                this.prop.getProperty("Entity") + "Mng.java");
        this.managerFile = new File(managerFilePath);
        log.debug("managerFile:" + this.managerFile.getAbsolutePath());

        String managerImplFilePath = getFilePath(this.prop
                .getProperty("manager_impl_p"), this.prop.getProperty("Entity") +
                "MngImpl.java");
        this.managerImplFile = new File(managerImplFilePath);
        log.debug("managerImplFile:" + this.managerImplFile.getAbsolutePath());

        String actionFilePath = getFilePath(this.prop.getProperty("action_p"), this.prop
                .getProperty("Entity") +
                "Act.java");
        this.actionFile = new File(actionFilePath);
        log.debug("actionFile:" + this.actionFile.getAbsolutePath());

        String pagePath = "WebContent/WEB-INF/" +
                this.prop.getProperty("config_sys") + "/" +
                this.prop.getProperty("config_entity") + "/";
        this.pageListFile = new File(pagePath + "list.html");
        log.debug("pageListFile:" + this.pageListFile.getAbsolutePath());
        this.pageEditFile = new File(pagePath + "edit.html");
        log.debug("pageEditFile:" + this.pageEditFile.getAbsolutePath());
        this.pageAddFile = new File(pagePath + "add.html");
        log.debug("pageAddFile:" + this.pageAddFile.getAbsolutePath());
    }

    private void prepareTemplate() {
        String tplPack = this.prop.getProperty("template_dir");
        log.debug("tplPack:" + tplPack);
        this.daoImplTpl = new File(getFilePath(tplPack, "dao_impl.txt"));
        this.daoTpl = new File(getFilePath(tplPack, "dao.txt"));
        this.managerImplTpl = new File(getFilePath(tplPack, "manager_impl.txt"));
        this.managerTpl = new File(getFilePath(tplPack, "manager.txt"));
        this.actionTpl = new File(getFilePath(tplPack, "action.txt"));
        this.pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
        this.pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
        this.pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
    }

    private static void stringToFile(File file, String s) throws IOException {
        FileUtils.writeStringToFile(file, s, "UTF-8");
    }

    private void writeFile() {
        try {
            if ("true".equals(this.prop.getProperty("is_dao"))) {
                stringToFile(this.daoImplFile, readTpl(this.daoImplTpl));
                stringToFile(this.daoFile, readTpl(this.daoTpl));
            }
            if ("true".equals(this.prop.getProperty("is_manager"))) {
                stringToFile(this.managerImplFile, readTpl(this.managerImplTpl));
                stringToFile(this.managerFile, readTpl(this.managerTpl));
            }
            if ("true".equals(this.prop.getProperty("is_action"))) {
                stringToFile(this.actionFile, readTpl(this.actionTpl));
            }
            if ("true".equals(this.prop.getProperty("is_page"))) {
                stringToFile(this.pageListFile, readTpl(this.pageListTpl));
                stringToFile(this.pageAddFile, readTpl(this.pageAddTpl));
                stringToFile(this.pageEditFile, readTpl(this.pageEditTpl));
            }
        } catch (IOException e) {
            log.warn("write file faild! " + e.getMessage());
        }
    }

    private String readTpl(File file) {
        String content = null;
        try {
            content = FileUtils.readFileToString(file, "UTF-8");
            Set ps = this.prop.keySet();
            for (Iterator localIterator = ps.iterator(); localIterator.hasNext(); ) {
                Object o = localIterator.next();
                String key = (String) o;
                String value = this.prop.getProperty(key);
                content = content.replaceAll("\\#\\{" + key + "\\}", value);
            }
        } catch (IOException e) {
            log.warn("read file faild. " + e.getMessage());
        }
        return content;
    }

    private String getFilePath(String packageName, String name) {
        log.debug("replace:" + packageName);
        String path = packageName.replaceAll("\\.", "/");
        log.debug("after relpace:" + path);
        return "src/" + path + "/" + name;
    }

    public void generate() {
        loadProperties();
        prepareFile();
        prepareTemplate();
        writeFile();
    }

    public static void main(String[] args) {
        String packName = "com.jeecms.common.developer.template";
        String fileName = "template.properties";
        new ModuleGenerator(packName, fileName).generate();
    }
}

