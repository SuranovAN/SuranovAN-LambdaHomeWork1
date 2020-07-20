package IOPS_FILES.Save;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static int i = 2;

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 2, 5, 300);
        GameProgress gameProgress2 = new GameProgress(80, 5, 15, 1200);
        GameProgress gameProgress3 = new GameProgress(95, 3, 20, 2500);

        StringBuilder stringBuilder = new StringBuilder();
        File file = new File("D:\\Games");
        if (file.mkdir()) {
            stringBuilder.append("Папка успешно создана\n");
        } else {
            stringBuilder.append("Не удалось создать папку или она уже создана\n");
        }

        String[] arr = {"src", "res", "savegames", "temp"};
        String[] arr2 = {"main", "test"};
        String[] arr3 = {"Main.java", "Utils.java"};
        String[] arr4 = {"drawables", "vectors", "icons"};

        createFolders(arr, file, "", stringBuilder);
        createFolders(arr2, file, "src\\", stringBuilder);
        createFiles(arr3, file, "src\\main\\", stringBuilder);
        createFolders(arr4, file, "res\\", stringBuilder);

        try {
            stringBuilder.append("temp.txt").append(" ")
                    .append(new File(file.getPath() + "\\" + "temp" + "\\" + "temp.txt").createNewFile())
                    .append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter("D:\\Games\\temp\\temp.txt", false)) {
            fw.write(String.valueOf(stringBuilder));
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "D:\\Games\\savegames\\";
        saveGame(path, gameProgress1);
        saveGame(path, gameProgress2);
        saveGame(path, gameProgress3);
        File saveFiles = new File(path);
        List<String> list = new ArrayList<>();
        for (File f : Objects.requireNonNull(saveFiles.listFiles())) {
            list.add(f.getPath());
        }
        zipFiles(path, list);
    }

    static void createFolders(String[] arr, File file, String folder, StringBuilder stringBuilder) {
        for (String s : arr) {
            stringBuilder.append(s).append(" ")
                    .append(new File(file.getPath() + "\\" + folder + s).mkdir())
                    .append("\n");
        }
    }

    static void createFiles(String[] arr, File file, String folder, StringBuilder stringBuilder) {
        for (String s : arr) {
            try {
                stringBuilder.append(s).append(" ")
                        .append(new File(file.getPath() + "\\" + folder + s).createNewFile())
                        .append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void saveGame(String path, GameProgress gameProgress) {
        File files = new File(path);
        String saveName = "save.dat";
        for (String s : Objects.requireNonNull(files.list())) {
            if (saveName.equals(s)) {
                saveName = "save" + i++ + ".dat";
            }
        }

        try (FileOutputStream fos = new FileOutputStream(path + saveName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void zipFiles(String path, List<String> list) {
        i = 1;
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(path + "zipSaves.zip"))) {
            for (String p : list) {
                System.out.println(p);
                FileInputStream fis = new FileInputStream(p);
                ZipEntry entry = new ZipEntry("packed_save" + i++ + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
            for (File f : Objects.requireNonNull(new File(path).listFiles())) {
                if (!f.getName().contains(".zip") || f.isDirectory()) {
                    f.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
