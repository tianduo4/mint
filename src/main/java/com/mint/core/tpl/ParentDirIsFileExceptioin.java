package com.mint.core.tpl;

public class ParentDirIsFileExceptioin extends RuntimeException {
    private String parentDir;

    public ParentDirIsFileExceptioin(String parentDir) {
        this.parentDir = parentDir;
    }

    public String getMessage() {
        return "parent directory is a file: " + this.parentDir;
    }

    public String getParentDir() {
        return this.parentDir;
    }
}

