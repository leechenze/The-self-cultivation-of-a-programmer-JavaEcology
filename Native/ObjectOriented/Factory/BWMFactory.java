package Factory;

public interface BWMFactory {
    BWM productBWM();
}

class BWM3Factory implements BWMFactory {
    @Override
    public BWM productBWM() {
        System.out.println("生产BWM3系");
        return new BWM3();
    }
}
class BWM5Factory implements BWMFactory {
    @Override
    public BWM productBWM() {
        System.out.println("生产BWM3系");
        return new BWM5();
    }
}

class BWM7Factory implements BWMFactory {
    @Override
    public BWM productBWM() {
        System.out.println("生产BWM7系");
        return new BWM7();
    }
}
