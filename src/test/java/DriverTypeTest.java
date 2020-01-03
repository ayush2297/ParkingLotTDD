import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriverTypeTest {
    private ParkingLot lot1;
    private ParkingLot lot2;
    private ArrayList<ParkingLot> listOfLots;

    @Before
    public void setUp() throws Exception {
        this.lot1 = mock(ParkingLot.class);
        this.lot2 = mock(ParkingLot.class);
        this.listOfLots = new ArrayList<>();
        this.listOfLots.add(this.lot1);
        this.listOfLots.add(this.lot2);
    }

    @Test
    public void givenARequestToGetLotForNormalDriver_ShouldReturnFirstLot() {
        try {
            when(lot1.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkingLot lot = DriverType.NORMAL.getLot(listOfLots);
            Assert.assertEquals(lot1,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestBySecondVehicleToGetLotForNormalDriver_ShouldReturnSecondLot() {
        try {
            when(lot1.getNumberOfVehiclesParked()).thenReturn(1);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkingLot lot = DriverType.NORMAL.getLot(listOfLots);
            Assert.assertEquals(lot2,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestByAVehicleToGetLotForHandicappedDriver_InAnAlreadyOccupiedSystem_ShouldReturnNearestLotWithAvailableSlots() {
        try {
            when(lot1.getNumberOfVehiclesParked()).thenReturn(1);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkingLot lot = DriverType.HANDICAPPED.getLot(listOfLots);
            Assert.assertEquals(lot1,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}
