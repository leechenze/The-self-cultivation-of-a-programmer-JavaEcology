package ReflectMethods;

public class Student extends Person implements Study, Move {


    String school;
    public String publicField;
    private String privateField;

    @Override
    public void moveType() {

    }

    @Override
    public void studyInfo() {

    }

    private void test(String name) {

    }
    public String getSchool() {
        return this.school;
    }

}
