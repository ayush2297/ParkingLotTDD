package parkinglot.vehicleessentials;

import parkinglot.parkinglotessentials.ISearchableAttributes;
import parkinglot.parkinglotessentials.Slot;

import java.util.function.Predicate;

public enum VehicleMake implements ISearchableAttributes {
    BMW {
        @Override
        public Predicate<Slot> getFilter() {
            return slot -> slot.getVehicle().getMake().equals(BMW);
        }
    }, TOYOTA {
        @Override
        public Predicate<Slot> getFilter() {
            return slot -> slot.getVehicle().getMake().equals(TOYOTA);
        }
    }, OTHER {
        @Override
        public Predicate<Slot> getFilter() {
            return slot -> slot.getVehicle().getMake().equals(OTHER);
        }
    }
}
