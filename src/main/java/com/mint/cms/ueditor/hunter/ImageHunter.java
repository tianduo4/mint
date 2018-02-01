package com.mint.cms.ueditor.hunter;

import com.mint.cms.service.ImageSvc;
import com.mint.cms.ueditor.PathFormat;
import com.mint.cms.ueditor.define.BaseState;
import com.mint.cms.ueditor.define.MultiState;
import com.mint.cms.ueditor.define.State;
import com.mint.core.entity.Website;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ImageHunter {
    private String filename = null;
    private String savePath = null;
    private String rootPath = null;
    private List<String> allowTypes = null;
    private long maxSize = -1L;

    private List<String> filters = null;
    private ImageSvc imgSvc;
    private Website site;

    public ImageHunter(ImageSvc imgSvc, Website site) {
        this.imgSvc = imgSvc;
        this.site = site;
    }

    public State capture(String[] list) {
        MultiState state = new MultiState(true);
        if ((list != null) && (list.length > 0)) {
            for (String source : list) {
                state.addState(captureRemoteData(source));
            }
        }

        return state;
    }

    public State captureByApi(String urlPrefix, String[] list) {
        MultiState state = new MultiState(true);
        if ((list != null) && (list.length > 0)) {
            for (String source : list) {
                state.addState(captureRemoteDataByApi(urlPrefix, source));
            }
        }

        return state;
    }

    public State captureRemoteData(String urlStr) {
        String imgUrl = this.imgSvc.crawlImg(urlStr, this.site);
        State state = new BaseState();
        state.putInfo("url", imgUrl);
        state.putInfo("source", urlStr);
        return state;
    }

    public State captureRemoteDataByApi(String urlPrefix, String urlStr) {
        String imgUrl = this.imgSvc.crawlImg(urlStr, this.site);
        State state = new BaseState();
        state.putInfo("url", urlPrefix + imgUrl);
        state.putInfo("source", urlStr);
        return state;
    }

    private String getPath(String savePath, String filename, String suffix) {
        return PathFormat.parse(savePath + suffix, filename);
    }

    private boolean validHost(String hostname) {
        try {
            InetAddress ip = InetAddress.getByName(hostname);

            if (ip.isSiteLocalAddress())
                return false;
        } catch (UnknownHostException e) {
            return false;
        }

        return !this.filters.contains(hostname);
    }

    private boolean validContentState(int code) {
        return 200 == code;
    }

    private boolean validFileType(String type) {
        return this.allowTypes.contains(type);
    }

    private boolean validFileSize(int size) {
        return size < this.maxSize;
    }
}

