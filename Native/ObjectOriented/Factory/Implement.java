package Factory;

public class Implement {
    public static void main(String[] args){
        BWM b3 = new BWM3Factory().productBWM();
        b3.showInfo();
        BWM b5 = new BWM5Factory().productBWM();
        b5.showInfo();
        BWM b7 = new BWM7Factory().productBWM();
        b7.showInfo();

    }
}
