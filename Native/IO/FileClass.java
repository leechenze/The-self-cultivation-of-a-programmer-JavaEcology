import java.io.File;

public class FileClass {
    public static void main(String[] args) {
        File f = new File("./src/test1.txt");
        File f1 = new File("./src", "test1.txt");
        File f2 = new File("/src" + File.separator + "test1.txt");

        System.out.println(f.getName());
        System.out.println(f.getAbsoluteFile());
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getParent());
//        f.renameTo(new File("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/test2.txt"));
        System.out.println(f.exists());

        System.out.println(f.canWrite());
        System.out.println(f.canRead());
        System.out.println(f.lastModified());
        System.out.println(f.length());

        


    }
}
