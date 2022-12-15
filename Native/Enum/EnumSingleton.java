public class EnumSingleton {
    public static void main(String[] args) {

    }
}

enum Season1 {
    SPRING("春天", "春暖花开");


    private final String name;
    private final String desc;

    private Season1 (String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void showInfo() {
        System.out.println(this.name + ": " + this.desc);
    }

}