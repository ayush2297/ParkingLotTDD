import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private final SlotAllotment slotManager;
    private ParkingTimeManager parkingTimeManager;
    private ArrayList<Slot> parkingSlots;
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingCapacity) {
        this.parkingSlots = new ArrayList<Slot>(parkingCapacity);
        this.slotManager = new SlotAllotment(parkingCapacity);
        this.setInitialValuesToSlots(parkingCapacity);
    }

    public ParkingLot(int parkingCapacity, ParkingTimeManager timeManager) {
        this.parkingSlots = new ArrayList<Slot>(parkingCapacity);
        this.slotManager = new SlotAllotment(parkingCapacity);
        this.setInitialValuesToSlots(parkingCapacity);
        this.parkingTimeManager = timeManager;
    }

    private void setInitialValuesToSlots(int parkingCapacity) {
        IntStream.range(0, parkingCapacity)
                .forEach(i -> this.parkingSlots.add(i, null));
    }

    public void setParkingTimeManager(ParkingTimeManager parkingTimeManager) {
        this.parkingTimeManager = parkingTimeManager;
    }

    public List getAvailableSlots() {
        return this.slotManager.getAvailableSlotsList();
    }

    public LocalDateTime getVehicleTimingDetails(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return this.parkingSlots.get(this.parkingSlots.indexOf(tempSlot)).getParkingStartTime();
    }

    public void parkTheCar(Object vehicle) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicle)) {
            int slot = this.getSlot();
            this.partAtSlot(slot, vehicle);
            return;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
    }

    public void parkAtFollowingSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicle)) {
            this.partAtSlot(slotNumber, vehicle);
            return;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
    }

    public boolean vehicleAlreadyPresent(Object vehicle) {
        int isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent != -1) {
            return false;
        }
        return true;
    }

    private int getSlot() throws ParkingLotException {
        try {
            return slotManager.getNearestParkingSlot();
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    private void partAtSlot(int slot, Object vehicle) {
        Slot tempSlot = new Slot(vehicle, this.parkingTimeManager.getCurrentTime());
        this.parkingSlots.set(slot - 1, tempSlot);
        this.slotManager.parkUpdate(slot);
    }

    public void unParkTheCar(Object vehicle) throws ParkingLotException {
        Integer isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots.set(isCarPresent, null);
        this.slotManager.unParkUpdate(isCarPresent + 1);
        return;
    }

    public int isThisVehiclePresentInTheParkingLot(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return IntStream.range(0, this.parkingSlots.size())
                .filter(i -> tempSlot.equals(this.parkingSlots.get(i)))
                .findFirst()
                .orElse(-1);
    }
}
