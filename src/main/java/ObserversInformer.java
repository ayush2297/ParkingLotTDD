import java.util.ArrayList;
import java.util.List;

public class ObserversInformer {
    public List<ParkingLotObservers> observers;

    public ObserversInformer() {
        this.observers = new ArrayList();
        for (ParkingLotObservers observer: ParkingLotObservers.values()) {
            this.observers.add(observer);
        }
    }

    public void informThatParkingIsFull() {
        this.observers.forEach(observer -> observer.isParkingFull = true);;
    }

    public void informThatParkingIsAvailable() {
        this.observers.forEach(observer -> observer.isParkingFull = false);
    }
}
