package Overload;

public class Overload {
    int num = 0;

    public int add(int x, int y) {
        return x + y;
    }
    public int add(int x, int y, int z){
        return x + y + z;
    }
    public double add(double x, double y) {
        return x + y;
    }
}
