import org.junit.Test;

import java.util.Optional;

public class OptionalAdvanced {
    public static void main(String[] args) {

    }

    @Test
    public void orElse() {

        Optional<String> op1 = Optional.of("douglas");
        Optional<Object> op2 = Optional.ofNullable(null);
        Optional<Object> op3 = Optional.empty();

        String name1 = op1.orElse("hilary");
        String name2 = (String) op2.orElse("hilary");
        System.out.println("name1 = " + name1);
        System.out.println("name2 = " + name2);

    }

    @Test
    public void ifPresent() {
        Optional<String> op1 = Optional.of("douglas");
        Optional<Object> op2 = Optional.empty();
        op1.ifPresent(System.out::println);
        op2.ifPresent(System.out::println);
        op2.ifPresentOrElse(System.out::println, () -> System.out.println("没有值"));
    }

    @Test
    public void map() {
        Person person = new Person("Abraham", 20);

        String upperPersonName = getUpperPersonName(person);
        System.out.println("upperPersonName = " + upperPersonName);

        String upperPersonNameOptional = getUpperPersonNameOptional(person);
        System.out.println("upperPersonNameOptional = " + upperPersonNameOptional);

    }

    /**
     * 需求：
     * 将User中的用户名转换成大写并返回
     *
     * @param person
     * @return
     */
    // 使用传统方式实现需求
    public String getUpperPersonName(Person person) {
        if (person != null) {
            String name = person.getName();
            if (name != null) {
                return name.toUpperCase();
            } else {
                return null;
            }
        }
        return null;
    }

    // 使用Optional方式实现需求
    public String getUpperPersonNameOptional(Person person) {
        Optional<Person> personOpt = Optional.of(person);
        String upperName = personOpt.map(Person::getName).map(String::toUpperCase).orElse("null");
        return upperName;
    }

}











