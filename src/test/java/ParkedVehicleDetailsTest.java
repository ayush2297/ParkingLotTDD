import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.DriverType;
import parkinglot.vehicleessentials.VehicleColor;
import parkinglot.vehicleessentials.VehicleSize;

public class ParkedVehicleDetailsTest {
    private ParkedVehicleDetails details;
    private Object vehicle;

    @Before
    public void setUp() throws Exception {
        this.vehicle = new Object();
        this.details = new ParkedVehicleDetails(vehicle,DriverType.NORMAL, VehicleSize.SMALL,VehicleColor.WHITE);
    }

    @Test
    public void givenARequestToGetDriverType_ShouldReturnDriverType() {
        DriverType driverType = details.getDriverType();
        Assert.assertEquals(DriverType.NORMAL,driverType);
    }

    @Test
    public void givenARequestToGetVehicleSize_ShouldReturnVehicleSize() {
        VehicleSize vehicleSize = details.getVehicleSize();
        Assert.assertEquals(VehicleSize.SMALL,vehicleSize);
    }

    @Test
    public void givenARequestToGetVehicleColour_ShouldReturnVehicleColor() {
        VehicleColor vehicleColor = details.getVehicleColor();
        Assert.assertEquals(VehicleColor.WHITE,vehicleColor);
    }

}
