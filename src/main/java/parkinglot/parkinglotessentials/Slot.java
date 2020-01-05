package parkinglot.parkinglotessentials;

import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;

import java.time.LocalDateTime;
import java.util.Objects;

public class Slot {

    private ParkedVehicleDetails vehicleDetails;
    private LocalDateTime parkingStartTime;

    public Slot(ParkedVehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public Slot(ParkedVehicleDetails vehicle, LocalDateTime parkingStartTime) {
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

    public String getVehicleMake() {
        return this.vehicleDetails.getVehicle().getMake();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(vehicleDetails, slot.vehicleDetails);
    }
}
