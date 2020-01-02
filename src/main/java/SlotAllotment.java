import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlotAllotment {

    public int parkingCapacity;
    Map<Availability, ArrayList> availableParkingSlots;
    private final ObserversInformer observersInformer;

    public SlotAllotment(int parkingCapacity) {
        this.observersInformer = new ObserversInformer();
        this.parkingCapacity = parkingCapacity;
        this.setInitialParkingStatus(parkingCapacity);
    }

    private void setInitialParkingStatus(int parkingCapacity) {
        this.availableParkingSlots = new HashMap<>();
        ArrayList<Object> unoccupiedSlots = new ArrayList<>(parkingCapacity);
        ArrayList<Object> occupiedSlots = new ArrayList<>(parkingCapacity);
        for (Integer i = 1; i <= parkingCapacity; i++) {
            unoccupiedSlots.add(i);
        }
        this.availableParkingSlots.put(Availability.UNOCCUPIED, unoccupiedSlots);
        this.availableParkingSlots.put(Availability.OCCUPIED, occupiedSlots);
    }

    public void parkUpdate(Object vehicle, Integer slot) {
        this.availableParkingSlots.get(Availability.OCCUPIED).add(vehicle);
        this.availableParkingSlots.get(Availability.UNOCCUPIED).remove(slot);
    }

    public void unParkUpdate(Object vehicle) {
        this.availableParkingSlots.get(Availability.UNOCCUPIED)
                .add(this.availableParkingSlots.get(Availability.OCCUPIED).indexOf(vehicle)+1);
        this.availableParkingSlots.get(Availability.OCCUPIED).remove(vehicle);
        Collections.sort(this.availableParkingSlots.get(Availability.UNOCCUPIED));
    }

    public int getNearestParkingSlot() throws ParkingLotException {
        try {
            return (Integer) this.availableParkingSlots.get(Availability.UNOCCUPIED).remove(0);
        } catch (IndexOutOfBoundsException e) {
            this.observersInformer.informThatParkingIsFull();
            throw new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
    }
}
