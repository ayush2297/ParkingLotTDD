import java.util.ArrayList;
import java.util.Comparator;

public enum DriverType {
    NORMAL {
        //************** According to even distribution strategy..
        @Override
        public ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
            return lots.stream()
                    .sorted(Comparator.comparing(lot -> lot.getNumberOfVehiclesParked()))
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    }, HANDICAPPED {
        //************** Offering nearest lot with available slots..
        @Override
        public ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
            return lots.stream()
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    };

    public abstract ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException;

}
