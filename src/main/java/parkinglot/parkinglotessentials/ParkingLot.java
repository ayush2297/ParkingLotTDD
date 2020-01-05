package parkinglot.parkinglotessentials;

import com.sun.imageio.plugins.wbmp.WBMPImageWriter;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private SlotAllotment slotManager;
    private int parkingCapacity;
    private ParkingTimeManager parkingTimeManager;
    private ArrayList<Slot> parkingSlots;
    private boolean parkingCapacityFull;
    private int numberOfCars = 0;

    public ParkingLot(int parkingCapacity) {
        this.parkingSlots = new ArrayList<Slot>(parkingCapacity);
        this.slotManager = new SlotAllotment(parkingCapacity);
        this.parkingTimeManager = new ParkingTimeManager();
        this.setInitialValuesToSlots(parkingCapacity);
        this.parkingCapacity = parkingCapacity;
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

    public int getParkingCapacity() {
        return parkingCapacity;
    }

    public int getNumberOfVehiclesParked() {
        return this.numberOfCars;
    }

    public LocalDateTime getVehicleTimingDetails(Vehicle vehicle) {
        Slot tempSlot = new Slot(new ParkedVehicleDetails(vehicle));
        return this.parkingSlots.get(this.parkingSlots.indexOf(tempSlot)).getParkingStartTime();
    }

    public void parkVehicleInThisLot(ParkedVehicleDetails vehicleDetails) throws ParkingLotException {
        int slot = this.getSlotToParkVehicle();
        this.parkVehicleAtSpecifiedSlot(slot, vehicleDetails);
    }

    public void parkVehicleAtSpecifiedSlot(int slotNumber, ParkedVehicleDetails vehicleDetails) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicleDetails.getVehicle())) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.partVehicleAtSlot(slotNumber, vehicleDetails);
    }

    private int getSlotToParkVehicle() throws ParkingLotException {
        try {
            return slotManager.getParkingSlot();
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    private void partVehicleAtSlot(int slot, ParkedVehicleDetails vehicleDetails) {
        Slot tempSlot = new Slot(vehicleDetails, this.parkingTimeManager.getCurrentTime());
        this.parkingSlots.set(slot - 1, tempSlot);
        this.slotManager.parkUpdate(slot);
        this.numberOfCars++;
    }

    public void unParkFromParkingLot(Vehicle vehicle) throws ParkingLotException {
        Integer isCarPresent = this.FindSlotOfThisVehicle(vehicle);
        if (isCarPresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots.set(isCarPresent, null);
        this.slotManager.unParkUpdate(isCarPresent + 1);
        this.numberOfCars--;
    }

    public boolean vehicleAlreadyPresent(Vehicle vehicle) {
        int isCarPresent = this.FindSlotOfThisVehicle(vehicle);
        if (isCarPresent == -1) {
            return false;
        }
        return true;
    }

    public int FindSlotOfThisVehicle(Vehicle vehicle) {
        return IntStream.range(0, this.parkingSlots.size())
                .filter(i -> this.parkingSlots.get(i) != null && vehicle.equals(this.parkingSlots.get(i).getVehicle()))
                .findFirst()
                .orElse(-1);
    }

    public void setSlotAllotment(SlotAllotment mockedSlotAllotment) {
        this.slotManager = mockedSlotAllotment;
    }

    public List<Integer> getSlotNumberListOfVehiclesByColor(VehicleColor color) {
        List<Integer> slotsList = new ArrayList<>();
        IntStream.range(0, this.parkingSlots.size()).filter(i -> this.parkingSlots.get(i) != null &&
                this.parkingSlots.get(i).getVehicleColor().equals(color))
                .forEach(i -> slotsList.add(i));
        return slotsList;
    }

    public List<Integer> getSlotNumberListOfVehiclesByMakeAndColor(String make, VehicleColor color) {
        List<Integer> slotsList = new ArrayList<>();
        IntStream.range(0, this.parkingSlots.size())
                .filter(i ->
                this.parkingSlots.get(i).getVehicleMake().equals(make) &&
                this.parkingSlots.get(i).getVehicleColor().equals(color))
                .forEach(i -> slotsList.add(i));
        return slotsList;
    }
}
