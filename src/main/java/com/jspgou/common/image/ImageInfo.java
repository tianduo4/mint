/*      */ package com.jspgou.common.image;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class ImageInfo
/*      */ {
/*      */   public static final int FORMAT_JPEG = 0;
/*      */   public static final int FORMAT_GIF = 1;
/*      */   public static final int FORMAT_PNG = 2;
/*      */   public static final int FORMAT_BMP = 3;
/*      */   public static final int FORMAT_PCX = 4;
/*      */   public static final int FORMAT_IFF = 5;
/*      */   public static final int FORMAT_RAS = 6;
/*      */   public static final int FORMAT_PBM = 7;
/*      */   public static final int FORMAT_PGM = 8;
/*      */   public static final int FORMAT_PPM = 9;
/*      */   public static final int FORMAT_PSD = 10;
/*  231 */   private static final String[] FORMAT_NAMES = { "JPEG", "GIF", "PNG", "BMP", "PCX", 
/*  232 */     "IFF", "RAS", "PBM", "PGM", "PPM", 
/*  233 */     "PSD" };
/*      */ 
/*  241 */   private static final String[] MIME_TYPE_STRINGS = { "image/jpeg", "image/gif", "image/png", "image/bmp", "image/pcx", 
/*  242 */     "image/iff", "image/ras", "image/x-portable-bitmap", "image/x-portable-graymap", "image/x-portable-pixmap", 
/*  243 */     "image/psd" };
/*      */   private int width;
/*      */   private int height;
/*      */   private int bitsPerPixel;
/*      */   private boolean progressive;
/*      */   private int format;
/*      */   private InputStream in;
/*      */   private DataInput din;
/*  253 */   private boolean collectComments = true;
/*      */   private Vector comments;
/*      */   private boolean determineNumberOfImages;
/*      */   private int numberOfImages;
/*      */   private int physicalHeightDpi;
/*      */   private int physicalWidthDpi;
/*      */ 
/*      */   private void addComment(String s)
/*      */   {
/*  261 */     if (this.comments == null) {
/*  262 */       this.comments = new Vector();
/*      */     }
/*  264 */     this.comments.addElement(s);
/*      */   }
/*      */ 
/*      */   public boolean check()
/*      */   {
/*  275 */     this.format = -1;
/*  276 */     this.width = -1;
/*  277 */     this.height = -1;
/*  278 */     this.bitsPerPixel = -1;
/*  279 */     this.numberOfImages = 1;
/*  280 */     this.physicalHeightDpi = -1;
/*  281 */     this.physicalWidthDpi = -1;
/*  282 */     this.comments = null;
/*      */     try {
/*  284 */       int b1 = read() & 0xFF;
/*  285 */       int b2 = read() & 0xFF;
/*  286 */       if ((b1 == 71) && (b2 == 73)) {
/*  287 */         return checkGif();
/*      */       }
/*      */ 
/*  290 */       if ((b1 == 137) && (b2 == 80)) {
/*  291 */         return checkPng();
/*      */       }
/*      */ 
/*  294 */       if ((b1 == 255) && (b2 == 216)) {
/*  295 */         return checkJpeg();
/*      */       }
/*      */ 
/*  298 */       if ((b1 == 66) && (b2 == 77)) {
/*  299 */         return checkBmp();
/*      */       }
/*      */ 
/*  302 */       if ((b1 == 10) && (b2 < 6)) {
/*  303 */         return checkPcx();
/*      */       }
/*      */ 
/*  306 */       if ((b1 == 70) && (b2 == 79)) {
/*  307 */         return checkIff();
/*      */       }
/*      */ 
/*  310 */       if ((b1 == 89) && (b2 == 166)) {
/*  311 */         return checkRas();
/*      */       }
/*      */ 
/*  314 */       if ((b1 == 80) && (b2 >= 49) && (b2 <= 54)) {
/*  315 */         return checkPnm(b2 - 48);
/*      */       }
/*      */ 
/*  318 */       if ((b1 == 56) && (b2 == 66)) {
/*  319 */         return checkPsd();
/*      */       }
/*      */ 
/*  322 */       return false;
/*      */     } catch (IOException ioe) {
/*      */     }
/*  325 */     return false;
/*      */   }
/*      */ 
/*      */   private boolean checkBmp() throws IOException
/*      */   {
/*  330 */     byte[] a = new byte[44];
/*  331 */     if (read(a) != a.length) {
/*  332 */       return false;
/*      */     }
/*  334 */     this.width = getIntLittleEndian(a, 16);
/*  335 */     this.height = getIntLittleEndian(a, 20);
/*  336 */     if ((this.width < 1) || (this.height < 1)) {
/*  337 */       return false;
/*      */     }
/*  339 */     this.bitsPerPixel = getShortLittleEndian(a, 26);
/*  340 */     if ((this.bitsPerPixel != 1) && (this.bitsPerPixel != 4) && 
/*  341 */       (this.bitsPerPixel != 8) && (this.bitsPerPixel != 16) && 
/*  342 */       (this.bitsPerPixel != 24) && (this.bitsPerPixel != 32)) {
/*  343 */       return false;
/*      */     }
/*  345 */     int x = (int)(getIntLittleEndian(a, 36) * 0.0254D);
/*  346 */     if (x > 0) {
/*  347 */       setPhysicalWidthDpi(x);
/*      */     }
/*  349 */     int y = (int)(getIntLittleEndian(a, 40) * 0.0254D);
/*  350 */     if (y > 0) {
/*  351 */       setPhysicalHeightDpi(y);
/*      */     }
/*  353 */     this.format = 3;
/*  354 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean checkGif() throws IOException {
/*  358 */     byte[] GIF_MAGIC_87A = { 70, 56, 55, 97 };
/*  359 */     byte[] GIF_MAGIC_89A = { 70, 56, 57, 97 };
/*  360 */     byte[] a = new byte[11];
/*  361 */     if (read(a) != 11) {
/*  362 */       return false;
/*      */     }
/*  364 */     if ((!equals(a, 0, GIF_MAGIC_89A, 0, 4)) && 
/*  365 */       (!equals(a, 0, GIF_MAGIC_87A, 0, 4))) {
/*  366 */       return false;
/*      */     }
/*  368 */     this.format = 1;
/*  369 */     this.width = getShortLittleEndian(a, 4);
/*  370 */     this.height = getShortLittleEndian(a, 6);
/*  371 */     int flags = a[8] & 0xFF;
/*  372 */     this.bitsPerPixel = ((flags >> 4 & 0x7) + 1);
/*      */ 
/*  374 */     if (!this.determineNumberOfImages) {
/*  375 */       return true;
/*      */     }
/*      */ 
/*  378 */     if ((flags & 0x80) != 0) {
/*  379 */       int tableSize = (1 << (flags & 0x7) + 1) * 3;
/*  380 */       skip(tableSize);
/*  382 */     }this.numberOfImages = 0;
/*      */     int blockType;
/*      */     do {
/*  386 */       blockType = read();
/*  387 */       switch (blockType)
/*      */       {
/*      */       case 44:
/*  391 */         if (read(a, 0, 9) != 9) {
/*  392 */           return false;
/*      */         }
/*  394 */         flags = a[8] & 0xFF;
/*  395 */         this.progressive = ((flags & 0x40) != 0);
/*      */ 
/*  399 */         int localBitsPerPixel = (flags & 0x7) + 1;
/*  400 */         if (localBitsPerPixel > this.bitsPerPixel) {
/*  401 */           this.bitsPerPixel = localBitsPerPixel;
/*      */         }
/*  403 */         if ((flags & 0x80) != 0) {
/*  404 */           skip((1 << localBitsPerPixel) * 3);
/*  406 */         }skip(1);
/*      */         int n;
/*      */         do {
/*  410 */           n = read();
/*  411 */           if (n > 0) {
/*  412 */             skip(n);
/*      */           }
/*  415 */           else if (n == -1)
/*  416 */             return false;
/*      */         }
/*  408 */         while (
/*  419 */           n > 0);
/*  420 */         this.numberOfImages += 1;
/*  421 */         break;
/*      */       case 33:
/*  425 */         int extensionType = read();
/*  426 */         if ((this.collectComments) && (extensionType == 254)) { StringBuffer sb = new StringBuffer();
/*      */           int n;
/*      */           do {
/*  431 */             n = read();
/*  432 */             if (n == -1) {
/*  433 */               return false;
/*      */             }
/*  435 */             if (n > 0)
/*  436 */               for (int i = 0; i < n; i++) {
/*  437 */                 int ch = read();
/*  438 */                 if (ch == -1) {
/*  439 */                   return false;
/*      */                 }
/*  441 */                 sb.append((char)ch);
/*      */               }
/*      */           }
/*  429 */           while (
/*  445 */             n > 0);
/*      */         } else {
/*      */           int n;
/*      */           do {
/*  450 */             n = read();
/*  451 */             if (n > 0) {
/*  452 */               skip(n);
/*      */             }
/*  455 */             else if (n == -1)
/*  456 */               return false;
/*      */           }
/*  448 */           while (
/*  459 */             n > 0);
/*      */         }
/*  461 */         break;
/*      */       case 59:
/*  465 */         break;
/*      */       default:
/*  469 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  473 */     while (blockType != 59);
/*  474 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean checkIff() throws IOException {
/*  478 */     byte[] a = new byte[10];
/*      */ 
/*  481 */     if (read(a, 0, 10) != 10) {
/*  482 */       return false;
/*      */     }
/*  484 */     byte[] IFF_RM = { 82, 77 };
/*  485 */     if (!equals(a, 0, IFF_RM, 0, 2)) {
/*  486 */       return false;
/*      */     }
/*  488 */     int type = getIntBigEndian(a, 6);
/*  489 */     if ((type != 1229734477) && 
/*  490 */       (type != 1346522400)) {
/*  491 */       return false;
/*      */     }
/*      */     while (true)
/*      */     {
/*  495 */       if (read(a, 0, 8) != 8) {
/*  496 */         return false;
/*      */       }
/*  498 */       int chunkId = getIntBigEndian(a, 0);
/*  499 */       int size = getIntBigEndian(a, 4);
/*  500 */       if ((size & 0x1) == 1) {
/*  501 */         size++;
/*      */       }
/*  503 */       if (chunkId == 1112361028) {
/*  504 */         if (read(a, 0, 9) != 9) {
/*  505 */           return false;
/*      */         }
/*  507 */         this.format = 5;
/*  508 */         this.width = getShortBigEndian(a, 0);
/*  509 */         this.height = getShortBigEndian(a, 2);
/*  510 */         this.bitsPerPixel = (a[8] & 0xFF);
/*  511 */         return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel < 33);
/*      */       }
/*  513 */       skip(size);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkJpeg() throws IOException
/*      */   {
/*  519 */     byte[] data = new byte[12];
/*      */     while (true) {
/*  521 */       if (read(data, 0, 4) != 4) {
/*  522 */         return false;
/*      */       }
/*  524 */       int marker = getShortBigEndian(data, 0);
/*  525 */       int size = getShortBigEndian(data, 2);
/*  526 */       if ((marker & 0xFF00) != 65280) {
/*  527 */         return false;
/*      */       }
/*  529 */       if (marker == 65504) {
/*  530 */         if (size < 14)
/*      */         {
/*  532 */           skip(size - 2);
/*  533 */           continue;
/*      */         }
/*  535 */         if (read(data, 0, 12) != 12) {
/*  536 */           return false;
/*      */         }
/*  538 */         byte[] APP0_ID = { 74, 70, 73, 70 };
/*  539 */         if (equals(APP0_ID, 0, data, 0, 5))
/*      */         {
/*  541 */           if (data[7] == 1) {
/*  542 */             setPhysicalWidthDpi(getShortBigEndian(data, 8));
/*  543 */             setPhysicalHeightDpi(getShortBigEndian(data, 10));
/*      */           }
/*  546 */           else if (data[7] == 2) {
/*  547 */             int x = getShortBigEndian(data, 8);
/*  548 */             int y = getShortBigEndian(data, 10);
/*  549 */             setPhysicalWidthDpi((int)(x * 2.54F));
/*  550 */             setPhysicalHeightDpi((int)(y * 2.54F));
/*      */           }
/*      */         }
/*  553 */         skip(size - 14);
/*  554 */         continue;
/*      */       }
/*  556 */       if ((this.collectComments) && (size > 2) && (marker == 65534)) {
/*  557 */         size -= 2;
/*  558 */         byte[] chars = new byte[size];
/*  559 */         if (read(chars, 0, size) != size) {
/*  560 */           return false;
/*      */         }
/*  562 */         String comment = new String(chars, "iso-8859-1");
/*  563 */         comment = comment.trim();
/*  564 */         addComment(comment);
/*  565 */         continue;
/*      */       }
/*  567 */       if ((marker >= 65472) && (marker <= 65487) && (marker != 65476) && (marker != 65480)) {
/*  568 */         if (read(data, 0, 6) != 6) {
/*  569 */           return false;
/*      */         }
/*  571 */         this.format = 0;
/*  572 */         this.bitsPerPixel = ((data[0] & 0xFF) * (data[5] & 0xFF));
/*  573 */         this.progressive = ((marker == 65474) || (marker == 65478) || 
/*  574 */           (marker == 65482) || (marker == 65486));
/*  575 */         this.width = getShortBigEndian(data, 3);
/*  576 */         this.height = getShortBigEndian(data, 1);
/*  577 */         return true;
/*      */       }
/*  579 */       skip(size - 2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkPcx() throws IOException
/*      */   {
/*  585 */     byte[] a = new byte[64];
/*  586 */     if (read(a) != a.length) {
/*  587 */       return false;
/*      */     }
/*  589 */     if (a[0] != 1) {
/*  590 */       return false;
/*      */     }
/*      */ 
/*  593 */     int x1 = getShortLittleEndian(a, 2);
/*  594 */     int y1 = getShortLittleEndian(a, 4);
/*  595 */     int x2 = getShortLittleEndian(a, 6);
/*  596 */     int y2 = getShortLittleEndian(a, 8);
/*  597 */     if ((x1 < 0) || (x2 < x1) || (y1 < 0) || (y2 < y1)) {
/*  598 */       return false;
/*      */     }
/*  600 */     this.width = (x2 - x1 + 1);
/*  601 */     this.height = (y2 - y1 + 1);
/*      */ 
/*  603 */     int bits = a[1];
/*  604 */     int planes = a[63];
/*  605 */     if ((planes == 1) && (
/*  606 */       (bits == 1) || (bits == 2) || (bits == 4) || (bits == 8)))
/*      */     {
/*  608 */       this.bitsPerPixel = bits;
/*      */     }
/*  610 */     else if ((planes == 3) && (bits == 8))
/*      */     {
/*  612 */       this.bitsPerPixel = 24;
/*      */     }
/*  614 */     else return false;
/*      */ 
/*  616 */     setPhysicalWidthDpi(getShortLittleEndian(a, 10));
/*  617 */     setPhysicalHeightDpi(getShortLittleEndian(a, 10));
/*  618 */     this.format = 4;
/*  619 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean checkPng() throws IOException {
/*  623 */     byte[] PNG_MAGIC = { 78, 71, 13, 10, 26, 10 };
/*  624 */     byte[] a = new byte[27];
/*  625 */     if (read(a) != 27) {
/*  626 */       return false;
/*      */     }
/*  628 */     if (!equals(a, 0, PNG_MAGIC, 0, 6)) {
/*  629 */       return false;
/*      */     }
/*  631 */     this.format = 2;
/*  632 */     this.width = getIntBigEndian(a, 14);
/*  633 */     this.height = getIntBigEndian(a, 18);
/*  634 */     this.bitsPerPixel = (a[22] & 0xFF);
/*  635 */     int colorType = a[23] & 0xFF;
/*  636 */     if ((colorType == 2) || (colorType == 6)) {
/*  637 */       this.bitsPerPixel *= 3;
/*      */     }
/*  639 */     this.progressive = ((a[26] & 0xFF) != 0);
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean checkPnm(int id) throws IOException {
/*  644 */     if ((id < 1) || (id > 6)) {
/*  645 */       return false; } int[] PNM_FORMATS = { 7, 8, 9 };
/*  648 */     this.format = PNM_FORMATS[((id - 1) % 3)];
/*  649 */     boolean hasPixelResolution = false;
/*      */     String s;
/*      */     while (true) { s = readLine();
/*  654 */       if (s != null) {
/*  655 */         s = s.trim();
/*      */       }
/*  657 */       if ((s == null) || (s.length() < 1)) {
/*      */         continue;
/*      */       }
/*  660 */       if (s.charAt(0) == '#')
/*  661 */         if ((this.collectComments) && (s.length() > 1)) {
/*  662 */           addComment(s.substring(1));
/*      */ 
/*  664 */           continue;
/*      */         }
/*  666 */       if (hasPixelResolution) break;
/*  667 */       int spaceIndex = s.indexOf(' ');
/*  668 */       if (spaceIndex == -1) {
/*  669 */         return false;
/*      */       }
/*  671 */       String widthString = s.substring(0, spaceIndex);
/*  672 */       spaceIndex = s.lastIndexOf(' ');
/*  673 */       if (spaceIndex == -1) {
/*  674 */         return false;
/*      */       }
/*  676 */       String heightString = s.substring(spaceIndex + 1);
/*      */       try {
/*  678 */         this.width = Integer.parseInt(widthString);
/*  679 */         this.height = Integer.parseInt(heightString);
/*      */       } catch (NumberFormatException nfe) {
/*  681 */         return false;
/*      */       }
/*  683 */       if ((this.width < 1) || (this.height < 1)) {
/*  684 */         return false;
/*      */       }
/*  686 */       if (this.format == 7) {
/*  687 */         this.bitsPerPixel = 1;
/*  688 */         return true;
/*      */       }
/*  690 */       hasPixelResolution = true;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  696 */       maxSample = Integer.parseInt(s);
/*      */     }
/*      */     catch (NumberFormatException nfe)
/*      */     {
/*      */       int maxSample;
/*  698 */       return false;
/*      */     }
/*      */     int maxSample;
/*  700 */     if (maxSample < 0) {
/*  701 */       return false;
/*      */     }
/*  703 */     for (int i = 0; i < 25; i++) {
/*  704 */       if (maxSample < 1 << i + 1) {
/*  705 */         this.bitsPerPixel = (i + 1);
/*  706 */         if (this.format == 9) {
/*  707 */           this.bitsPerPixel *= 3;
/*      */         }
/*  709 */         return true;
/*      */       }
/*      */     }
/*  712 */     return false;
/*      */   }
/*      */ 
/*      */   private boolean checkPsd()
/*      */     throws IOException
/*      */   {
/*  718 */     byte[] a = new byte[24];
/*  719 */     if (read(a) != a.length) {
/*  720 */       return false;
/*      */     }
/*  722 */     byte[] PSD_MAGIC = { 80, 83 };
/*  723 */     if (!equals(a, 0, PSD_MAGIC, 0, 2)) {
/*  724 */       return false;
/*      */     }
/*  726 */     this.format = 10;
/*  727 */     this.width = getIntBigEndian(a, 16);
/*  728 */     this.height = getIntBigEndian(a, 12);
/*  729 */     int channels = getShortBigEndian(a, 10);
/*  730 */     int depth = getShortBigEndian(a, 20);
/*  731 */     this.bitsPerPixel = (channels * depth);
/*  732 */     return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel <= 64);
/*      */   }
/*      */ 
/*      */   private boolean checkRas() throws IOException {
/*  736 */     byte[] a = new byte[14];
/*  737 */     if (read(a) != a.length) {
/*  738 */       return false;
/*      */     }
/*  740 */     byte[] RAS_MAGIC = { 106, -107 };
/*  741 */     if (!equals(a, 0, RAS_MAGIC, 0, 2)) {
/*  742 */       return false;
/*      */     }
/*  744 */     this.format = 6;
/*  745 */     this.width = getIntBigEndian(a, 2);
/*  746 */     this.height = getIntBigEndian(a, 6);
/*  747 */     this.bitsPerPixel = getIntBigEndian(a, 10);
/*  748 */     return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel <= 24);
/*      */   }
/*      */ 
/*      */   private static boolean determineVerbosity(String[] args)
/*      */   {
/*  757 */     if ((args != null) && (args.length > 0)) {
/*  758 */       for (int i = 0; i < args.length; i++) {
/*  759 */         if ("-c".equals(args[i])) {
/*  760 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*  764 */     return true;
/*      */   }
/*      */ 
/*      */   private static boolean equals(byte[] a1, int offs1, byte[] a2, int offs2, int num) {
/*  768 */     while (num-- > 0) {
/*  769 */       if (a1[(offs1++)] != a2[(offs2++)]) {
/*  770 */         return false;
/*      */       }
/*      */     }
/*  773 */     return true;
/*      */   }
/*      */ 
/*      */   public int getBitsPerPixel()
/*      */   {
/*  782 */     return this.bitsPerPixel;
/*      */   }
/*      */ 
/*      */   public String getComment(int index)
/*      */   {
/*  793 */     if ((this.comments == null) || (index < 0) || (index >= this.comments.size())) {
/*  794 */       throw new IllegalArgumentException("Not a valid comment index: " + index);
/*      */     }
/*  796 */     return (String)this.comments.elementAt(index);
/*      */   }
/*      */ 
/*      */   public int getFormat()
/*      */   {
/*  806 */     return this.format;
/*      */   }
/*      */ 
/*      */   public String getFormatName()
/*      */   {
/*  815 */     if ((this.format >= 0) && (this.format < FORMAT_NAMES.length)) {
/*  816 */       return FORMAT_NAMES[this.format];
/*      */     }
/*  818 */     return "?";
/*      */   }
/*      */ 
/*      */   public int getHeight()
/*      */   {
/*  828 */     return this.height;
/*      */   }
/*      */ 
/*      */   private static int getIntBigEndian(byte[] a, int offs) {
/*  832 */     return 
/*  833 */       (a[offs] & 0xFF) << 24 | 
/*  834 */       (a[(offs + 1)] & 0xFF) << 16 | 
/*  835 */       (a[(offs + 2)] & 0xFF) << 8 | 
/*  836 */       a[(offs + 3)] & 0xFF;
/*      */   }
/*      */ 
/*      */   private static int getIntLittleEndian(byte[] a, int offs) {
/*  840 */     return 
/*  841 */       (a[(offs + 3)] & 0xFF) << 24 | 
/*  842 */       (a[(offs + 2)] & 0xFF) << 16 | 
/*  843 */       (a[(offs + 1)] & 0xFF) << 8 | 
/*  844 */       a[offs] & 0xFF;
/*      */   }
/*      */ 
/*      */   public String getMimeType()
/*      */   {
/*  853 */     if ((this.format >= 0) && (this.format < MIME_TYPE_STRINGS.length)) {
/*  854 */       if ((this.format == 0) && (this.progressive))
/*      */       {
/*  856 */         return "image/pjpeg";
/*      */       }
/*  858 */       return MIME_TYPE_STRINGS[this.format];
/*      */     }
/*  860 */     return null;
/*      */   }
/*      */ 
/*      */   public int getNumberOfComments()
/*      */   {
/*  874 */     if (this.comments == null) {
/*  875 */       return 0;
/*      */     }
/*  877 */     return this.comments.size();
/*      */   }
/*      */ 
/*      */   public int getNumberOfImages()
/*      */   {
/*  890 */     return this.numberOfImages;
/*      */   }
/*      */ 
/*      */   public int getPhysicalHeightDpi()
/*      */   {
/*  902 */     return this.physicalHeightDpi;
/*      */   }
/*      */ 
/*      */   public float getPhysicalHeightInch()
/*      */   {
/*  914 */     int h = getHeight();
/*  915 */     int ph = getPhysicalHeightDpi();
/*  916 */     if ((h > 0) && (ph > 0)) {
/*  917 */       return h / ph;
/*      */     }
/*  919 */     return -1.0F;
/*      */   }
/*      */ 
/*      */   public int getPhysicalWidthDpi()
/*      */   {
/*  932 */     return this.physicalWidthDpi;
/*      */   }
/*      */ 
/*      */   public float getPhysicalWidthInch()
/*      */   {
/*  944 */     int w = getWidth();
/*  945 */     int pw = getPhysicalWidthDpi();
/*  946 */     if ((w > 0) && (pw > 0)) {
/*  947 */       return w / pw;
/*      */     }
/*  949 */     return -1.0F;
/*      */   }
/*      */ 
/*      */   private static int getShortBigEndian(byte[] a, int offs)
/*      */   {
/*  954 */     return 
/*  955 */       (a[offs] & 0xFF) << 8 | 
/*  956 */       a[(offs + 1)] & 0xFF;
/*      */   }
/*      */ 
/*      */   private static int getShortLittleEndian(byte[] a, int offs) {
/*  960 */     return a[offs] & 0xFF | (a[(offs + 1)] & 0xFF) << 8;
/*      */   }
/*      */ 
/*      */   public int getWidth()
/*      */   {
/*  969 */     return this.width;
/*      */   }
/*      */ 
/*      */   public boolean isProgressive()
/*      */   {
/*  978 */     return this.progressive;
/*      */   }
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*  990 */     ImageInfo imageInfo = new ImageInfo();
/*  991 */     imageInfo.setDetermineImageNumber(true);
/*  992 */     boolean verbose = determineVerbosity(args);
/*  993 */     if (args.length == 0) {
/*  994 */       run(null, System.in, imageInfo, verbose);
/*      */     } else {
/*  996 */       int index = 0;
/*  997 */       while (index < args.length) {
/*  998 */         InputStream in = null;
/*      */         try {
/* 1000 */           String name = args[(index++)];
/* 1001 */           System.out.print(name + ";");
/* 1002 */           if (name.startsWith("http://"))
/* 1003 */             in = new URL(name).openConnection().getInputStream();
/*      */           else {
/* 1005 */             in = new FileInputStream(name);
/*      */           }
/* 1007 */           run(name, in, imageInfo, verbose);
/* 1008 */           in.close();
/*      */         } catch (IOException e) {
/* 1010 */           System.out.println(e);
/*      */           try {
/* 1012 */             if (in != null)
/* 1013 */               in.close();
/*      */           }
/*      */           catch (IOException localIOException1) {
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void print(String sourceName, ImageInfo ii, boolean verbose) {
/* 1023 */     if (verbose)
/* 1024 */       printVerbose(sourceName, ii);
/*      */     else
/* 1026 */       printCompact(sourceName, ii);
/*      */   }
/*      */ 
/*      */   private static void printCompact(String sourceName, ImageInfo imageInfo)
/*      */   {
/* 1031 */     String SEP = "\t";
/* 1032 */     System.out.println(
/* 1033 */       sourceName + "\t" + 
/* 1034 */       imageInfo.getFormatName() + "\t" + 
/* 1035 */       imageInfo.getMimeType() + "\t" + 
/* 1036 */       imageInfo.getWidth() + "\t" + 
/* 1037 */       imageInfo.getHeight() + "\t" + 
/* 1038 */       imageInfo.getBitsPerPixel() + "\t" + 
/* 1039 */       imageInfo.getNumberOfImages() + "\t" + 
/* 1040 */       imageInfo.getPhysicalWidthDpi() + "\t" + 
/* 1041 */       imageInfo.getPhysicalHeightDpi() + "\t" + 
/* 1042 */       imageInfo.getPhysicalWidthInch() + "\t" + 
/* 1043 */       imageInfo.getPhysicalHeightInch() + "\t" + 
/* 1044 */       imageInfo.isProgressive());
/*      */   }
/*      */ 
/*      */   private static void printLine(int indentLevels, String text, float value, float minValidValue)
/*      */   {
/* 1049 */     if (value < minValidValue) {
/* 1050 */       return;
/*      */     }
/* 1052 */     printLine(indentLevels, text, Float.toString(value));
/*      */   }
/*      */ 
/*      */   private static void printLine(int indentLevels, String text, int value, int minValidValue) {
/* 1056 */     if (value >= minValidValue)
/* 1057 */       printLine(indentLevels, text, Integer.toString(value));
/*      */   }
/*      */ 
/*      */   private static void printLine(int indentLevels, String text, String value)
/*      */   {
/* 1062 */     if ((value == null) || (value.length() == 0)) {
/* 1063 */       return;
/*      */     }
/*      */     do
/* 1066 */       System.out.print("\t");
/* 1065 */     while (indentLevels-- > 0);
/*      */ 
/* 1068 */     if ((text != null) && (text.length() > 0)) {
/* 1069 */       System.out.print(text);
/* 1070 */       System.out.print(" ");
/*      */     }
/* 1072 */     System.out.println(value);
/*      */   }
/*      */ 
/*      */   private static void printVerbose(String sourceName, ImageInfo ii) {
/* 1076 */     printLine(0, null, sourceName);
/* 1077 */     printLine(1, "File format: ", ii.getFormatName());
/* 1078 */     printLine(1, "MIME type: ", ii.getMimeType());
/* 1079 */     printLine(1, "Width (pixels): ", ii.getWidth(), 1);
/* 1080 */     printLine(1, "Height (pixels): ", ii.getHeight(), 1);
/* 1081 */     printLine(1, "Bits per pixel: ", ii.getBitsPerPixel(), 1);
/* 1082 */     printLine(1, "Progressive: ", ii.isProgressive() ? "yes" : "no");
/* 1083 */     printLine(1, "Number of images: ", ii.getNumberOfImages(), 1);
/* 1084 */     printLine(1, "Physical width (dpi): ", ii.getPhysicalWidthDpi(), 1);
/* 1085 */     printLine(1, "Physical height (dpi): ", ii.getPhysicalHeightDpi(), 1);
/* 1086 */     printLine(1, "Physical width (inches): ", ii.getPhysicalWidthInch(), 1.0F);
/* 1087 */     printLine(1, "Physical height (inches): ", ii.getPhysicalHeightInch(), 1.0F);
/* 1088 */     int numComments = ii.getNumberOfComments();
/* 1089 */     printLine(1, "Number of textual comments: ", numComments, 1);
/* 1090 */     if (numComments > 0)
/* 1091 */       for (int i = 0; i < numComments; i++)
/* 1092 */         printLine(2, null, ii.getComment(i));
/*      */   }
/*      */ 
/*      */   private int read()
/*      */     throws IOException
/*      */   {
/* 1098 */     if (this.in != null) {
/* 1099 */       return this.in.read();
/*      */     }
/* 1101 */     return this.din.readByte();
/*      */   }
/*      */ 
/*      */   private int read(byte[] a) throws IOException
/*      */   {
/* 1106 */     if (this.in != null) {
/* 1107 */       return this.in.read(a);
/*      */     }
/* 1109 */     this.din.readFully(a);
/* 1110 */     return a.length;
/*      */   }
/*      */ 
/*      */   private int read(byte[] a, int offset, int num) throws IOException
/*      */   {
/* 1115 */     if (this.in != null) {
/* 1116 */       return this.in.read(a, offset, num);
/*      */     }
/* 1118 */     this.din.readFully(a, offset, num);
/* 1119 */     return num;
/*      */   }
/*      */ 
/*      */   private String readLine() throws IOException
/*      */   {
/* 1124 */     return readLine(new StringBuffer());
/*      */   }
/*      */   private String readLine(StringBuffer sb) throws IOException {
/*      */     boolean finished;
/*      */     do {
/* 1130 */       int value = read();
/* 1131 */       finished = (value == -1) || (value == 10);
/* 1132 */       if (!finished)
/* 1133 */         sb.append((char)value);
/*      */     }
/* 1135 */     while (!finished);
/* 1136 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   private static void run(String sourceName, InputStream in, ImageInfo imageInfo, boolean verbose) {
/* 1140 */     imageInfo.setInput(in);
/* 1141 */     imageInfo.setDetermineImageNumber(true);
/* 1142 */     imageInfo.setCollectComments(verbose);
/* 1143 */     if (imageInfo.check())
/* 1144 */       print(sourceName, imageInfo, verbose);
/*      */   }
/*      */ 
/*      */   public void setCollectComments(boolean newValue)
/*      */   {
/* 1158 */     this.collectComments = newValue;
/*      */   }
/*      */ 
/*      */   public void setDetermineImageNumber(boolean newValue)
/*      */   {
/* 1177 */     this.determineNumberOfImages = newValue;
/*      */   }
/*      */ 
/*      */   public void setInput(DataInput dataInput)
/*      */   {
/* 1187 */     this.din = dataInput;
/* 1188 */     this.in = null;
/*      */   }
/*      */ 
/*      */   public void setInput(InputStream inputStream)
/*      */   {
/* 1196 */     this.in = inputStream;
/* 1197 */     this.din = null;
/*      */   }
/*      */ 
/*      */   private void setPhysicalHeightDpi(int newValue) {
/* 1201 */     this.physicalWidthDpi = newValue;
/*      */   }
/*      */ 
/*      */   private void setPhysicalWidthDpi(int newValue) {
/* 1205 */     this.physicalHeightDpi = newValue;
/*      */   }
/*      */ 
/*      */   private void skip(int num) throws IOException {
/* 1209 */     while (num > 0)
/*      */     {
/*      */       long result;
/*      */       long result;
/* 1211 */       if (this.in != null)
/* 1212 */         result = this.in.skip(num);
/*      */       else {
/* 1214 */         result = this.din.skipBytes(num);
/*      */       }
/* 1216 */       if (result > 0L) {
/* 1217 */         num = (int)(num - result);
/*      */       } else {
/* 1219 */         if (this.in != null)
/* 1220 */           result = this.in.read();
/*      */         else {
/* 1222 */           result = this.din.readByte();
/*      */         }
/* 1224 */         if (result == -1L) {
/* 1225 */           throw new IOException("Premature end of input.");
/*      */         }
/* 1227 */         num--;
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.image.ImageInfo
 * JD-Core Version:    0.6.0
 */