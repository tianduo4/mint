package com.mint.common.security.rememberme;

import com.mint.common.security.UsernameNotFoundException;
import com.mint.common.security.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;

public class TokenBasedRememberMeServices extends AbstractRememberMeServices {
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response)
            throws DataAccessException, UsernameNotFoundException, InvalidCookieException {
        if (cookieTokens.length != 4) {
            throw new InvalidCookieException("Cookie token did not contain 4 tokens, but contained '" +
                    Arrays.asList(cookieTokens) + "'");
        }
        long tokenExpiryTime;
        Long userId;
        try {
            tokenExpiryTime = new Long(cookieTokens[1]).longValue();
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException(
                    "Cookie token[1] did not contain a valid number (contained '" +
                            cookieTokens[1] + "')");
        }
        if (isTokenExpired(tokenExpiryTime))
            throw new InvalidCookieException(
                    "Cookie token[1] has expired (expired on '" +
                            new Date(tokenExpiryTime) +
                            "'; current time is '" + new Date() + "')");
        try {
            userId = new Long(cookieTokens[3]);
        } catch (NumberFormatException nfe) {

            throw new InvalidCookieException(
                    "Cookie token[3] did not contain a valid number (contained '" +
                            cookieTokens[3] + "')");
        }
        UserDetails user = getUserDetailsService().loadUser(userId,
                cookieTokens[0]);

        String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
                user.getUsername(), user.getPassword(), user.getId());

        if (!expectedTokenSignature.equals(cookieTokens[2])) {
            throw new InvalidCookieException(
                    "Cookie token[2] contained signature '" + cookieTokens[2] +
                            "' but expected '" + expectedTokenSignature + "'");
        }
        return user;
    }

    protected String makeTokenSignature(long tokenExpiryTime, String username, String password, Long id) {
        return DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" +
                password + ":" + getKey() + ":" + id);
    }

    protected boolean isTokenExpired(long tokenExpiryTime) {
        return tokenExpiryTime < System.currentTimeMillis();
    }

    public boolean onLoginSuccess(HttpServletRequest request, HttpServletResponse response, UserDetails user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if ((!StringUtils.hasLength(username)) ||
                (!StringUtils.hasLength(password))) {
            return false;
        }

        int tokenLifetime = calculateLoginLifetime(request, user);
        long expiryTime = System.currentTimeMillis();

        expiryTime += 1000L * (tokenLifetime < 0 ? 1209600 : tokenLifetime);

        String signatureValue = makeTokenSignature(expiryTime, username,
                password, user.getId());

        setCookie(new String[]{username, Long.toString(expiryTime),
                        signatureValue, user.getId().toString()}, tokenLifetime,
                request, response);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Added remember-me cookie for user '" + username +
                    "', expiry: '" + new Date(expiryTime) + "'");
        }
        return true;
    }

    protected int calculateLoginLifetime(HttpServletRequest request, UserDetails user) {
        return getTokenValiditySeconds();
    }
}

