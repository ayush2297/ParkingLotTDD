import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final SlotAllotment slotManager;
    private List parkedVehicles;
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingCapacity) {
        this.parkedVehicles = new ArrayList(parkingCapacity);
        this.slotManager = new SlotAllotment(parkingCapacity);
    }

    public boolean isThisCarPresentInTheParkingLot(Object vehicle) {
        if (this.parkedVehicles.contains(vehicle)) {
            return true;
        }
        return false;
    }

    public void parkTheCar(Object vehicle) throws ParkingLotException {
        if (this.isThisCarPresentInTheParkingLot(vehicle)) {
            throw new ParkingLotException("Car already present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        int slot = this.getSlot();
        this.parkedVehicles.add(slot,vehicle);
        this.slotManager.parkUpdate(vehicle,slot);
    }

    private int getSlot() throws ParkingLotException {
        try {
            return slotManager.getNearestParkingSlot()-1;
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    public void unParkTheCar(Object vehicle) throws ParkingLotException {
        if (!this.isThisCarPresentInTheParkingLot(vehicle)) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkedVehicles.remove(vehicle);
        this.slotManager.unParkUpdate(vehicle);
        return;
    }
}
