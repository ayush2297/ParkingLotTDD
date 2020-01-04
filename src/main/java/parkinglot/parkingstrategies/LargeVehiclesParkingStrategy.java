package parkinglot.parkingstrategies;

import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.vehicleessentials.DriverType;

import java.util.ArrayList;
import java.util.Comparator;

public class LargeVehiclesParkingStrategy implements IParkingStrategy {

    @Override
    public ParkingLot getLot(ArrayList<ParkingLot> lotsList) throws ParkingLotException {
        return lotsList.stream()
                .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                .sorted(Comparator.comparing(lot -> (lot.getParkingCapacity() - lot.getNumberOfVehiclesParked()), Comparator.reverseOrder()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                        ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
    }

}
