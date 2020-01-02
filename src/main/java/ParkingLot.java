import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private final SlotAllotment slotManager;
    private ParkingTimeManager parkingTimeManager;
    private Slot[] parkingSlots;
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingCapacity) {
        this.parkingSlots = new Slot[parkingCapacity];
        this.slotManager = new SlotAllotment(parkingCapacity);
    }

    public void setParkingTimeManager(ParkingTimeManager parkingTimeManager) {
        this.parkingTimeManager = parkingTimeManager;
    }

    public List getAvailableSlots() {
        return this.slotManager.availableParkingSlots;
    }

    public LocalDateTime getSlotTimingDetails(int tempSlot) {
        return this.parkingSlots[tempSlot].getParkingStartTime();
    }

    public void parkTheCar(Object vehicle) throws ParkingLotException {
        this.carAlreadyPresent(vehicle);
        int slot = this.getSlot();
        this.partAtSlot(slot,vehicle);
    }

    public void parkAtFollowingSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        this.carAlreadyPresent(vehicle);
        this.partAtSlot(slotNumber,vehicle);
    }

    private void carAlreadyPresent(Object vehicle) throws ParkingLotException {
        int isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent != -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
    }

    private int getSlot() throws ParkingLotException {
        try {
            return slotManager.getNearestParkingSlot() - 1;
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    private void partAtSlot(int slot, Object vehicle) {
        Slot tempSlot = new Slot(vehicle,this.parkingTimeManager.getCurrentTime());
        this.parkingSlots[slot] = tempSlot;
        this.slotManager.parkUpdate(slot + 1);
    }

    public void unParkTheCar(Object vehicle) throws ParkingLotException {
        Integer isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots[isCarPresent] = null;
        this.slotManager.unParkUpdate(isCarPresent + 1);
        return;
    }

    public int isThisVehiclePresentInTheParkingLot(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return IntStream.range(0, this.parkingSlots.length)
                .filter(i -> tempSlot.equals(this.parkingSlots[i]))
                .findFirst()
                .orElse(-1);
    }
}
