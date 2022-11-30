package SingletonModel;

public class Sluggard {
    private Sluggard() {

    }
    private static Sluggard sluggard = null;
    public static Sluggard getInstance() {
        if(sluggard == null) {
            sluggard = new Sluggard();
        }
        return sluggard;
    }
}
