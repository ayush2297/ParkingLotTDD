package parkinglot.vehicleessentials;

import java.util.Objects;

public class Vehicle {

    private final String plateNum;
    private final String make;
    private final VehicleColor vehicleColor;

    public Vehicle(String plateNum, String make, VehicleColor vehicleColor) {
        this.plateNum = plateNum;
        this.make = make;
        this.vehicleColor = vehicleColor;
    }

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(plateNum, vehicle.plateNum) &&
                Objects.equals(make, vehicle.make) &&
                vehicleColor == vehicle.vehicleColor;
    }

}
