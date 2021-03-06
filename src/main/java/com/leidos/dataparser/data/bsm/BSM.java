package com.leidos.dataparser.data.bsm;

import com.leidos.dataparser.io.formatting.Output;
import com.leidos.dataparser.io.formatting.OutputData;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BSM implements Serializable, OutputData {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int dbId;
    @Output
    private String bsmMessageId;
    @Output
    private int messageCount;
    @Output
    private String id;
    @Output
    private int secMark;
    @Output
    private double latitude;
    @Output
    private double longitude;
    @Output
    private double elevation;
    @Output
    private String accuracy;
    @Output
    private int trans;
    @Output
    private double speed;
    @Output
    private double heading;
    @Output
    private Integer angle;
    @Output
    private Double acceleration;
    @Output
    private Double accelLat;
    @Output
    private Double accelLong;
    @Output
    private Double accelVert;
    @Output
    private Double accelYaw;
    @Output
    private String brakes;
    @Output
    private int width;
    @Output
    private int length;
    private Timestamp dateReceived;
    @Output
    private SafetyExtData extData;
    @Output
    private VehStatusData vehStatusData;
    @Output
    private String rsuID;
    
    
    public String getDateReceivedString() {
        return sdf.format(new Date(dateReceived.getTime()));
    }

    public void setDateReceivedString(String s) {

    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public String getBsmMessageId() {
        return bsmMessageId;
    }

    public void setBsmMessageId(String bsmMessageId) {
        this.bsmMessageId = bsmMessageId;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSecMark() {
        return secMark;
    }

    public void setSecMark(int secMark) {
        this.secMark = secMark;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public int getTrans() {
        return trans;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public Double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Double acceleration) {
        this.acceleration = acceleration;
    }

    public Double getAccelLat() {
        return accelLat;
    }

    public void setAccelLat(Double accelLat) {
        this.accelLat = accelLat;
    }

    public Double getAccelLong() {
        return accelLong;
    }

    public void setAccelLong(Double accelLong) {
        this.accelLong = accelLong;
    }

    public Double getAccelVert() {
        return accelVert;
    }

    public void setAccelVert(Double accelVert) {
        this.accelVert = accelVert;
    }

    public Double getAccelYaw() {
        return accelYaw;
    }

    public void setAccelYaw(Double accelYaw) {
        this.accelYaw = accelYaw;
    }

    public String getBrakes() {
        return brakes;
    }

    public void setBrakes(String brakes) {
        this.brakes = brakes;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public SafetyExtData getExtData() {
        return extData;
    }

    public void setExtData(SafetyExtData extData) {
        this.extData = extData;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public Timestamp getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Timestamp dateReceived) {
        this.dateReceived = dateReceived;
    }

    public VehStatusData getVehStatusData() {
        return vehStatusData;
    }

    public void setVehStatusData(VehStatusData vehStatusData) {
        this.vehStatusData = vehStatusData;
    }



    public String toString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("bsmMessageId: " + bsmMessageId);
        pw.println("messageCount: " + messageCount);
        pw.println("id: " + id);
        pw.println("secMark: " + secMark);
        pw.println("latitude: " + latitude);
        pw.println("longitude: " + longitude);
        pw.println("elevation: " + elevation);
        pw.println("accuracy: " + accuracy);
        pw.println("trans: " + trans);
        pw.println("speed: " + speed);
        pw.println("heading: " + heading);
        pw.println("angle: " + angle);
        pw.println("acceleration: " + acceleration);

        pw.println("accelLat: " + accelLat);
        pw.println("accelLong: " + accelLong);
        pw.println("accelVert: " + accelVert);
        pw.println("accelYaw: " + accelYaw);

        pw.println("brakes: " + brakes);
        pw.println("width: " + width);
        pw.println("length: " + length);
//		pw.println("dateReceived: "+sdf.format(dateReceived));

        pw.println(extData);
        pw.println(vehStatusData);

        return sw.getBuffer().toString();
    }

    /**
     * @return the rsuID
     */
    public String getRsuID() {
        return rsuID;
    }

    /**
     * @param rsuID the rsuID to set
     */
    public void setRsuID(String rsuID) {
        this.rsuID = rsuID;
    }
}
