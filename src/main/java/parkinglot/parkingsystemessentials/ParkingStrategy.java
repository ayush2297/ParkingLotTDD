package parkinglot.parkingsystemessentials;

import parkinglot.parkingstrategies.EvenDistributionParkingStrategy;
import parkinglot.parkingstrategies.HandicappedDriverParkingStrategy;
import parkinglot.parkingstrategies.IParkingStrategy;
import parkinglot.parkingstrategies.LargeVehiclesParkingStrategy;

public enum ParkingStrategy {
    NORMAL {
        @Override
        public IParkingStrategy getParkingStrategy() {
            return new EvenDistributionParkingStrategy();
        }
    }, HANDICAP {
        @Override
        public IParkingStrategy getParkingStrategy() {
            return new HandicappedDriverParkingStrategy();
        }
    }, LARGE_VEHICLE {
        @Override
        public IParkingStrategy getParkingStrategy() {
            return new LargeVehiclesParkingStrategy();
        }
    };

    public abstract IParkingStrategy getParkingStrategy();
}
