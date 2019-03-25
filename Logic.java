import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class Logic {
    private static ArrayList<String> detectedCopies;
    private static ArrayList<String> fileHashes;
    private static String destDir = "";

    public static ArrayList<String> run(String toSearchDir, String moveToDir) {
        fileHashes = new ArrayList<>();
        detectedCopies = new ArrayList<>();
        destDir = moveToDir;
        new File(destDir).mkdirs(); // create folder to move dupe files if not exist
        listFolders(new File(toSearchDir));

        return detectedCopies;
    }

    public static void listFolders(File dir) {
        File[] subDir = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        findDupes(dir);

        for (File folder : subDir)
            listFolders(folder);
    }

    public static void findDupes(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
        	// we don't want to include empty parent folders in cleanup
        	if (!file.isDirectory()) {
                String currentHash = Arrays.toString(getHash(file));
                if (fileHashes.contains(currentHash)) {
                    // this will fight back against the ui
                    // it might be easier to return the files[]
                    // and then have the foreach loop happen in the UI thread
                    // this will allow a more graceful update of the UI box?
                    detectedCopies.add(file.getName());
                    moveDupes(file, destDir);
                } else {
                    fileHashes.add(currentHash);
                }
        		
        	}
        }
    }

    public static byte[] getHash(File file) {
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

    // this needs to be updated pretty badly
    public static void moveDupes(File dupeFile, String destDir) {
        // files will only be copied during testing to prevent data loss
        String copyToPath = destDir + "/" + dupeFile.getName();
        File target = new File(copyToPath);

        try {
            Files.copy(dupeFile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Files.move(dupeFile.toPath(), target.toPath(),
            // StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
