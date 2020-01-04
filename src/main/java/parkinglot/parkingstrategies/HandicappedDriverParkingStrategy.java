package parkinglot.parkingstrategies;

import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import java.util.ArrayList;

public class HandicappedDriverParkingStrategy implements IParkingStrategy {
    @Override
    public ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
        return lots.stream()
                .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                        ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
    }
}
