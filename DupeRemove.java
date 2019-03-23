import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.*;

public class DupeRemove {
    private static ArrayList<String> detectedCopies;

    private static ArrayList<String> fileHashes;

    private static String dupeDir = "/home/nullbytes/DupesHere";

    public static void main(String[] args) {
        fileHashes = new ArrayList<String>();
        detectedCopies = new ArrayList<String>();
        System.out.println("[*] Starting Search");
        new File(dupeDir).mkdirs(); // this should prompt
        new DupeRemove().listFolders(new File("/home/nullbytes/Downloads/test")); // this should also prompt

        if (detectedCopies.size() > 0) {
            System.out.printf("[*] Found %d duplicate files: \n", detectedCopies.size());
            for (String name : detectedCopies)
                System.out.println(name);
        }
        System.out.println("[+] Done");
    }

    public void listFolders(File dir) {
        File[] subDir = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        findDupes(dir);

        for (File folder : subDir) {
            listFolders(folder);
        }
    }

    // currently unsued function
    public void listFile(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    public void findDupes(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            String currentHash = Arrays.toString(getHash(file));
            if (fileHashes.contains(currentHash)) {
                // if file match is found, this executes
                detectedCopies.add(file.getName());
                moveDupes(file);
            } else {
                fileHashes.add(currentHash);
            }
        }
    }

    public byte[] getHash(File file) {
        if (!file.isDirectory()) {
            try {
                byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(b);
                return hash;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[4];
    }

    public void moveDupes(File dupeFile) {
        // files will only be copied during testing to prevent data loss
        String copyToPath = dupeDir + "/" + dupeFile.getName();
        File target = new File(copyToPath);
        try {
            Files.copy(dupeFile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
