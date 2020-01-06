import org.junit.Assert;
import org.junit.Test;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;
import parkinglot.vehicleessentials.VehicleMake;

import java.util.ArrayList;

public class VehicleTest {

    @Test
    public void givenTwoVehicles_IWhenHaveSameDetails_ShouldReturnEqual() {
        Vehicle vehicle1 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.WHITE);
        Vehicle vehicle2 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.WHITE);
        boolean vehiclesEqual = vehicle1.equals(vehicle2);
        Assert.assertTrue(vehiclesEqual);
    }

    @Test
    public void givenTwoVehicles_IWhenHaveDifferentDetails_ShouldReturnNotEqual() {
        Vehicle vehicle1 = new Vehicle("A2", VehicleMake.BMW, VehicleColor.WHITE);
        Vehicle vehicle2 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.WHITE);
        boolean vehiclesEqual = vehicle1.equals(vehicle2);
        Assert.assertFalse(vehiclesEqual);
    }

    @Test
    public void givenOneVehicle_WhenComparedToItself_ShouldReturnEqual() {
        Vehicle vehicle1 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.WHITE);
        boolean vehiclesEqual = vehicle1.equals(vehicle1);
        Assert.assertTrue(vehiclesEqual);
    }

    @Test
    public void givenOneVehicle_WhenComparedSomeOtherClassObject_ShouldReturnNotEqual() {
        Vehicle vehicle1 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.WHITE);
        boolean vehiclesEqual = vehicle1.equals(new ArrayList<>());
        Assert.assertFalse(vehiclesEqual);
    }
}
