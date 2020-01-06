import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkingstrategies.IParkingStrategy;
import parkinglot.parkingstrategies.LargeVehiclesParkingStrategy;
import java.util.ArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LargeVehiclesParkingStrategyTest {


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
        this.strategy = new LargeVehiclesParkingStrategy();
    }

    @Test
    public void givenARequestToGetLot_ShouldReturnLotAccordingToEvenDistributionStrategy() {
        when(lot1.getNumberOfVehiclesParked()).thenReturn(5);
        when(lot1.getParkingCapacity()).thenReturn(10);
        when(lot2.getNumberOfVehiclesParked()).thenReturn(8);
        when(lot2.getParkingCapacity()).thenReturn(25);
        try {
            ParkingLot lot = strategy.getLot(listOfLots);
            Assert.assertEquals(lot2,lot);
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
