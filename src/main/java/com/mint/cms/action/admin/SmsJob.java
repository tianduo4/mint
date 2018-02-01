package com.mint.cms.action.admin;

import com.mint.cms.manager.OrderMng;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsJob {

    @Autowired
    private OrderMng manager;

    public void execute() {
        this.manager.abolishOrder();
    }
}

