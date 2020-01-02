import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    public final SlotAllotment slotManager;
    private Object[] parkedVehicles;
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingCapacity) {
        this.parkedVehicles = new Object[parkingCapacity];
        this.slotManager = new SlotAllotment(parkingCapacity);
    }

    public int isThisVehiclePresentInTheParkingLot(Object vehicle) {
        return IntStream.range(0, this.parkedVehicles.length)
                .filter(i -> vehicle.equals(this.parkedVehicles[i])).findFirst().orElse(-1);
    }

    public void parkTheCar(Object vehicle) throws ParkingLotException {
        if (this.isThisVehiclePresentInTheParkingLot(vehicle) != -1) {
            throw new ParkingLotException("Car already present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        int slot = this.getSlot();
        this.parkedVehicles[slot] = vehicle;
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

    public void unParkTheCar(Object vehicle) throws ParkingLotException {
        Integer isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkedVehicles[isCarPresent] = null;
        this.slotManager.unParkUpdate(isCarPresent + 1);
        return;
    }

    public List getAvailableSlots() {
        return this.slotManager.availableParkingSlots;
    }

    public void parkFollowingSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        int isCarPresent = this.isThisVehiclePresentInTheParkingLot(vehicle);
        if (isCarPresent != -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.parkedVehicles[slotNumber] = vehicle;
        this.slotManager.unParkUpdate(slotNumber + 1);
        return;
    }
}
