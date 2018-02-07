package com.mint.cms.action.admin.main;

import com.mint.cms.manager.OrderMng;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalesStatisticController {
    private static final Logger log = LoggerFactory.getLogger(SalesStatisticController.class);

    @Autowired
    private OrderMng orderMng;

    @RequestMapping({"/salesstatistic/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        List orders = this.orderMng.getCountByStatus(null, null, null);
        List notOrders = this.orderMng.getCountByStatus(null, null, Integer.valueOf(3));
        model.addAttribute("notOrders", notOrders);
        Integer flag = Integer.valueOf(1);

        model.addAttribute("flag", flag);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("orders", orders);

        return "salesstatistic/list";
    }

    @RequestMapping({"/salesstatistic/v_sale.do"})
    public String sale(Integer pageNo, HttpServletRequest request, ModelMap model) {
        List orders = this.orderMng.getCountByStatus1(null, null, null);
        List notOrders = this.orderMng.getCountByStatus1(null, null, Integer.valueOf(3));
        Integer flag = Integer.valueOf(1);

        model.addAttribute("notOrders", notOrders);
        model.addAttribute("orders", orders);
        model.addAttribute("flag", flag);
        model.addAttribute("pageNo", pageNo);

        return "salesstatistic/sale";
    }

    @RequestMapping({"/salesStatistic/saleroom.do"})
    public String saleroom(Integer flag, Integer pageNo, Date startTime, Date endTime, HttpServletRequest request, ModelMap model) {
        Calendar calendar = Calendar.getInstance();
        if ((startTime == null) && (endTime == null)) {
            endTime = calendar.getTime();
            calendar.add(2, -1);
            startTime = calendar.getTime();
        }

        if (flag.intValue() == 1) {
            List orders = this.orderMng.getCountByStatus1(startTime, endTime, null);
            model.addAttribute("orders", orders);
        } else if (flag.intValue() == 2) {
            int y;
            if ((request.getParameter("year") != null) && (!request.getParameter("year").equals("")))
                y = Integer.parseInt(request.getParameter("year"));
            else {
                y = calendar.get(1);
            }
            List orders = this.orderMng.getStatisticByYear1(Integer.valueOf(y), null);
            model.addAttribute("year", Integer.valueOf(y));
            model.addAttribute("orders", orders);
        }

        model.addAttribute("flag", flag);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        return "salesstatistic/sale";
    }

    @RequestMapping({"/salesStatistic/refer.do"})
    public String refer(Integer flag, Integer pageNo, Date startTime, Date endTime, HttpServletRequest request, ModelMap model) {
        Calendar calendar = Calendar.getInstance();

        if ((startTime == null) && (endTime == null)) {
            endTime = calendar.getTime();
            calendar.add(2, -1);
            startTime = calendar.getTime();
        }

        if (flag.intValue() == 1) {
            List orders = this.orderMng.getCountByStatus(startTime, endTime,
                    null);

            for (int i = 0; i < orders.size(); i++) ;
        } else if (flag.intValue() == 2) {
            int y;
            if ((request.getParameter("year") != null) &&
                    (!request.getParameter("year").equals(""))) {
                y = Integer.parseInt(request.getParameter("year"));
            } else {
                y = calendar.get(1);
            }

            List orders = this.orderMng.getStatisticByYear(Integer.valueOf(y), null);
            model.addAttribute("orders", orders);
            model.addAttribute("year", Integer.valueOf(y));
        }
        model.addAttribute("flag", flag);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "salesstatistic/list";
    }
}

