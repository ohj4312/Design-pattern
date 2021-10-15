package ch03_solid.ocp.exam2.sol;

public class TimeProviderTest {
    public static void main(String[] args){
        TimeReminder sut=new TimeReminder();
        TimeProvider tProvStub = new FakeTimeProvider();
        tProvStub.setHours(18);
        sut.setTimeProvider(tProvStub);
    }
}
