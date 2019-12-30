import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlotAllotment {

    public int parkingCapacity;
    Map<Availability, ArrayList> parkingAvailabilityStatus;
    private final ObserversInformer observersInformer;

    public SlotAllotment(int parkingCapacity) {
        this.observersInformer = new ObserversInformer();
        this.parkingCapacity = parkingCapacity;
        this.setInitialParkingStatus(parkingCapacity);
    }

    private void setInitialParkingStatus(int parkingCapacity) {
        this.parkingAvailabilityStatus = new HashMap<>();
        ArrayList<Object> unoccupiedSlots = new ArrayList<>(parkingCapacity);
        ArrayList<Object> occupiedSlots = new ArrayList<>(parkingCapacity);
        for (Integer i = 1; i <= parkingCapacity; i++) {
            unoccupiedSlots.add(i);
        }
        this.parkingAvailabilityStatus.put(Availability.UNOCCUPIED, unoccupiedSlots);
        this.parkingAvailabilityStatus.put(Availability.OCCUPIED, occupiedSlots);
    }

    public void parkUpdate(Object vehicle, Integer slot) {
        this.parkingAvailabilityStatus.get(Availability.OCCUPIED).add(vehicle);
        this.parkingAvailabilityStatus.get(Availability.UNOCCUPIED).remove(slot);
    }

    public void unParkUpdate(Object vehicle) {
        this.parkingAvailabilityStatus.get(Availability.UNOCCUPIED)
                .add(this.parkingAvailabilityStatus.get(Availability.OCCUPIED).indexOf(vehicle)+1);
        this.parkingAvailabilityStatus.get(Availability.OCCUPIED).remove(vehicle);
        Collections.sort(this.parkingAvailabilityStatus.get(Availability.UNOCCUPIED));
    }

    public int getNearestParkingSlot() throws ParkingLotException {
        try {
            return (Integer) this.parkingAvailabilityStatus.get(Availability.UNOCCUPIED).remove(0);
        } catch (IndexOutOfBoundsException e) {
            this.observersInformer.informThatParkingIsFull();
            throw new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
    }
}
