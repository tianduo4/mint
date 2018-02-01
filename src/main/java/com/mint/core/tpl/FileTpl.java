package com.mint.core.tpl;

import com.mint.common.file.FileWrap;
import com.mint.common.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileTpl
        implements Tpl {
    private File file;
    private String root;
    private List<FileTpl> child;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("path", getPath());
        json.put("filename", getFilename());
        if (StringUtils.isNotBlank(getSource()))
            json.put("source", getSource());
        else {
            json.put("source", "");
        }
        json.put("length", getLength());
        json.put("lastModifiedDate", DateUtils.parseDateToTimeStr(getLastModifiedDate()));
        json.put("size", getSize());
        json.put("isDirectory", isDirectory());
        return json;
    }

    public JSONObject convertToTreeJson(FileTpl ob) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", ob.getFilename());
        json.put("path", ob.getName());
        json.put("isDirectory", ob.isDirectory());
        List childs = ob.getChild();
        if (childs.size() > 0) {
            JSONArray childJson = new JSONArray();
            for (int i = 0; i < childs.size(); i++) {
                FileTpl f = (FileTpl) childs.get(i);
                JSONObject obj = new JSONObject();
                obj = convertToTreeJson(f);
                childJson.put(i, obj);
            }
            json.put("child", childJson);
        } else {
            json.put("child", "");
        }
        return json;
    }

    public FileTpl(File file, String root) {
        this.file = file;
        this.root = root;
    }

    public String getName() {
        String ap = this.file.getAbsolutePath().substring(this.root.length());
        ap = ap.replace(File.separatorChar, '/');

        if (!ap.startsWith("/")) {
            ap = "/" + ap;
        }
        return ap;
    }

    public String getPath() {
        String name = getName();
        return name.substring(0, name.lastIndexOf('/'));
    }

    public String getFilename() {
        return this.file.getName();
    }

    public String getSource() {
        if (this.file.isDirectory())
            return null;
        try {
            return FileUtils.readFileToString(this.file, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getLastModified() {
        return this.file.lastModified();
    }

    public Date getLastModifiedDate() {
        return new Timestamp(getLastModified());
    }

    public long getLength() {
        return this.file.length();
    }

    public int getSize() {
        return (int) (getLength() / 1024L) + 1;
    }

    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    public File getFile() {
        return this.file;
    }

    public List<FileTpl> getChild() {
        if (this.child != null) {
            return this.child;
        }

        File[] files = getFile().listFiles();
        List list = new ArrayList();
        if (files != null) {
            Arrays.sort(files, new FileWrap.FileComparator());
            for (File f : files) {
                FileTpl fw = new FileTpl(f, this.root);
                list.add(fw);
            }
        }
        return list;
    }

    public void setChild(List<FileTpl> child) {
        this.child = child;
    }
}

