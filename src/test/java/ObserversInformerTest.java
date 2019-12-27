import org.junit.Assert;
import org.junit.Test;

public class ObserversInformerTest {

    @Test
    public void givenTaskToInformEveryObserverThatTheParkingIsFull_InformerShouldInformEveryone() {
        ObserversInformer informer = new ObserversInformer();
        informer.informThatParkingIsFull();
        Assert.assertTrue(ParkingLotObservers.OWNER.isParkingFull);
        Assert.assertTrue(ParkingLotObservers.AIRPORT_SECURITY.isParkingFull);
    }

    @Test
    public void givenTaskToInformEveryObserverThatParkingIsAvailable_InformerShouldInformEveryone() {
        ObserversInformer informer = new ObserversInformer();
        informer.informThatParkingIsFull();
        informer.informThatParkingIsAvailable();
        Assert.assertFalse(ParkingLotObservers.OWNER.isParkingFull);
        Assert.assertFalse(ParkingLotObservers.AIRPORT_SECURITY.isParkingFull);
    }
}
