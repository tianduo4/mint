package com.jspgou.cms.webservice;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberGroup;
import com.jspgou.cms.manager.ShopAdminMng;
import com.jspgou.cms.manager.ShopMemberGroupMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.manager.WebserviceAuthMng;
import com.jspgou.cms.manager.WebserviceCallRecordMng;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.manager.RoleMng;
import com.jspgou.core.manager.UserMng;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UserService extends SpringBeanAutowiringSupport {
    private static final String SERVICE_CODE_USER_DELETE = "user_delete";
    private static final String SERVICE_CODE_USER_ADD = "user_add";
    private static final String SERVICE_CODE_USER_UPDATE = "user_update";
    private static final String RESPONSE_CODE_SUCCESS = "100";
    private static final String RESPONSE_CODE_AUTH_ERROR = "101";
    private static final String RESPONSE_CODE_PARAM_REQUIRED = "102";
    private static final String RESPONSE_CODE_USER_NOT_FOUND = "103";
    private static final String RESPONSE_CODE_USER_ADD_ERROR = "104";
    private static final String RESPONSE_CODE_USER_UPDATE_ERROR = "105";
    private static final String RESPONSE_CODE_USER_DELETE_ERROR = "106";
    private static final String LOCAL_IP = "127.0.0.1";

    @Autowired
    private ShopAdminMng shopAdminMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private RoleMng roleMng;

    @Autowired
    private ShopMemberGroupMng shopMemberGroupMng;

    @Autowired
    private WebserviceAuthMng webserviceAuthMng;

    @Autowired
    private WebserviceCallRecordMng webserviceCallRecordMng;

    @Autowired
    private UserMng UserMng;

    public String addUser(String auth_username, String auth_password, String admin, String username, String password, String email, String realname, String sex, String tel, String groupId, String role) {
        String responseCode = "101";
        if (validate(auth_username, auth_password)) {
            if ((StringUtils.isBlank(username)) || (StringUtils.isBlank(password))) {
                responseCode = "102";
            } else {
                if (StringUtils.isBlank(admin))
                    admin = "false";
                try {
                    ShopMember shopmember = new ShopMember();
                    shopmember.setRealName(realname);
                    if (StringUtils.isNotBlank(sex)) {
                        if (sex.equals("true"))
                            shopmember.setGender(Boolean.valueOf(true));
                        else if (sex.equals("false")) {
                            shopmember.setGender(Boolean.valueOf(false));
                        }
                    }
                    shopmember.setMobile(tel);
                    ShopMemberGroup group = null;
                    if (StringUtils.isNotBlank(groupId)) {
                        Long gid = Long.valueOf(Long.parseLong(groupId));
                        group = this.shopMemberGroupMng.findById(gid);
                    }
                    if (group == null) {
                        group = this.shopMemberGroupMng.findById(Long.valueOf(1L));
                    }
                    if (admin.equals("false")) {
                        this.shopMemberMng.register(username, password, email, Boolean.valueOf(true), null, "127.0.0.1", Boolean.valueOf(false), Long.valueOf(1L), group.getId(), null, null, null, null, null, shopmember);
                    } else if (admin.equals("true")) {
                        Integer[] roleIds = null;
                        if (StringUtils.isNotBlank(role)) {
                            String[] roles = role.split(",");
                            roleIds = new Integer[roles.length];
                            for (int i = 0; i < roles.length; i++) {
                                roleIds[i] = Integer.valueOf(Integer.parseInt(roles[i]));
                            }
                        }
                        ShopAdmin bean = new ShopAdmin();
                        bean.setFirstname(realname);
                        this.shopAdminMng.register(username, password, Boolean.valueOf(false), email, "127.0.0.1", false, Long.valueOf(1L), bean);
                    }
                    responseCode = "100";
                    this.webserviceCallRecordMng.save(auth_username, "user_add");
                } catch (Exception e) {
                    e.printStackTrace();
                    responseCode = "104";
                }
            }
        }
        return responseCode;
    }

    public String updateUser(String auth_username, String auth_password, String username, String password, String email, String realname, String sex, String tel, String groupId, String role) {
        String responseCode = "101";
        if (validate(auth_username, auth_password)) {
            if (StringUtils.isBlank(username)) {
                responseCode = "102";
            } else {
                ShopAdmin user = null;
                ShopMember member = this.shopMemberMng.getByUsername(null, username);
                if (member != null) {
                    if (StringUtils.isNotBlank(realname)) {
                        member.setRealName(realname);
                    }
                    if (StringUtils.isNotBlank(tel)) {
                        member.setMobile(tel);
                    }
                    if (StringUtils.isNotBlank(sex)) {
                        member.setGender(Boolean.valueOf(Boolean.parseBoolean(sex)));
                    }

                    ShopMemberGroup group = null;
                    if (StringUtils.isNotBlank(groupId)) {
                        Long gid = Long.valueOf(Long.parseLong(groupId));
                        group = this.shopMemberGroupMng.findById(gid);
                    }
                    if (group == null)
                        group = this.shopMemberGroupMng.findById(Long.valueOf(1L));
                    try {
                        this.shopMemberMng.update(member, group.getId(), null, null, null, null, null, password, email, Boolean.valueOf(false));
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseCode = "105";
                    }
                } else {
                    responseCode = "103";
                }
                user = this.shopAdminMng.getByUsername(username);
                if (user != null)
                    try {
                        Integer[] roleIds = null;
                        if (StringUtils.isNotBlank(role)) {
                            String[] roles = role.split(",");
                            roleIds = new Integer[roles.length];
                            for (int i = 0; i < roles.length; i++) {
                                roleIds[i] = Integer.valueOf(Integer.parseInt(roles[i]));
                            }
                        }
                        if (user != null) {
                            user.setFirstname(realname);
                            Admin a = user.getAdmin();
                            Set roles = new HashSet();
                            if (roleIds != null) {
                                a.getRoles().clear();
                                for (Integer i : roleIds) {
                                    roles.add(this.roleMng.findById(i));
                                }
                                a.setRoles(roles);
                            }
                            user.setAdmin(a);
                            this.shopAdminMng.update(user, password, Boolean.valueOf(false), email, Boolean.valueOf(false));
                        }
                        responseCode = "100";
                        this.webserviceCallRecordMng.save(auth_username, "user_update");
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseCode = "105";
                    }
                else {
                    responseCode = "103";
                }
            }
        }

        return responseCode;
    }

    public String delUser(String auth_username, String auth_password, String username) {
        String responseCode = "101";
        if (validate(auth_username, auth_password)) {
            if (StringUtils.isNotBlank(username)) {
                ShopMember member = this.shopMemberMng.getByUsername(Long.valueOf(1L), username);
                if (member != null) {
                    try {
                        this.shopMemberMng.deleteById(member.getId());
                        responseCode = "100";
                        this.webserviceCallRecordMng.save(auth_username, "user_delete");
                    } catch (Exception e) {
                        responseCode = "106";
                    }
                } else {
                    ShopAdmin user = this.shopAdminMng.getByUsername(username);
                    if (user != null)
                        try {
                            this.shopAdminMng.deleteById(user.getId());
                            responseCode = "100";
                            this.webserviceCallRecordMng.save(auth_username, "user_delete");
                        } catch (Exception e) {
                            responseCode = "106";
                        }
                    else
                        responseCode = "103";
                }
            } else {
                responseCode = "102";
            }
        }
        return responseCode;
    }

    private boolean validate(String username, String password) {
        if ((StringUtils.isBlank(username)) || (StringUtils.isBlank(password))) {
            return false;
        }
        return this.webserviceAuthMng.isPasswordValid(username, password);
    }
}

