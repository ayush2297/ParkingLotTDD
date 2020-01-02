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

    public void parkTheCar(Object vehicle) throws ParkingLotException {
        if (this.isThisVehiclePresentInTheParkingLot(vehicle) != -1) {
            throw new ParkingLotException("Car already present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        int slot = this.getSlot();
        Slot tempSlot = new Slot(vehicle,this.parkingTimeManager.getCurrentTime());
        this.parkingSlots[slot] = tempSlot;
        this.slotManager.parkUpdate(slot + 1);
    }

    private int getSlot() throws ParkingLotException {
        try {
            return slotManager.getNearestParkingSlot() - 1;
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    public void parkAtFollowingSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        int isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent != -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        Slot tempSlot = new Slot(vehicle,this.parkingTimeManager.getCurrentTime());
        this.parkingSlots[slotNumber] = tempSlot;
        this.slotManager.unParkUpdate(slotNumber + 1);
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

    public List getAvailableSlots() {
        return this.slotManager.availableParkingSlots;
    }

    public LocalDateTime getSlotTimingDetails(int tempSlot) {
        return this.parkingSlots[tempSlot].getParkingStartTime();
    }
}
