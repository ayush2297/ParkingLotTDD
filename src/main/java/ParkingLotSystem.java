import java.util.ArrayList;
import java.util.Arrays;

public class ParkingLotSystem {
    private final ArrayList<ParkingLot> lots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.lots = new ArrayList<ParkingLot>();
        Arrays.asList(parkingLot).stream().forEach(lot -> this.lots.add(lot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.lots.add(parkingLot);
    }

    public int getNumberOfParkingLots() {
        return this.lots.size();
    }
}
