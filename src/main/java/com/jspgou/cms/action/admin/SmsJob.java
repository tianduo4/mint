package com.jspgou.cms.action.admin;

import com.jspgou.cms.manager.OrderMng;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsJob {

    @Autowired
    private OrderMng manager;

    public void execute() {
        this.manager.abolishOrder();
    }
}

