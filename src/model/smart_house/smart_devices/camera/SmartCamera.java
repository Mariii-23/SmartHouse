package model.smart_house.smart_devices.camera;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;
import java.util.Objects;

public class SmartCamera extends SmartDevice implements Serializable {
    private final int width;
    private final int height;
    private final float fileSize;

    public SmartCamera(float fixedConsumption, boolean on, int width, int height, float fileSize) {
        super(fixedConsumption, on);
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
    }

    public SmartCamera(float fixedConsumption, int width, int height, float fileSize) {
        super(fixedConsumption);
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
    }

    public SmartCamera(SmartCamera that) {
        super(that);
        this.width = that.getWidth();
        this.height = that.getHeight();
        this.fileSize = that.getFileSize();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getFileSize() {
        return fileSize;
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fixedConsumption * (1 - 1 / (fileSize * width * height)) : 0;
    }

    @Override
    public String getSimpleName() {
        return "SmartCamera";
    }

    @Override
    public SmartCamera clone() {
        return new SmartCamera(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SmartCamera\n");
        sb.append("Width :: ").append(width).append("\n");
        sb.append("Height  :: ").append(height).append("\n");
        sb.append("File Size  :: ").append(fileSize).append("\n");
        sb.append(super.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartCamera that)) return false;
        if (!super.equals(o)) return false;
        return super.equals(o)
            && width == that.width
            && height == that.height
            && Float.compare(that.fileSize, fileSize) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height, fileSize);
    }
}

