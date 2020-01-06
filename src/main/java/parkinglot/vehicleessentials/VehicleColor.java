package parkinglot.vehicleessentials;

import parkinglot.parkinglotessentials.ISearchableAttributes;
import parkinglot.parkinglotessentials.Slot;

import java.util.function.Predicate;

public enum VehicleColor implements ISearchableAttributes {
    WHITE{
        @Override
        public Predicate<Slot> getFilter() {
            return slot -> slot.getVehicle().getColor().equals(WHITE);
        }
    }, OTHER {
        @Override
        public Predicate<Slot> getFilter() {
            return slot -> slot.getVehicle().getColor().equals(OTHER);
        }
    };

}
