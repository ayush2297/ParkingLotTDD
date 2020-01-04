package parkinglot.observers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObserversInformer {
    public List<ParkingLotObservers> observers;

    public ObserversInformer() {
        this.observers = new ArrayList();
        this.observers.addAll(Arrays.asList(ParkingLotObservers.values()));
    }

    public void informThatParkingIsFull() {
        this.observers.forEach(observer -> observer.isParkingFull = true);;
    }

    public void informThatParkingIsAvailable() {
        this.observers.forEach(observer -> observer.isParkingFull = false);
    }
}
