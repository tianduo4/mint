package com.mint.core.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.common.upload.UploadUtils;
import com.mint.common.util.MyFileUtils;
import com.mint.core.entity.base.BaseFtp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.List;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ftp extends BaseFtp {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Ftp.class);

    public String storeByExt(String path, String ext, InputStream in)
            throws IOException {
        String fileName = UploadUtils.generateRamdonFilename(ext);
        String fileUrl = path + fileName;
        store(fileUrl, in);
        return fileUrl;
    }

    public String storeByExts(String path, String ext, InputStream in) {
        String fileName = UploadUtils.generateRamdonFilename(ext);
        String fileUrl = path + fileName;
        if (store(fileUrl, in) == 0) {
            return getUrl() + "/" + fileUrl;
        }
        return "";
    }

    public void storeByExt(String path, InputStream in) throws IOException {
        store(path, in);
    }

    public String storeByFilename(String filename, InputStream in) throws IOException {
        store(filename, in);
        return filename;
    }

    public File retrieve(String name, String fileName) throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        File file = new File(path, fileName);
        file = UploadUtils.getUniqueFile(file);
        FTPClient ftp = getClient();
        OutputStream output = new FileOutputStream(file);
        ftp.retrieveFile(getPath() + name, output);
        output.close();
        ftp.logout();
        ftp.disconnect();
        return file;
    }

    public boolean restore(String name, File file) throws IOException {
        store(name, FileUtils.openInputStream(file));
        file.deleteOnExit();
        return true;
    }

    public int storeByFloder(String folder, String rootPath) {
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                List<File> files = MyFileUtils.getFiles(new File(folder));
                for (File file : files) {
                    String filename = getPath() + file.getName();
                    String name = FilenameUtils.getName(filename);
                    String path = FilenameUtils.getFullPath(filename);
                    String fileAbsolutePath = file.getAbsolutePath();
                    String fileRelativePath = fileAbsolutePath.substring(rootPath.length() + 1, fileAbsolutePath.indexOf(name));
                    path = path + fileRelativePath.replace("\\", "/");
                    if (!ftp.changeWorkingDirectory(path)) {
                        String[] ps = StringUtils.split(path, '/');
                        String p = "/";
                        ftp.changeWorkingDirectory(p);
                        for (String s : ps) {
                            p = p + s + "/";
                            if (!ftp.changeWorkingDirectory(p)) {
                                ftp.makeDirectory(s);
                                ftp.changeWorkingDirectory(p);
                            }
                        }
                    }

                    name = new String(name.getBytes(getEncoding()), "iso-8859-1");
                    if (!file.isFile()) {
                        ftp.makeDirectory(name);
                    } else {
                        InputStream in = new FileInputStream(file.getAbsolutePath());
                        ftp.storeFile(name, in);
                        in.close();
                    }
                }
                ftp.logout();
                ftp.disconnect();
            }
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
        }
        return 4;
    }

    private int store(String remote, InputStream in) {
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                String filename = getPath() + remote;
                String name = FilenameUtils.getName(filename);
                String path = FilenameUtils.getFullPath(filename);
                if (!ftp.changeWorkingDirectory(path)) {
                    String[] ps = StringUtils.split(path, '/');
                    String p = "/";
                    ftp.changeWorkingDirectory(p);
                    for (String s : ps) {
                        p = p + s + "/";
                        if (!ftp.changeWorkingDirectory(p)) {
                            ftp.makeDirectory(s);
                            ftp.changeWorkingDirectory(p);
                        }
                    }
                }
                ftp.storeFile(name, in);
                ftp.logout();
                ftp.disconnect();
            }
            in.close();
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
        }
        return 4;
    }

    private FTPClient getClient() throws SocketException, IOException {
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(
                new PrintCommandListener(new PrintWriter(System.out)));
        ftp.setDefaultPort(getPort().intValue());
        ftp.connect(getIp());
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            log.warn("FTP server refused connection: {}", getIp());
            ftp.disconnect();
            return null;
        }
        if (!ftp.login(getUsername(), getPassword())) {
            log.warn("FTP server refused login: {}, user: {}", getIp(),
                    getUsername());
            ftp.logout();
            ftp.disconnect();
            return null;
        }
        ftp.setControlEncoding(getEncoding());
        ftp.setFileType(2);
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    public Ftp() {
    }

    public Ftp(Integer id) {
        super(id);
    }

    public Ftp(Integer id, String name, String ip, Integer port, String encoding, String url) {
        super(id,
                name,
                ip,
                port,
                encoding,
                url);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("ip", CommonUtils.parseStr(getIp()));
        json.put("port", Integer.valueOf(CommonUtils.parseInteger(getPort())));
        json.put("username", CommonUtils.parseStr(getUsername()));
        json.put("password", "");
        json.put("encoding", CommonUtils.parseStr(getEncoding()));
        json.put("timeout", Integer.valueOf(CommonUtils.parseInteger(getTimeout())));
        json.put("path", CommonUtils.parseStr(getPath()));
        json.put("url", CommonUtils.parseStr(getUrl()));

        return json;
    }
}

