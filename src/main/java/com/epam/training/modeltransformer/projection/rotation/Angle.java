package com.epam.training.modeltransformer.projection.rotation;

public class Angle {
    private final double angleX;
    private final double angleY;
    private final double angleZ;

    @Override
    public String toString() {
        return "Angle{" +
                "angleX=" + angleX +
                ", angleY=" + angleY +
                ", angleZ=" + angleZ +
                '}';
    }

    public Angle(double angleX, double angleY, double angleZ) {
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public double getAngleZ() {
        return angleZ;
    }
}
