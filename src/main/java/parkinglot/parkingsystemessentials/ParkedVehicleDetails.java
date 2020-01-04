package parkinglot.parkingsystemessentials;

import parkinglot.vehicleessentials.DriverType;
import parkinglot.vehicleessentials.VehicleSize;

public class ParkedVehicleDetails {

    private final VehicleSize vehicleSize;
    private final DriverType driverType;

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public ParkedVehicleDetails(DriverType driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
    }
}
