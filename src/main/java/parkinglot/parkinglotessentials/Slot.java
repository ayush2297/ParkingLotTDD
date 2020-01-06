package parkinglot.parkinglotessentials;

import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;

import java.time.LocalDateTime;
import java.util.Objects;

public class Slot {

    private ParkedVehicleDetails vehicleDetails;
    private LocalDateTime parkingStartTime;
    private int slotNumber;

    public Slot(ParkedVehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public Slot(int slot, ParkedVehicleDetails vehicle, LocalDateTime parkingStartTime) {
        this.slotNumber = slot;
        this.vehicleDetails = vehicle;
        this.parkingStartTime = parkingStartTime;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public VehicleColor getVehicleColor() {
        return this.vehicleDetails.getVehicleColor();
    }

    public Vehicle getVehicle() { return this.vehicleDetails.getVehicle(); }

    public int getSlotNumber() {
        return this.slotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(vehicleDetails, slot.vehicleDetails);
    }

    public ParkedVehicleDetails getDetails() {
        return this.vehicleDetails;
    }
}
