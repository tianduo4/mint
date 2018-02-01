package com.mint.common.fck;

import com.mint.common.web.springmvc.MessageResolver;

import javax.servlet.http.HttpServletRequest;

public class LocalizedMessages {
    private static String getMessage(HttpServletRequest request, String key, Object[] args) {
        return MessageResolver.getMessage(request, key, args);
    }

    public static String getCompatibleBrowserYes(HttpServletRequest request) {
        return getMessage(request, "fck.editor.compatibleBrowser.yes", new Object[0]);
    }

    public static String getCompatibleBrowserNo(HttpServletRequest request) {
        return getMessage(request, "fck.editor.compatibleBrowser.no", new Object[0]);
    }

    public static String getFileUploadEnabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.fileUpload.enabled", new Object[0]);
    }

    public static String getFileUploadDisabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.fileUpload.disabled", new Object[0]);
    }

    public static String getFileRenamedWarning(HttpServletRequest request, String newFileName) {
        return getMessage(request,
                "fck.connector.fileUpload.file_renamed_warning", new Object[]{newFileName});
    }

    public static String getInvalidFileTypeSpecified(HttpServletRequest request) {
        return getMessage(request,
                "fck.connector.fileUpload.invalid_file_type_specified", new Object[0]);
    }

    public static String getFileUploadWriteError(HttpServletRequest request) {
        return getMessage(request, "fck.connector.fileUpload.write_error", new Object[0]);
    }

    public static String getGetResourcesEnabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.getResources.enabled", new Object[0]);
    }

    public static String getGetResourcesDisabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.getResources.disabled", new Object[0]);
    }

    public static String getGetResourcesReadError(HttpServletRequest request) {
        return getMessage(request, "fck.connector.getResources.read_error", new Object[0]);
    }

    public static String getCreateFolderEnabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.createFolder.enabled", new Object[0]);
    }

    public static String getCreateFolderDisabled(HttpServletRequest request) {
        return getMessage(request, "fck.connector.createFolder.disabled", new Object[0]);
    }

    public static String getInvalidCommandSpecified(HttpServletRequest request) {
        return getMessage(request, "fck.connector.invalid_command_specified", new Object[0]);
    }

    public static String getFolderAlreadyExistsError(HttpServletRequest request) {
        return getMessage(request,
                "fck.connector.createFolder.folder_already_exists_error", new Object[0]);
    }

    public static String getInvalidNewFolderNameSpecified(HttpServletRequest request) {
        return getMessage(request,
                "fck.connector.createFolder.invalid_new_folder_name_specified", new Object[0]);
    }

    public static String getCreateFolderWriteError(HttpServletRequest request) {
        return getMessage(request, "fck.connector.createFolder.write_error", new Object[0]);
    }

    public static String getInvalidResouceTypeSpecified(HttpServletRequest request) {
        return getMessage(request,
                "fck.connector.invalid_resource_type_specified", new Object[0]);
    }

    public static String getInvalidCurrentFolderSpecified(HttpServletRequest request) {
        return getMessage(request,
                "fck.connector.invalid_current_folder_specified", new Object[0]);
    }
}

