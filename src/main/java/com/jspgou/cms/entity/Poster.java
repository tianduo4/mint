package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BasePoster;
import com.jspgou.common.util.DateUtils;
import net.sf.json.JSONObject;

public class Poster extends BasePoster {
    private static final long serialVersionUID = 1L;

    public Poster() {
    }

    public Poster(Integer id) {
        super(id);
    }

    public Poster(Integer id, String picUrl) {
        super(id,
                picUrl);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("picUrl", CommonUtils.parseId(getPicUrl()));
        json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("interUrl", CommonUtils.parseStr(getInterUrl()));
        return json;
    }
}

