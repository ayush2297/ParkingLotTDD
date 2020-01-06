package parkinglot.vehicleessentials;

import java.util.Objects;

public class Vehicle {

    private final String plateNum;
    private final VehicleMake make;
    private final VehicleColor vehicleColor;

    public Vehicle(String plateNum, VehicleMake make, VehicleColor vehicleColor) {
        this.plateNum = plateNum;
        this.make = make;
        this.vehicleColor = vehicleColor;
    }

    public VehicleMake getMake() {
        return make;
    }

    public VehicleColor getColor() {
        return vehicleColor;
    }

    public String getNumberPlate() {
        return this.plateNum;
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
