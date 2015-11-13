package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.io.formatting.Output;
import com.leidos.dataparser.io.formatting.OutputData;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class VehStatusData implements Serializable, OutputData {
    private int bsmId;
    @Output
    private int vehStatusLights;
    @Output
    private int vehStatusLightBar;
    @Output
    private int wiperStatFront;
    @Output
    private int wiperRateFront;
    @Output
    private int wiperStatRear;
    @Output
    private int wiperRateRear;
    @Output
    private String brakes;
    @Output
    private int breakPressure;
    @Output
    private int coefficientOfFriction;
    @Output
    private int sunSensor;
    @Output
    private int rainSensor;
    @Output
    private int airTemp;
    @Output
    private int airPressure;
    @Output
    private double steeringWheelAngle;
    @Output
    private int steeringWheelAngleConf;
    @Output
    private int steeringWheelAngleRateOfChange;
    @Output
    private int drivingWheelAngle;
    @Output
    private int vehStatusThrottle;
    @Output
    private int vehStatusHeight;
    @Output
    private int vehStatusBumperFront;
    @Output
    private int vehStatusBumperRear;
    @Output
    private int vehStatusMass;
    @Output
    private int vehStatusTrailerWeight;
    @Output
    private int vehStatusType;
    @Output
    private Integer initPosYear;
    @Output
    private Integer initPosMonth;
    @Output
    private Integer initPosDay;
    @Output
    private Integer initPosHour;
    @Output
    private Integer initPosMinute;
    @Output
    private Integer initPosSecond;
    @Output
    private Double initPosLat;
    @Output
    private Double initPosLong;
    @Output
    private Double elevation;
    @Output
    private Double heading;
    @Output
    private Integer trans;
    @Output
    private Double speed;
    @Output
    private String posAccuracy;
    @Output
    private String timeConfidence;
    @Output
    private String posConfidence;
    @Output
    private String speedConfidence;
    @Output
    private int v2vHeight;
    @Output
    private int v2vBumperFront;
    @Output
    private int v2vBumperRear;
    @Output
    private int v2vMass;
    @Output
    private int v2vType;

    public int getBsmId() {
        return bsmId;
    }

    public void setBsmId(int bsmId) {
        this.bsmId = bsmId;
    }

    public int getVehStatusLights() {
        return vehStatusLights;
    }

    public void setVehStatusLights(int vehStatusLights) {
        this.vehStatusLights = vehStatusLights;
    }

    public int getVehStatusLightBar() {
        return vehStatusLightBar;
    }

    public void setVehStatusLightBar(int vehStatusLightBar) {
        this.vehStatusLightBar = vehStatusLightBar;
    }

    public int getWiperStatFront() {
        return wiperStatFront;
    }

    public void setWiperStatFront(int wiperStatFront) {
        this.wiperStatFront = wiperStatFront;
    }

    public int getWiperRateFront() {
        return wiperRateFront;
    }

    public void setWiperRateFront(int wiperRateFront) {
        this.wiperRateFront = wiperRateFront;
    }

    public int getWiperStatRear() {
        return wiperStatRear;
    }

    public void setWiperStatRear(int wiperStatRear) {
        this.wiperStatRear = wiperStatRear;
    }

    public int getWiperRateRear() {
        return wiperRateRear;
    }

    public void setWiperRateRear(int wiperRateRear) {
        this.wiperRateRear = wiperRateRear;
    }

    public String getBrakes() {
        return brakes;
    }

    public void setBrakes(String brakes) {
        this.brakes = brakes;
    }

    public int getBreakPressure() {
        return breakPressure;
    }

    public void setBreakPressure(int breakPressure) {
        this.breakPressure = breakPressure;
    }

    public int getCoefficientOfFriction() {
        return coefficientOfFriction;
    }

    public void setCoefficientOfFriction(int coefficientOfFriction) {
        this.coefficientOfFriction = coefficientOfFriction;
    }

    public int getSunSensor() {
        return sunSensor;
    }

    public void setSunSensor(int sunSensor) {
        this.sunSensor = sunSensor;
    }

    public int getRainSensor() {
        return rainSensor;
    }

    public void setRainSensor(int rainSensor) {
        this.rainSensor = rainSensor;
    }

    public int getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(int airTemp) {
        this.airTemp = airTemp;
    }

    public int getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(int airPressure) {
        this.airPressure = airPressure;
    }

    public double getSteeringWheelAngle() {
        return steeringWheelAngle;
    }

    public void setSteeringWheelAngle(double steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }

    public int getSteeringWheelAngleConf() {
        return steeringWheelAngleConf;
    }

    public void setSteeringWheelAngleConf(int steeringWheelAngleConf) {
        this.steeringWheelAngleConf = steeringWheelAngleConf;
    }

    public int getSteeringWheelAngleRateOfChange() {
        return steeringWheelAngleRateOfChange;
    }

    public void setSteeringWheelAngleRateOfChange(int steeringWheelAngleRateOfChange) {
        this.steeringWheelAngleRateOfChange = steeringWheelAngleRateOfChange;
    }

    public int getDrivingWheelAngle() {
        return drivingWheelAngle;
    }

    public void setDrivingWheelAngle(int drivingWheelAngle) {
        this.drivingWheelAngle = drivingWheelAngle;
    }

    public int getVehStatusThrottle() {
        return vehStatusThrottle;
    }

    public void setVehStatusThrottle(int vehStatusThrottle) {
        this.vehStatusThrottle = vehStatusThrottle;
    }

    public int getVehStatusHeight() {
        return vehStatusHeight;
    }

    public void setVehStatusHeight(int vehStatusHeight) {
        this.vehStatusHeight = vehStatusHeight;
    }

    public int getVehStatusBumperFront() {
        return vehStatusBumperFront;
    }

    public void setVehStatusBumperFront(int vehStatusBumperFront) {
        this.vehStatusBumperFront = vehStatusBumperFront;
    }

    public int getVehStatusBumperRear() {
        return vehStatusBumperRear;
    }

    public void setVehStatusBumperRear(int vehStatusBumperRear) {
        this.vehStatusBumperRear = vehStatusBumperRear;
    }

    public int getVehStatusMass() {
        return vehStatusMass;
    }

    public void setVehStatusMass(int vehStatusMass) {
        this.vehStatusMass = vehStatusMass;
    }

    public int getVehStatusTrailerWeight() {
        return vehStatusTrailerWeight;
    }

    public void setVehStatusTrailerWeight(int vehStatusTrailerWeight) {
        this.vehStatusTrailerWeight = vehStatusTrailerWeight;
    }

    public int getVehStatusType() {
        return vehStatusType;
    }

    public void setVehStatusType(int vehStatusType) {
        this.vehStatusType = vehStatusType;
    }

    public int getV2vHeight() {
        return v2vHeight;
    }

    public void setV2vHeight(int v2vHeight) {
        this.v2vHeight = v2vHeight;
    }

    public int getV2vBumperFront() {
        return v2vBumperFront;
    }

    public void setV2vBumperFront(int v2vBumperFront) {
        this.v2vBumperFront = v2vBumperFront;
    }

    public int getV2vBumperRear() {
        return v2vBumperRear;
    }

    public void setV2vBumperRear(int v2vBumperRear) {
        this.v2vBumperRear = v2vBumperRear;
    }

    public int getV2vMass() {
        return v2vMass;
    }

    public void setV2vMass(int v2vMass) {
        this.v2vMass = v2vMass;
    }

    public int getV2vType() {
        return v2vType;
    }

    public void setV2vType(int v2vType) {
        this.v2vType = v2vType;
    }

    public Integer getInitPosYear() {
        return initPosYear;
    }

    public void setInitPosYear(Integer initPosYear) {
        this.initPosYear = initPosYear;
    }

    public Integer getInitPosMonth() {
        return initPosMonth;
    }

    public void setInitPosMonth(Integer initPosMonth) {
        this.initPosMonth = initPosMonth;
    }

    public Integer getInitPosDay() {
        return initPosDay;
    }

    public void setInitPosDay(Integer initPosDay) {
        this.initPosDay = initPosDay;
    }

    public Integer getInitPosHour() {
        return initPosHour;
    }

    public void setInitPosHour(Integer initPosHour) {
        this.initPosHour = initPosHour;
    }

    public Integer getInitPosMinute() {
        return initPosMinute;
    }

    public void setInitPosMinute(Integer initPosMinute) {
        this.initPosMinute = initPosMinute;
    }

    public Integer getInitPosSecond() {
        return initPosSecond;
    }

    public void setInitPosSecond(Integer initPosSecond) {
        this.initPosSecond = initPosSecond;
    }

    public Double getInitPosLat() {
        return initPosLat;
    }

    public void setInitPosLat(Double initPosLat) {
        this.initPosLat = initPosLat;
    }

    public Double getInitPosLong() {
        return initPosLong;
    }

    public void setInitPosLong(Double initPosLong) {
        this.initPosLong = initPosLong;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Integer getTrans() {
        return trans;
    }

    public void setTrans(Integer trans) {
        this.trans = trans;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getPosAccuracy() {
        return posAccuracy;
    }

    public void setPosAccuracy(String posAccuracy) {
        this.posAccuracy = posAccuracy;
    }

    public String getTimeConfidence() {
        return timeConfidence;
    }

    public void setTimeConfidence(String timeConfidence) {
        this.timeConfidence = timeConfidence;
    }

    public String getPosConfidence() {
        return posConfidence;
    }

    public void setPosConfidence(String posConfidence) {
        this.posConfidence = posConfidence;
    }

    public String getSpeedConfidence() {
        return speedConfidence;
    }

    public void setSpeedConfidence(String speedConfidence) {
        this.speedConfidence = speedConfidence;
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.print(vehStatusLights + ",");
        pw.print(vehStatusLightBar + ",");
        pw.print(wiperStatFront + ",");
        pw.print(wiperRateFront + ",");
        pw.print(wiperStatRear + ",");
        pw.print(wiperRateRear + ",");
        pw.print(brakes + ",");
        pw.print(breakPressure + ",");
        pw.print(coefficientOfFriction + ",");
        pw.print(sunSensor + ",");
        pw.print(rainSensor + ",");
        pw.print(airTemp + ",");
        pw.print(airPressure + ",");
        pw.print(steeringWheelAngle + ",");
        pw.print(steeringWheelAngleConf + ",");
        pw.print(steeringWheelAngleRateOfChange + ",");
        pw.print(drivingWheelAngle  + ",");
        pw.print(vehStatusThrottle + ",");
        pw.print(vehStatusHeight + ",");
        pw.print(vehStatusBumperFront + ",");
        pw.print(vehStatusBumperRear + ",");
        pw.print(vehStatusMass + ",");
        pw.print(vehStatusTrailerWeight + ",");
        pw.print(vehStatusType + ",");
        pw.print(initPosYear + ",");
        pw.print(initPosMonth + ",");
        pw.print(initPosDay + ",");
        pw.print(initPosHour + ",");
        pw.print(initPosMinute + ",");
        pw.print(initPosSecond + ",");
        pw.print(initPosLat + ",");
        pw.print(initPosLong + ",");
        pw.print(elevation + ",");
        pw.print(heading + ",");
        pw.print(trans + ",");
        pw.print(speed + ",");
        pw.print(posAccuracy + ",");
        pw.print(timeConfidence + ",");
        pw.print(posConfidence + ",");
        pw.print(speedConfidence + ",");
        pw.print(v2vHeight + ",");
        pw.print(v2vBumperFront + ",");
        pw.print(v2vBumperRear + ",");
        pw.print(v2vMass + ",");
        pw.print(v2vType);

        return sw.getBuffer().toString();
    }
}
