package com.mint.common.security.encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Md5PwdEncoder
        implements PwdEncoder {
    private String defaultSalt;

    public String encodePassword(String rawPass) {
        return encodePassword(rawPass, this.defaultSalt);
    }

    public String encodePassword(String rawPass, String salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
        MessageDigest messageDigest = getMessageDigest();
        byte[] digest;
        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        return new String(Hex.encodeHex(digest));
    }

    public boolean isPasswordValid(String encPass, String rawPass) {
        return isPasswordValid(encPass, rawPass, this.defaultSalt);
    }

    public boolean isPasswordValid(String encPass, String rawPass, String salt) {
        if (encPass == null) {
            return false;
        }
        String pass2 = encodePassword(rawPass, salt);
        return encPass.equals(pass2);
    }

    protected final MessageDigest getMessageDigest() {
        String algorithm = "MD5";
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
        }
        throw new IllegalArgumentException("No such algorithm [" +
                algorithm + "]");
    }

    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }
        if ((strict) && (salt != null) && (
                (salt.toString().lastIndexOf("{") != -1) ||
                        (salt.toString().lastIndexOf("}") != -1))) {
            throw new IllegalArgumentException(
                    "Cannot use { or } in salt.toString()");
        }

        if ((salt == null) || ("".equals(salt))) {
            return password;
        }
        return password + "{" + salt.toString() + "}";
    }

    public String getDefaultSalt() {
        return this.defaultSalt;
    }

    public void setDefaultSalt(String defaultSalt) {
        this.defaultSalt = defaultSalt;
    }
}

