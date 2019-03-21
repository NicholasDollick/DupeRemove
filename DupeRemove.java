import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class DupeRemove {
    public static void main(String [] args) {
        System.out.println("[*] Starting Search");
        new DupeRemove().listFolders(new File("/home/nullbytes/Arduino/test"));
    }

    public void listFolders(File dir) {
        File[] subDir = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        doSomething(dir);

        for (File folder : subDir) {
            listFolders(folder);
        }
    }

    public void listFile(File dir) {
        File[] files = dir.listFiles();
        for (File file : files){
            System.out.println(file.getName());
        }
    }

    public void doSomething(File dir) {
        File[] files = dir.listFiles();
        ArrayList<byte[]> fileHashs = new ArrayList<>();
        for(File file : files) {
            fileHashs.add(getHash(file));
            System.out.println(file.getName());
        }
        System.out.println(fileHashs);
        if(Arrays.equals(fileHashs.get(0), fileHashs.get(1)))
            System.out.println("These files are the same");
        else
            System.out.println("These files are not the same");
    }

    public byte[] getHash(File file) {
        if(!file.isDirectory()) {
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
