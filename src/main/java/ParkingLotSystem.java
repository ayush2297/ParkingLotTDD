import java.util.*;

public class ParkingLotSystem {
    private final ArrayList<ParkingLot> lots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.lots = new ArrayList<ParkingLot>();
        Arrays.stream(parkingLot).forEach(lot -> this.lots.add(lot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.lots.add(parkingLot);
    }

    public int getNumberOfParkingLots() {
        return this.lots.size();
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        List<ParkingLot> tempListOfLots = new ArrayList(this.lots);
        Comparator<ParkingLot> sortLotsBasedOnSlotsAvailable =
                Comparator.comparing(parkingLot -> parkingLot.getAvailableSlots().size(), Comparator.reverseOrder());
        Collections.sort(tempListOfLots, sortLotsBasedOnSlotsAvailable);
        tempListOfLots.get(0).parkTheCar(vehicle);
    }

    public ParkingLot getParkingLotOInWhichThisVehicleIsParked(Object vehicle) {
        return this.lots.stream().filter(parkingLot -> parkingLot.vehicleAlreadyPresent(vehicle)).findFirst().get();
    }
}
