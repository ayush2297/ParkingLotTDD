package parkinglot.parkingsystemessentials;

import parkinglot.vehicleessentials.DriverType;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;
import parkinglot.vehicleessentials.VehicleSize;

import java.util.Objects;

public class ParkedVehicleDetails {

    private String attendantName;
    private Vehicle vehicle;
    private VehicleSize vehicleSize;
    private DriverType driverType;

    public ParkedVehicleDetails(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public ParkedVehicleDetails(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
        this.vehicle = vehicle;
    }

    public VehicleColor getVehicleColor() {
        return this.vehicle.getVehicleColor();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkedVehicleDetails details = (ParkedVehicleDetails) o;
        return Objects.equals(vehicle, details.vehicle);
    }

}
