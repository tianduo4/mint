package com.jspgou.cms.action.admin;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.ReceiverMessageMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeAct {

    @Autowired
    private OrderMng manager;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @RequestMapping({"/main.do"})
    public String main() {
        return "main";
    }

    @RequestMapping({"/left.do"})
    public String left() {
        return "left";
    }

    @RequestMapping({"/right.do"})
    public String right(HttpServletRequest request, ModelMap model) {
        List o = this.manager.getTotlaOrder();
        ShopAdmin admin = AdminThread.get();
        Long[] c = (Long[]) o.get(0);
        Runtime runtime = Runtime.getRuntime();
        long freeMemoery = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - freeMemoery;
        long maxMemory = runtime.maxMemory();
        long useableMemory = maxMemory - totalMemory + freeMemoery;
        model.addAttribute("c", c);
        model.addAttribute("admin", admin);
        model.addAttribute("restart", Double.valueOf(4.6D));
        model.addAttribute("site", SiteUtils.getWeb(request));
        model.addAttribute("freeMemoery", Long.valueOf(freeMemoery));
        model.addAttribute("totalMemory", Long.valueOf(totalMemory));
        model.addAttribute("usedMemory", Long.valueOf(usedMemory));
        model.addAttribute("maxMemory", Long.valueOf(maxMemory));
        model.addAttribute("useableMemory", Long.valueOf(useableMemory));
        return "right";
    }

    @RequestMapping({"/top.do"})
    public String top(HttpServletRequest request, ModelMap model) {
        ShopAdmin admin = AdminThread.get();
        model.addAttribute("admin", admin);

        ShopMember Receiver = this.shopMemberMng.findUsername(admin.getUsername());
        if (Receiver != null) {
            Pagination pagination = this.receiverMessageMng.getUnreadPage(Receiver.getId(), SimplePage.cpn(null),
                    CookieUtils.getPageSize(request));
            model.addAttribute("rcvMsgUnRead", Integer.valueOf(pagination.getTotalCount()));
        }

        return "top";
    }
}

