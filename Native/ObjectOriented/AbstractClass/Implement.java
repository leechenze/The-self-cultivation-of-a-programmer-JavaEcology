package AbstractClass;

public class Implement {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.move();
        Bird bird = new Bird();
        bird.move();
        Fish fish = new Fish();
        fish.move();

        // 模版设计模式测试
        TemplateClass test = new TestTemplate();
        test.getTime();
    }
}
