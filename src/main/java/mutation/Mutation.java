package mutation;

import base.Tour;

// do not modify
public abstract class Mutation {
    public abstract void doMutation(Tour tour);

    public String toString() {
        return getClass().getSimpleName();
    }
}