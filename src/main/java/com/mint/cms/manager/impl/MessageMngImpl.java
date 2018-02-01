package com.mint.cms.manager.impl;

import com.mint.cms.dao.MessageDao;
import com.mint.cms.entity.Message;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.MessageMng;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageMngImpl
        implements MessageMng {
    private MessageDao dao;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Transactional(readOnly = true)
    public Pagination getPage(Long sendMemberId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(sendMemberId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Message findById(Long id) {
        Message entity = this.dao.findById(id);
        return entity;
    }

    public Message save(Message bean) {
        this.dao.save(bean);
        return bean;
    }

    public Message update(Message bean) {
        Updater updater = new Updater(bean);
        Message entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Message deleteById(Long id) {
        Message bean = this.dao.deleteById(id);
        return bean;
    }

    public Message[] deleteByIds(Long[] ids) {
        Message[] beans = new Message[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void send(Message message, ShopAdmin admin, String username, Long groupId) throws Exception {
        Date now = new Date();
        ReceiverMessage receiverMessage = new ReceiverMessage();
        ShopMember msgReceiverUser = null;
        ShopMember Receiver = null;
        if (StringUtils.isNotBlank(username)) {
            Receiver = this.shopMemberMng.findUsername(username);
        }
        if (Receiver != null) {
            messageInfoSet(message, receiverMessage, admin, Receiver, now);
        }

        if ((groupId != null) && (groupId.longValue() != -1L)) {
            if (groupId.longValue() == 0L) {
                List users = this.shopMemberMng.getList(null, null);
                if ((users != null) && (users.size() > 0)) {
                    for (int i = 0; i < users.size(); i++) {
                        ShopMember tempUser = (ShopMember) users.get(i);
                        Message tempMsg = new Message();
                        tempMsg.setMsgTitle(message.getMsgTitle());
                        tempMsg.setMsgContent(message.getMsgContent());
                        ReceiverMessage tempReceiverMsg = new ReceiverMessage();
                        if (msgReceiverUser != null) {
                            if (!tempUser.equals(msgReceiverUser))
                                messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
                        } else
                            messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
                    }
                }
            } else {
                List users = this.shopMemberMng.getList(null, groupId);
                if ((users != null) && (users.size() > 0))
                    for (int i = 0; i < users.size(); i++) {
                        ShopMember tempUser = (ShopMember) users.get(i);
                        Message tempMsg = new Message();
                        tempMsg.setMsgTitle(message.getMsgTitle());
                        tempMsg.setMsgContent(message.getMsgContent());
                        ReceiverMessage tempReceiverMsg = new ReceiverMessage();
                        if (msgReceiverUser != null) {
                            if (!tempUser.equals(msgReceiverUser))
                                messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
                        } else
                            messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
                    }
            }
        }
    }

    public void trash(ShopAdmin admin, Long[] ids)
            throws Exception {
        for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
            Message message = this.dao.findById(ids[i.intValue()]);
            ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
            if ((message != null) && (message.getMsgSendUser().equals(admin))) {
                receiverMessage = new ReceiverMessage();
                receiverMessage.setMsgBox(Integer.valueOf(3));
                receiverMessage.setMsgContent(message.getMsgContent());
                receiverMessage.setMsgSendUser(message.getMsgSendUser());
                receiverMessage.setMsgReceiverUser(message
                        .getMsgReceiverUser());
                receiverMessage.setMsgStatus(message.getMsgStatus());
                receiverMessage.setMsgTitle(message.getMsgTitle());
                receiverMessage.setSendTime(message.getSendTime());
                receiverMessage.setMessage(null);

                this.receiverMessageMng.save(receiverMessage);

                Set receiverMessages = message
                        .getReceiverMsgs();

                if ((receiverMessages != null) && (receiverMessages.size() > 0)) {
                    Iterator it = receiverMessages.iterator();
                    while (it.hasNext()) {
                        ReceiverMessage tempReceiverMessage = (ReceiverMessage) it.next();
                        tempReceiverMessage.setMessage(null);
                        this.receiverMessageMng.update(tempReceiverMessage);
                    }
                }
                this.dao.deleteById(ids[i.intValue()]);
            }
            if ((receiverMessage == null) ||
                    (!receiverMessage.getMsgReceiverUser().getMember().getUsername().equals(admin.getUsername())))
                continue;
            receiverMessage.setMsgBox(Integer.valueOf(3));
            this.receiverMessageMng.update(receiverMessage);
        }
    }

    public void revert(ShopAdmin admin, Long[] ids)
            throws Exception {
        for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
            ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);

            if ((receiverMessage != null) && (receiverMessage.getMsgSendUser().equals(admin))) {
                receiverMessage.setMsgBox(Integer.valueOf(1));
                this.receiverMessageMng.update(receiverMessage);
                if (receiverMessage.getMsgBox().intValue() == 1) {
                    Message message = new Message();
                    message.setMsgBox(receiverMessage.getMsgBox());
                    message.setId(receiverMessage.getId());
                    message.setMsgSendUser(receiverMessage.getMsgSendUser());
                    message.setMsgReceiverUser(receiverMessage
                            .getMsgReceiverUser());
                    message.setMsgStatus(false);
                    message.setMsgTitle(receiverMessage.getTitleHtml());
                    message.setMsgContent(receiverMessage.getContentHtml());
                    message.setSendTime(receiverMessage.getSendTime());
                    this.dao.save(message);
                }
                this.receiverMessageMng.deleteById(ids[i.intValue()]);
            }

            if ((receiverMessage == null) ||
                    (!receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername()))) continue;
            receiverMessage.setMsgBox(Integer.valueOf(0));
            this.receiverMessageMng.update(receiverMessage);
        }
    }

    private void messageInfoSet(Message message, ReceiverMessage receiverMessage, ShopAdmin sendUser, ShopMember receiverUser, Date sendTime) {
        message.setMsgBox(Integer.valueOf(1));
        message.setMsgSendUser(sendUser);
        message.setMsgReceiverUser(receiverUser);
        message.setMsgStatus(false);
        message.setSendTime(sendTime);
        save(message);
        receiverMessage.setMsgBox(Integer.valueOf(0));
        receiverMessage.setMsgContent(message.getMsgContent());
        receiverMessage.setMsgSendUser(sendUser);
        receiverMessage.setMsgReceiverUser(receiverUser);
        receiverMessage.setMsgStatus(false);
        receiverMessage.setMsgTitle(message.getMsgTitle());
        receiverMessage.setSendTime(sendTime);
        receiverMessage.setMessage(message);

        this.receiverMessageMng.save(receiverMessage);
    }

    @Autowired
    public void setDao(MessageDao dao) {
        this.dao = dao;
    }

    public void clean(ShopAdmin admin, Long[] ids)
            throws Exception {
        for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
            ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);

            if ((receiverMessage != null) &&
                    (receiverMessage.getMsgSendUser().equals(admin))) {
                this.receiverMessageMng.deleteById(ids[i.intValue()]);
            } else if ((receiverMessage != null) &&
                    (receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername()))) {
                this.receiverMessageMng.deleteById(ids[i.intValue()]);
            } else {
                Message message = receiverMessage.getMessage();
                if (receiverMessage.getMsgBox().equals(Integer.valueOf(3))) {
                    receiverMessage.setMessage(null);
                    if (message != null)
                        this.dao.deleteById(message.getId());
                } else {
                    receiverMessage.setMessage(null);
                }
                if ((message != null) && (message.getMsgSendUser().equals(admin)))
                    this.dao.deleteById(message.getId());
            }
        }
    }
}

