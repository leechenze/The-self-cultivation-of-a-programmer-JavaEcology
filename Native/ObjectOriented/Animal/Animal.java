package Animal;

public class Animal {
    String name;
    int eye;
    int legs;

    public void eat(String food) {
        System.out.println("Animal食物为" + food);
    }
    public void move(String moveType) {
        System.out.print("Animal移动方式为" + moveType);
    }
}
