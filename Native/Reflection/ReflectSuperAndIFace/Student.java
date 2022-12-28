package ReflectSuperAndIFace;

public class Student extends Person implements Move, Study {
    String school;
    public void showInfo() {
        System.out.println("school is" + this.school);
    }

    @Override
    public void moveType() {
        System.out.println("Go to school by bicycle");
    }

    @Override
    public void studyInfo() {
        System.out.println("Learning java");
    }
}
