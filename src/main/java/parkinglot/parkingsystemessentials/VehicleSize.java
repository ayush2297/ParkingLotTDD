package parkinglot.parkingsystemessentials;

import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;

import java.util.ArrayList;
import java.util.Comparator;

public enum VehicleSize {
    SMALL {
        @Override
        public ParkingLot getLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException {
            return driverType.getLot(lotsList);
        }
    }, LARGE {
        @Override
        public ParkingLot getLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException {
            return lotsList.stream()
                    .sorted(Comparator.comparing(lot -> (lot.getParkingCapacity() - lot.getNumberOfVehiclesParked()), Comparator.reverseOrder()))
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    };

    public abstract ParkingLot getLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException;
}
