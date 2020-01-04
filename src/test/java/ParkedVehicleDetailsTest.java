import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.DriverType;
import parkinglot.vehicleessentials.VehicleSize;

public class ParkedVehicleDetailsTest {
    private ParkedVehicleDetails details;

    @Before
    public void setUp() throws Exception {
        this.details = new ParkedVehicleDetails(DriverType.NORMAL, VehicleSize.SMALL);
    }

    @Test
    public void givenARequestToGetDriverType_ShouldReturnDriverType() {
        DriverType driverType = details.getDriverType();
        Assert.assertEquals(DriverType.NORMAL,driverType);
    }

    @Test
    public void givenARequestToGetVehicleSize_ShouldReturnDriverType() {
        VehicleSize vehicleSize = details.getVehicleSize();
        Assert.assertEquals(VehicleSize.SMALL,vehicleSize);
    }
}
