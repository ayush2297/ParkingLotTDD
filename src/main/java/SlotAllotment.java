import java.util.ArrayList;
import java.util.Collections;

public class SlotAllotment {

    public int parkingCapacity;
    ArrayList<Integer> availableParkingSlots;
    private final ObserversInformer observersInformer;

    public SlotAllotment(int parkingCapacity) {
        this.observersInformer = new ObserversInformer();
        this.parkingCapacity = parkingCapacity;
        this.setInitialParkingStatus(parkingCapacity);
    }

    private void setInitialParkingStatus(int parkingCapacity) {
        this.availableParkingSlots = new ArrayList<>();
        for (Integer i = 1; i <= parkingCapacity; i++) {
            this.availableParkingSlots.add(i);
        }
    }

    public void parkUpdate(Integer slot) {
        this.availableParkingSlots.remove(slot);
    }

    public void unParkUpdate(Integer slot) {
        this.availableParkingSlots.add(slot);
        Collections.sort(this.availableParkingSlots);
    }

    public int getNearestParkingSlot() throws ParkingLotException {
        try {
            return this.availableParkingSlots.remove(0);
        } catch (IndexOutOfBoundsException e) {
            this.observersInformer.informThatParkingIsFull();
            throw new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
    }
}
