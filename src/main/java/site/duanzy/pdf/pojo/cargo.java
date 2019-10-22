package site.duanzy.pdf.pojo;

import com.itextpdf.text.*;

import java.io.Serializable;

public class cargo implements Serializable {
    //车号
    private String carNumber;
    //发货地点
    private String pointOfDeparture;
    //计费标准
    private String chargingStandard;
    //车型
    private String motorcycle;
    //到货地点
    private String placeOfArrive;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public String getChargingStandard() {
        return chargingStandard;
    }

    public void setChargingStandard(String chargingStandard) {
        this.chargingStandard = chargingStandard;
    }

    public String getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(String motorcycle) {
        this.motorcycle = motorcycle;
    }

    public String getPlaceOfArrive() {
        return placeOfArrive;
    }

    public void setPlaceOfArrive(String placeOfArrive) {
        this.placeOfArrive = placeOfArrive;
    }

}
