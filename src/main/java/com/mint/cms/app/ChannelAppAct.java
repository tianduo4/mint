package com.mint.cms.app;

import com.mint.cms.entity.ShopChannel;
import com.mint.cms.manager.ShopChannelMng;
import com.mint.cms.web.SiteUtils;
import com.mint.core.entity.Website;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChannelAppAct {

    @Autowired
    private ShopChannelMng channelMng;

    @RequestMapping({"/api/channelList.jspx"})
    public void channelList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Website web = SiteUtils.getWeb(request);
        Map map = new HashMap();
        String parentId = request.getParameter("parentId");
        String count = request.getParameter("count");

        List list = null;
        String result = "00";
        if (StringUtils.isNotBlank(parentId)) {
            ShopChannel channel = this.channelMng.findById(Integer.valueOf(Integer.parseInt(parentId)));
            if (channel != null)
                list = new ArrayList(channel.getChild());
            else {
                list = new ArrayList();
            }
        } else if (StringUtils.isNotBlank(count)) {
            list = this.channelMng.getTopListForTag(web.getId(), Integer.valueOf(Integer.parseInt(count)));
        }

        if (list != null) {
            if (list.size() > 0) {
                result = "01";
                map.put("pd", getShopChannelJson(list));
            } else {
                result = "02";
            }
        } else result = "02";

        map.put("result", result);
    }

    private String getShopChannelJson(List<ShopChannel> beanList) throws JSONException {
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        for (ShopChannel channel : beanList) {
            o.put("id", channel.getId());
            o.put("name", channel.getName());
            arr.put(o);
        }

        return arr.toString();
    }
}

