import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SlotAllotment {

    private int parkingCapacity;
    private ArrayList<Integer> availableParkingSlots;
    private final ObserversInformer observersInformer;

    public SlotAllotment(int parkingCapacity) {
        this.observersInformer = new ObserversInformer();
        this.parkingCapacity = parkingCapacity;
        this.setInitialParkingStatus(parkingCapacity);
    }

    private void setInitialParkingStatus(int parkingCapacity) {
        this.availableParkingSlots = new ArrayList<>();
        IntStream.range(1, parkingCapacity + 1)
                .forEach(i -> this.availableParkingSlots.add(i));
    }

    public void parkUpdate(Integer slot) {
        this.availableParkingSlots.remove((Integer) slot);
    }

    public void unParkUpdate(Integer slot) {
        this.availableParkingSlots.add((Integer) slot);
        Collections.sort(this.availableParkingSlots);
    }

    public int getParkingSlot() throws ParkingLotException {
        try {
            return this.availableParkingSlots.remove(0);
        } catch (IndexOutOfBoundsException e) {
            this.observersInformer.informThatParkingIsFull();
            throw new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
    }

    public List getAvailableSlotsList() {
        return this.availableParkingSlots;
    }
}
