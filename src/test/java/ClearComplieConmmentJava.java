import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
public class ClearComplieConmmentJava {
    public static void main(String[] args) throws IOException {
        copy("E:\\mo_ws\\java", "E:\\mo_ws\\java_new"); // 这里写好源文件夹和目的文件夹

        System.out.println("ok");
    }
    private static void copy(String srcPath, String descPath)
            throws IOException {
        copy(new File(srcPath), new File(descPath));
    }
    private static void copy(File srcFile, File descFile) throws IOException {
        if (srcFile.isFile()) { // 文件

            File parent = descFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs(); // 创建文件夹

            }
            if (srcFile.getName().endsWith(".java")) {
                copyJava(srcFile, descFile);
            } else {
                copyFile(srcFile, descFile);
            }
        } else { // 文件夹

            for (File file : srcFile.listFiles()) {
                // 相对路径

                String srcPath = file.getAbsolutePath().substring(
                        srcFile.getAbsolutePath().length());

                copy(file, new File(descFile.getAbsolutePath() + srcPath));
            }
        }
    }
    private static void copyJava(File srcFile, File descFile)
            throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(descFile),"utf-8"));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(srcFile),"utf-8"));
        String line;
        while ((line = br.readLine()) != null) {
            //把最后几行去掉：
            if(line.indexOf("/* Location:           G:") != -1){
                break;
            }
            bw.write(line.replaceFirst(".*/\\*(.*)\\*/", "")); // 注意这里，如果不行，要适当修改
            bw.write("\n");
        }
        br.close();
        bw.close();
    }
    private static void copyFile(File srcFile, File descFile)
            throws IOException {
        OutputStream output = new FileOutputStream(descFile);
        InputStream input = new FileInputStream(srcFile);
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        input.close();
        output.close();
    }
}