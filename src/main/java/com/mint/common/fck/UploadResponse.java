package com.mint.common.fck;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class UploadResponse {
    protected Object[] parameters;
    public static final int EN_OK = 0;
    public static final int EN_CUSTOM_ERROR = 1;
    public static final int EN_CUSTOM_WARNING = 101;
    public static final int EN_FILE_RENAMED_WARNING = 201;
    public static final int EN_INVALID_FILE_TYPE_ERROR = 202;
    public static final int EN_SECURITY_ERROR = 203;

    public UploadResponse(Object[] arguments) {
        if ((arguments.length < 1) || (arguments.length > 4)) {
            throw new IllegalArgumentException(
                    "The amount of arguments has to be between 1 and 4");
        }
        this.parameters = new Object[arguments.length];

        if (!(arguments[0] instanceof Integer)) {
            throw new IllegalArgumentException(
                    "The first argument has to be an error number (int)");
        }
        System.arraycopy(arguments, 0, this.parameters, 0, arguments.length);
    }

    public void setCustomMessage(String customMassage) {
        if (!StringUtils.isBlank(customMassage)) {
            if (this.parameters.length == 1) {
                Object errorNumber = this.parameters[0];
                this.parameters = new Object[4];
                this.parameters[0] = errorNumber;
                this.parameters[1] = null;
                this.parameters[2] = null;
            }
            this.parameters[3] = customMassage;
        }
    }

    public static UploadResponse getOK(HttpServletRequest request, String fileUrl) {
        String callback = request.getParameter("CKEditorFuncNum");
        return new UploadResponse(new Object[]{Integer.valueOf(Integer.parseInt(callback)), fileUrl});
    }

    public static UploadResponse getFileRenamedWarning(HttpServletRequest request, String fileUrl, String newFileName) {
        return new UploadResponse(new Object[]{Integer.valueOf(201), fileUrl,
                newFileName, LocalizedMessages.getFileRenamedWarning(request,
                newFileName)});
    }

    public static UploadResponse getInvalidFileTypeError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(202),
                LocalizedMessages.getInvalidFileTypeSpecified(request)});
    }

    public static UploadResponse getInvalidCommandError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(1), null, null,
                LocalizedMessages.getInvalidCommandSpecified(request)});
    }

    public static UploadResponse getInvalidResourceTypeError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(1), null, null,
                LocalizedMessages.getInvalidResouceTypeSpecified(request)});
    }

    public static UploadResponse getInvalidCurrentFolderError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(1), null, null,
                LocalizedMessages.getInvalidCurrentFolderSpecified(request)});
    }

    public static UploadResponse getFileUploadDisabledError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(203), null, null,
                LocalizedMessages.getFileUploadDisabled(request)});
    }

    public static UploadResponse getFileUploadWriteError(HttpServletRequest request) {
        return new UploadResponse(new Object[]{Integer.valueOf(1), null, null,
                LocalizedMessages.getFileUploadWriteError(request)});
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(400);
        sb.append("<script type=\"text/javascript\">\n");

        sb
                .append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
        sb.append("window.parent.CKEDITOR.tools.callFunction(");

        for (Object parameter : this.parameters) {
            if ((parameter instanceof Integer)) {
                sb.append(parameter);
            } else {
                sb.append("'");
                if (parameter != null)
                    sb.append(parameter);
                sb.append("'");
            }
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(");\n");
        sb.append("</script>");

        return sb.toString();
    }
}

