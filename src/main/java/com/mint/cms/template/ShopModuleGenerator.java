package com.mint.cms.template;

import com.mint.common.developer.ModuleGenerator;

public class ShopModuleGenerator {
    private static String packName = "com.mint.cms.template";
    private static String fileName = "mint.properties";

    public static void main(String[] args) {
        new ModuleGenerator(packName, fileName).generate();
    }
}

