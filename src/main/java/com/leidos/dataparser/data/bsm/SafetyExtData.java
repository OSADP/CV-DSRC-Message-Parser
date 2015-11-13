package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.io.formatting.Output;
import com.leidos.dataparser.io.formatting.OutputData;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class SafetyExtData implements Serializable, OutputData {
	private int bsmId;
	@Output
	private Integer pathHistoryLength;
	@Output
	private Integer itemCount;
	@Output
	private String pathHistory;
	@Output
	private Integer curveRadius;
	@Output
	private Integer curveConfidence;
	@Output
	private Integer vehStatusLights;
	@Output
	private Integer vehStatusLightBar;
	@Output
	private Integer wiperStatFront;
	@Output
	private Integer wiperRateFront;
	@Output
	private Integer wiperStatRear;
	@Output
	private Integer wiperRateRear;
	@Output
	private String brakes;
	@Output
	private Integer breakPressure;
	@Output
	private Integer coefficientOfFriction;
	@Output
	private Integer sunSensor;
	@Output
	private Integer rainSensor;
	@Output
	private Integer airTemp;
	@Output
	private Integer airPressure;
	@Output
	private Double steeringWheelAngle;
	@Output
	private Integer steeringWheelAngleConf;
	@Output
	private Integer steeringWheelAngleRateOfChange;
	@Output
	private Integer drivingWheelAngle;
	@Output
	private Integer vehStatusThrottle;
	@Output
	private Integer vehStatusHeight;
	@Output
	private Integer vehStatusBumperFront;
	@Output
	private Integer vehStatusBumperRear;
	@Output
	private Integer vehStatusMass;
	@Output
	private Integer vehStatusTrailerWeight;
	@Output
	private Integer vehStatusType;
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
	private Integer v2vHeight;
	@Output
	private Integer v2vBumperFront;
	@Output
	private Integer v2vBumperRear;
	@Output
	private Integer v2vMass;
	@Output
	private Integer v2vType;
	
	public int getBsmId() {
		return bsmId;
	}
	public void setBsmId(int bsmId) {
		this.bsmId = bsmId;
	}
	public Integer getPathHistoryLength() {
		return pathHistoryLength;
	}
	public void setPathHistoryLength(Integer pathHistoryLength) {
		this.pathHistoryLength = pathHistoryLength;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public String getPathHistory() {
		return pathHistory;
	}
	public void setPathHistory(String pathHistory) {
		this.pathHistory = pathHistory;
	}
	public Integer getCurveRadius() {
		return curveRadius;
	}
	public void setCurveRadius(Integer curveRadius) {
		this.curveRadius = curveRadius;
	}
	public Integer getCurveConfidence() {
		return curveConfidence;
	}
	public void setCurveConfidence(Integer curveConfidence) {
		this.curveConfidence = curveConfidence;
	}
	public Integer getVehStatusLights() {
		return vehStatusLights;
	}
	public void setVehStatusLights(Integer vehStatusLights) {
		this.vehStatusLights = vehStatusLights;
	}
	public Integer getVehStatusLightBar() {
		return vehStatusLightBar;
	}
	public void setVehStatusLightBar(Integer vehStatusLightBar) {
		this.vehStatusLightBar = vehStatusLightBar;
	}
	public Integer getWiperStatFront() {
		return wiperStatFront;
	}
	public void setWiperStatFront(Integer wiperStatFront) {
		this.wiperStatFront = wiperStatFront;
	}
	public Integer getWiperRateFront() {
		return wiperRateFront;
	}
	public void setWiperRateFront(Integer wiperRateFront) {
		this.wiperRateFront = wiperRateFront;
	}
	public Integer getWiperStatRear() {
		return wiperStatRear;
	}
	public void setWiperStatRear(Integer wiperStatRear) {
		this.wiperStatRear = wiperStatRear;
	}
	public Integer getWiperRateRear() {
		return wiperRateRear;
	}
	public void setWiperRateRear(Integer wiperRateRear) {
		this.wiperRateRear = wiperRateRear;
	}
	public String getBrakes() {
		return brakes;
	}
	public void setBrakes(String brakes) {
		this.brakes = brakes;
	}
	public Integer getBreakPressure() {
		return breakPressure;
	}
	public void setBreakPressure(Integer breakPressure) {
		this.breakPressure = breakPressure;
	}
	public Integer getCoefficientOfFriction() {
		return coefficientOfFriction;
	}
	public void setCoefficientOfFriction(Integer coefficientOfFriction) {
		this.coefficientOfFriction = coefficientOfFriction;
	}
	public Integer getSunSensor() {
		return sunSensor;
	}
	public void setSunSensor(Integer sunSensor) {
		this.sunSensor = sunSensor;
	}
	public Integer getRainSensor() {
		return rainSensor;
	}
	public void setRainSensor(Integer rainSensor) {
		this.rainSensor = rainSensor;
	}
	public Integer getAirTemp() {
		return airTemp;
	}
	public void setAirTemp(Integer airTemp) {
		this.airTemp = airTemp;
	}
	public Integer getAirPressure() {
		return airPressure;
	}
	public void setAirPressure(Integer airPressure) {
		this.airPressure = airPressure;
	}
	public Double getSteeringWheelAngle() {
		return steeringWheelAngle;
	}
	public void setSteeringWheelAngle(Double steeringWheelAngle) {
		this.steeringWheelAngle = steeringWheelAngle;
	}
	public Integer getSteeringWheelAngleConf() {
		return steeringWheelAngleConf;
	}
	public void setSteeringWheelAngleConf(Integer steeringWheelAngleConf) {
		this.steeringWheelAngleConf = steeringWheelAngleConf;
	}
	public Integer getSteeringWheelAngleRateOfChange() {
		return steeringWheelAngleRateOfChange;
	}
	public void setSteeringWheelAngleRateOfChange(
			Integer steeringWheelAngleRateOfChange) {
		this.steeringWheelAngleRateOfChange = steeringWheelAngleRateOfChange;
	}
	public Integer getDrivingWheelAngle() {
		return drivingWheelAngle;
	}
	public void setDrivingWheelAngle(Integer drivingWheelAngle) {
		this.drivingWheelAngle = drivingWheelAngle;
	}
	public Integer getVehStatusThrottle() {
		return vehStatusThrottle;
	}
	public void setVehStatusThrottle(Integer vehStatusThrottle) {
		this.vehStatusThrottle = vehStatusThrottle;
	}
	public Integer getVehStatusHeight() {
		return vehStatusHeight;
	}
	public void setVehStatusHeight(Integer vehStatusHeight) {
		this.vehStatusHeight = vehStatusHeight;
	}
	public Integer getVehStatusBumperFront() {
		return vehStatusBumperFront;
	}
	public void setVehStatusBumperFront(Integer vehStatusBumperFront) {
		this.vehStatusBumperFront = vehStatusBumperFront;
	}
	public Integer getVehStatusBumperRear() {
		return vehStatusBumperRear;
	}
	public void setVehStatusBumperRear(Integer vehStatusBumperRear) {
		this.vehStatusBumperRear = vehStatusBumperRear;
	}
	public Integer getVehStatusMass() {
		return vehStatusMass;
	}
	public void setVehStatusMass(Integer vehStatusMass) {
		this.vehStatusMass = vehStatusMass;
	}
	public Integer getVehStatusTrailerWeight() {
		return vehStatusTrailerWeight;
	}
	public void setVehStatusTrailerWeight(Integer vehStatusTrailerWeight) {
		this.vehStatusTrailerWeight = vehStatusTrailerWeight;
	}
	public Integer getVehStatusType() {
		return vehStatusType;
	}
	public void setVehStatusType(Integer vehStatusType) {
		this.vehStatusType = vehStatusType;
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
	public Integer getV2vHeight() {
		return v2vHeight;
	}
	public void setV2vHeight(Integer v2vHeight) {
		this.v2vHeight = v2vHeight;
	}
	public Integer getV2vBumperFront() {
		return v2vBumperFront;
	}
	public void setV2vBumperFront(Integer v2vBumperFront) {
		this.v2vBumperFront = v2vBumperFront;
	}
	public Integer getV2vBumperRear() {
		return v2vBumperRear;
	}
	public void setV2vBumperRear(Integer v2vBumperRear) {
		this.v2vBumperRear = v2vBumperRear;
	}
	public Integer getV2vMass() {
		return v2vMass;
	}
	public void setV2vMass(Integer v2vMass) {
		this.v2vMass = v2vMass;
	}
	public Integer getV2vType() {
		return v2vType;
	}
	public void setV2vType(Integer v2vType) {
		this.v2vType = v2vType;
	}	
	
	
	public String toString(){
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		pw.print(pathHistoryLength + ",");
		pw.print(itemCount + ",");
		pw.print(pathHistory + ",");

		pw.print(curveRadius + ",");
		pw.print(curveConfidence + ",");


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
		pw.print(drivingWheelAngle + ",");
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

