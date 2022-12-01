package AbstractClass;

public abstract class TemplateClass {
    public abstract void code();
    public final void getTime(){
        long startTime = System.currentTimeMillis();
        code();
        long endTime = System.currentTimeMillis();
        System.out.println("code执行的时间是:" + (endTime - startTime));
    }
}

class TestTemplate extends TemplateClass{
    @Override
    public void code() {
        int k = 0;
        for (int i = 0; i < 5000; i++) {
            k += 1;
        }
        System.out.println(k);
    }
}
