import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkingstrategies.EvenDistributionParkingStrategy;
import parkinglot.parkingstrategies.IParkingStrategy;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EvenDistributionParkingStrategyTest {

    private ParkingLot lot1;
    private ParkingLot lot2;
    private ArrayList<ParkingLot> listOfLots;
    private IParkingStrategy strategy;

    @Before
    public void setUp() throws Exception {
        this.lot1 = mock(ParkingLot.class);
        this.lot2 = mock(ParkingLot.class);
        this.listOfLots = new ArrayList<>();
        this.listOfLots.add(lot1);
        this.listOfLots.add(lot2);
        this.strategy = new EvenDistributionParkingStrategy();
    }

    @Test
    public void givenARequestToGetLot_ShouldReturnLotAccordingToEvenDistributionStrategy() {
        when(lot1.getNumberOfVehiclesParked()).thenReturn(5);
        when(lot1.getParkingCapacity()).thenReturn(6);
        when(lot2.getNumberOfVehiclesParked()).thenReturn(8);
        when(lot1.getParkingCapacity()).thenReturn(15);
        try {
            ParkingLot lot = strategy.getLot(listOfLots);
            Assert.assertEquals(lot1,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetLot_WhenAllParkingLotsAreFull_ShouldThrowParkingLotException() {
        when(lot1.getNumberOfVehiclesParked()).thenReturn(5);
        when(lot1.getParkingCapacity()).thenReturn(5);
        when(lot2.getNumberOfVehiclesParked()).thenReturn(3);
        when(lot2.getParkingCapacity()).thenReturn(3);
        try {
            ParkingLot lot = strategy.getLot(listOfLots);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL,e.type);
        }
    }
}
