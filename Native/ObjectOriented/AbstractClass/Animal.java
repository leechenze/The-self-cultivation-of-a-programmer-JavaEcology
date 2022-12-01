package AbstractClass;

public abstract class Animal {
    public abstract void move();
}

class Dog extends Animal{

    @Override
    public void move() {
        System.out.println("修狗的移动方式是走");
    }
}

class Fish extends Animal{
    @Override
    public void move(){
        System.out.println("鱼的移动方式是游");
    }
}

class Bird extends Animal{
    @Override
    public void move() {
        System.out.println("鸟的移动方式是飞行");
    }
}