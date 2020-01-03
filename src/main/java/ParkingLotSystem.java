import java.util.*;

public class ParkingLotSystem {
    private final ArrayList<ParkingLot> lots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.lots = new ArrayList<>(Arrays.asList(parkingLot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.lots.add(parkingLot);
    }

    public int getNumberOfParkingLots() {
        return this.lots.size();
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        List<ParkingLot> tempListOfLots = new ArrayList(this.lots);
        Collections.sort(tempListOfLots, Comparator.comparing(parkingLot -> parkingLot.getNumberOfVehiclesParked()));
        ParkingLot parkingLot = tempListOfLots.get(0);
        parkingLot.parkVehicleInThisLot(vehicle);
    }

    public void UnParkVehicle(Object vehicle) throws ParkingLotException {
        ParkingLot parkingLotOfThisVehicle = this.getParkingLotOInWhichThisVehicleIsParked(vehicle);
        parkingLotOfThisVehicle.unParkFromParkingLot(vehicle);
    }

    public ParkingLot getParkingLotOInWhichThisVehicleIsParked(Object vehicle) throws ParkingLotException {
        return this.lots.stream().filter(parkingLot -> parkingLot.vehicleAlreadyPresent(vehicle)).findFirst()
                .orElseThrow(() -> {
                    return new ParkingLotException
                            ("vehicle not parked in any lot!", ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
                });
    }
}
