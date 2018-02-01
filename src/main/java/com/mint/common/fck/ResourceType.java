package com.mint.common.fck;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResourceType {
    private String name;
    private String path;
    private Set<String> allowedEextensions;
    private Set<String> deniedExtensions;
    private static Map<String, ResourceType> types = new HashMap(
            4);

    public static final ResourceType FILE = new ResourceType("File",
            PropertiesLoader.getFileResourceTypePath(),
            Utils.getSet(
                    PropertiesLoader.getFileResourceTypeAllowedExtensions()),
            Utils.getSet(
                    PropertiesLoader.getFileResourceTypeDeniedExtensions()));

    public static final ResourceType FLASH = new ResourceType("Flash",
            PropertiesLoader.getFlashResourceTypePath(),
            Utils.getSet(
                    PropertiesLoader.getFlashResourceTypeAllowedExtensions()),
            Utils.getSet(
                    PropertiesLoader.getFlashResourceTypeDeniedExtensions()));

    public static final ResourceType IMAGE = new ResourceType("Image",
            PropertiesLoader.getImageResourceTypePath(),
            Utils.getSet(
                    PropertiesLoader.getImageResourceTypeAllowedExtensions()),
            Utils.getSet(
                    PropertiesLoader.getImageResourceTypeDeniedExtensions()));

    public static final ResourceType MEDIA = new ResourceType("Media",
            PropertiesLoader.getMediaResourceTypePath(),
            Utils.getSet(
                    PropertiesLoader.getMediaResourceTypeAllowedExtensions()),
            Utils.getSet(
                    PropertiesLoader.getMediaResourceTypeDeniedExtensions()));

    static {
        types.put(FILE.getName(), FILE);
        types.put(FLASH.getName(), FLASH);
        types.put(IMAGE.getName(), IMAGE);
        types.put(MEDIA.getName(), MEDIA);
    }

    private ResourceType(String name, String path, Set<String> allowedEextensions, Set<String> deniedExtensions) {
        this.name = name;
        this.path = path;

        if ((allowedEextensions.isEmpty()) && (deniedExtensions.isEmpty())) {
            throw new IllegalArgumentException(
                    "Both sets are empty, one has always to be filled");
        }
        if ((!allowedEextensions.isEmpty()) && (!deniedExtensions.isEmpty())) {
            throw new IllegalArgumentException(
                    "Both sets contain extensions, only one can be filled at the same time");
        }
        this.allowedEextensions = allowedEextensions;
        this.deniedExtensions = deniedExtensions;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public Set<String> getAllowedEextensions() {
        return Collections.unmodifiableSet(this.allowedEextensions);
    }

    public Set<String> getDeniedExtensions() {
        return Collections.unmodifiableSet(this.deniedExtensions);
    }

    public static ResourceType valueOf(String name) {
        if (Utils.isEmpty(name)) {
            throw new NullPointerException("Name is null or empty");
        }
        ResourceType rt = (ResourceType) types.get(name);
        if (rt == null)
            throw new IllegalArgumentException("No resource type const " + name);
        return rt;
    }

    public static boolean isValidType(String name) {
        return types.containsKey(name);
    }

    public static ResourceType getResourceType(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
        }
        return null;
    }

    public static ResourceType getDefaultResourceType(String name) {
        ResourceType rt = getResourceType(name);
        return rt == null ? FILE : rt;
    }

    public boolean isAllowedExtension(String extension) {
        if (Utils.isEmpty(extension))
            return false;
        String ext = extension.toLowerCase();
        if (this.allowedEextensions.isEmpty())
            return !this.deniedExtensions.contains(ext);
        if (this.deniedExtensions.isEmpty())
            return this.allowedEextensions.contains(ext);
        return false;
    }

    @Deprecated
    public boolean isNotAllowedExtension(String extension) {
        return !isAllowedExtension(extension);
    }

    public boolean isDeniedExtension(String extension) {
        return !isAllowedExtension(extension);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        ResourceType rt = (ResourceType) obj;
        return this.name.equals(rt.getName());
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}

