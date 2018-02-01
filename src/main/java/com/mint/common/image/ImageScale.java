package com.mint.common.image;

import java.io.File;

public abstract interface ImageScale {
    public abstract void resizeFix(File paramFile1, File paramFile2, int paramInt1, int paramInt2)
            throws Exception;

    public abstract void resizeFix(File paramFile1, File paramFile2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
            throws Exception;
}

