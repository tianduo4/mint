package com.jspgou.common.fck;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private String name;
    private static final Map<String, Command> getCommands = new HashMap(
            3);

    private static final Map<String, Command> postCommands = new HashMap(
            2);

    public static final Command GET_FOLDERS = new Command("GetFolders");

    public static final Command GET_FOLDERS_AND_FILES = new Command(
            "GetFoldersAndFiles");

    public static final Command CREATE_FOLDER = new Command("CreateFolder");

    public static final Command FILE_UPLOAD = new Command("FileUpload");

    public static final Command QUICK_UPLOAD = new Command("QuickUpload");

    static {
        getCommands.put(GET_FOLDERS.getName(), GET_FOLDERS);
        getCommands.put(GET_FOLDERS_AND_FILES.getName(), GET_FOLDERS_AND_FILES);
        getCommands.put(CREATE_FOLDER.getName(), CREATE_FOLDER);

        postCommands.put(FILE_UPLOAD.getName(), FILE_UPLOAD);
        postCommands.put(QUICK_UPLOAD.getName(), QUICK_UPLOAD);
    }

    private Command(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Command valueOf(String name) {
        if (Utils.isEmpty(name)) {
            throw new NullPointerException("Name is null or empty");
        }
        Command command = (Command) getCommands.get(name);
        if (command == null)
            command = (Command) postCommands.get(name);
        if (command == null) {
            throw new IllegalArgumentException("No command const " + name);
        }
        return command;
    }

    public static boolean isValidForGet(String name) {
        return getCommands.containsKey(name);
    }

    public static boolean isValidForPost(String name) {
        return postCommands.containsKey(name);
    }

    public static Command getCommand(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        Command command = (Command) obj;
        return this.name.equals(command.getName());
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }
}

