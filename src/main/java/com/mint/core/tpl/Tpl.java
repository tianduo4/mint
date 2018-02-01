package com.mint.core.tpl;

import java.util.Date;

public abstract interface Tpl {
    public abstract String getName();

    public abstract String getPath();

    public abstract String getFilename();

    public abstract String getSource();

    public abstract long getLastModified();

    public abstract Date getLastModifiedDate();

    public abstract long getLength();

    public abstract int getSize();

    public abstract boolean isDirectory();
}

