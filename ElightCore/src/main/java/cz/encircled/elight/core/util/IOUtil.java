package cz.encircled.elight.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Encircled on 25-Dec-14.
 */
public class IOUtil {

    public static List<File> getFilesInFolder(String pathToFolder) {
        return getFilesInFolder(new File(pathToFolder));
    }

    public static List<File> getFilesInFolder(File folder) {
        if (folder == null || folder.isFile()) {
            throw new IllegalArgumentException("Correct folder is expected");
        }
        ArrayList<File> result = new ArrayList<>(16);
        getFilesInFolderInternal(folder, result, null);
        return result;
    }

    public static List<File> getFilesInFolder(String pathToFolder, String fileNamePattern) {
        return getFilesInFolder(new File(pathToFolder), fileNamePattern);
    }

    public static List<File> getFilesInFolder(File folder, String fileNamePattern) {
        if (folder == null || folder.isFile()) {
            throw new IllegalArgumentException("Correct folder is expected");
        }
        ArrayList<File> result = new ArrayList<>(16);
        getFilesInFolderInternal(folder, result, Pattern.compile(fileNamePattern).matcher(""));
        return result;
    }

    private static void getFilesInFolderInternal(File folder, List<File> result, Matcher matcher) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (matcher == null || matcher.reset(file.getName()).matches()) {
                        result.add(file);
                    }
                } else {
                    getFilesInFolderInternal(file, result, matcher);
                }
            }
        }
    }

    public static String getFileNameWithoutType(File file) {
        return getFileNameWithoutType(file.getName());
    }

    public static String getFileNameWithoutType(String fileName) {
        int lastDot = fileName.lastIndexOf(CommonConstants.DOT);
        return lastDot == -1 ? fileName : fileName.substring(0, lastDot);
    }

}
