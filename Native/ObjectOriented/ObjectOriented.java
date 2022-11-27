import Person.Person;
import Overload.Overload;
import ChangeableParam.ChangeableParam;

public class ObjectOriented {
    public static void main (String[] args) {
        // 方法重载
        System.out.println(new Overload().add(2,3));
        // 方法形参可变
        String[] strArr = new String[]{"2"};
        new ChangeableParam().printInfo1(strArr);
        new ChangeableParam().printInfo1("2");
        new ChangeableParam().printInfo1();



    }
}