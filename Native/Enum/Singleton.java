 class Season {
    private final String S_NAME;
    private final String S_DESC;

     private static Season spring = new Season("春天", "艳阳高照");
     private static Season summer = new Season("夏天", "夏日炎炎");
     private static Season autumn = new Season("秋天", "秋高气爽");
     private static Season winter = new Season("冬天", "寒风彻骨");

    private Season(String name, String desc) {
        this.S_NAME = name;
        this.S_DESC = desc;
        System.out.println(this.S_NAME + "," + this.S_DESC);
    }

    public static Season getSpring() {
        return spring;
    }
    public static Season getSummer() {
        return summer;
    }
    public static Season getAutumn() {
        return autumn;
    }
    public static Season getWinter() {
        return winter;
    }

}
