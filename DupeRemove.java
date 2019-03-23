import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class DupeRemove {
    private static ArrayList<String> detectedCopies;

    private static ArrayList<String> fileHashes;

    public static void main(String[] args) {
        fileHashes = new ArrayList<String>();
        detectedCopies = new ArrayList<String>();
        System.out.println("[*] Starting Search");
        new DupeRemove().listFolders(new File("/home/nullbytes/Downloads/test"));

        if (detectedCopies.size() > 0) {
            System.out.println(detectedCopies.size());
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

        doSomething(dir); // this is a really stupid name, needs to be fixed

        for (File folder : subDir) {
            listFolders(folder);
        }
    }

    public void listFile(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            // System.out.println(file.getName());
        }
    }

    public void doSomething(File dir) {
        File[] files = dir.listFiles();
        // ArrayList<byte[]> fileHashs = new ArrayList<>();
        for (File file : files) {
            String currentHash = Arrays.toString(getHash(file));
            // fileHashs.add(getHash(file));
            // System.out.println(file.getName());
            // System.out.println(Arrays.hashCode(currentHash)); // int
            // System.out.println((Arrays.toString(currentHash))); // string
            // System.out.println();
            if (fileHashes.contains(currentHash)) {
                System.out.println(currentHash.toString());
                detectedCopies.add(file.getName());
            } else {
                fileHashes.add(currentHash);
            }
        }
        // System.out.println(fileHashes.size());
        /*
         * if (Arrays.equals(fileHashs.get(0), fileHashs.get(1))) // this is assuming we
         * only compare two files System.out.println("These files are the same"); else
         * System.out.println("These files are not the same");
         */
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
}
