import java.util.ArrayList;
import java.util.List;

public class GenericWildcard {
    public static void main(String[] args) {
        Dd d = new Dd();
        List<String> l1 = new ArrayList<String>();
        d.test(l1);
        List<Integer> l2 = new ArrayList<Integer>();
        d.test(l2);
        // List<? extends Person>
        List<C1> lc = new ArrayList<C1>();
        d.test1(lc);
        List<D1> ld = new ArrayList<D1>();
        d.test1(ld);
        // List<? super Person>
        List<A1> la = new ArrayList<A1>();
        d.test2(la);
        List<B3> lb = new ArrayList<B3>();
        d.test2(lb);
        // List<? extends Interface>
        List<IAImpl> lia = new ArrayList<IAImpl>();
        d.test3(lia); // 不可以因为lia是IAImpl的实现类
        // d.test3(la); // 不可以因为la不是IAImpl的实现类



    }
}
class Dd {
    public void test(List<?> lsit) {}
    public void test1(List<? extends C1> lsit) {
        // list的元素数据类型是C1及其子类
    }
    public void test2(List<? super C1> list) {
        // list的元素数据类型是C1及其父类
    }
    public void test3(List<? extends IA> list) {
        // list的元素数据类型是IA的实现类
    }
}

/**
 * 有限制的通配符
 */
class A1 {

}
class B3 extends A1 {

}

class C1 extends B3 {

}

class D1 extends C1 {

}
/**
 * 定义接口
 */
interface IA{}

class IAImpl implements IA {

}