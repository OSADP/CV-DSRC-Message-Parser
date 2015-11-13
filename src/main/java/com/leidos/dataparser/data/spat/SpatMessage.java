package com.leidos.dataparser.data.spat;

import com.leidos.dataparser.io.formatting.Output;
import com.leidos.dataparser.io.formatting.OutputData;
import com.leidos.dataparser.util.UnpackUtils;
import javassist.bytecode.ByteArray;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SPAT Message
 *
 * User: ferenced
 * Date: 1/16/15
 * Time: 3:19 PM
 *
 */
public class SpatMessage implements OutputData {

    private byte ID = (byte) 0x8d;

    @Output
    private int version;

    private int payloadLength;

    @Output
    private long intersectionId;

    @Output
    private int status;

    @Output
    private DateTime timestamp;

    @Output
    private List<Movement> movements = new ArrayList<>();

    private Movement movement = null;
    private static Movement lastMovement = null;

    // objects decoded in a SPAT message
    public static final int SO_INTERSECTION_ID = 0x01;
    public static final int SO_INTERSECTION_STATUS = 0x02;
    public static final int SO_MESSAGE_TIMESTAMP = 0x03;
    public static final int SO_MOVEMENT = 0x04;
    public static final int SO_LANE_SET = 0x05;
    public static final int SO_CURRENT_STATE = 0x06;
    public static final int SO_MIN_TIME_REMAINING = 0x07;
    public static final int SO_MAX_TIME_REMAINING = 0x08;
    public static final int SO_YELLOW_STATE = 0x09;
    public static final int SO_YELLOW_TIME = 0x0A;
    public static final int SO_PEDESTRIAN_DETECTED = 0x0B;
    public static final int SO_VEHICLE_PEDESTRIAN_COUNT = 0x0C;
    public static final int SO_EOB = 0xFF;

    public static final int SO_INDEX_INTERSECTION_ID = 4;

    public SpatMessage()   {
    }

    public boolean parse(byte[] data)   {

        boolean result = true;

        try   {
            validate(data);

            int nextIndex = SO_INDEX_INTERSECTION_ID;

            while (nextIndex > 0)   {
                nextIndex = processObject(data, nextIndex);
            }
        }
        catch(Exception e)   {
            // logger.error("SPAT", "Error parsing SPAT message: " + e.getMessage());
            result = false;
        }

        return result;
    }

    /**
     *
     * @param objectBuf
     * @param startIndex
     * @return
     * @throws Exception
     */
    private int processObject(byte[] objectBuf, int startIndex) throws Exception   {
        byte objectId = (byte) objectBuf[startIndex];

        if (objectId == (byte) SO_EOB)   {
           return -1;
        }

        int objectLength = objectBuf[startIndex + 1];

        switch (objectId)   {
            case SO_INTERSECTION_ID:
                intersectionId = ByteArray.read32bit(objectBuf, startIndex + 2);
                break;

            case SO_INTERSECTION_STATUS:
                status = objectBuf[startIndex + 2] & 0x0F;
                break;

            case SO_MESSAGE_TIMESTAMP:
                byte[] baSeconds = Arrays.copyOfRange(objectBuf, startIndex + 2, startIndex + 2 + 4);

                long seconds = UnpackUtils.getInstance().unpackU32BigEndian(baSeconds);
                int tenths = objectBuf[startIndex + 6];

                timestamp = new DateTime(seconds * 1000 + tenths * 100);
                break;

            case SO_MOVEMENT:
                // create new movement
                movement = new Movement();
                objectLength = - 1;       // no payload with movement object
                break;

            case SO_LANE_SET:
                byte[] lanesetPayload = Arrays.copyOfRange(objectBuf, startIndex + 2, startIndex + 2 + objectLength);

                for (int i=0; i<lanesetPayload.length; i+=2)   {
                    LaneSet laneSet = new LaneSet(lanesetPayload[i + 1], lanesetPayload[i]);
                    movement.addLaneSet(laneSet);
                }

                break;

            case SO_CURRENT_STATE:
                byte[] stateArray = Arrays.copyOfRange(objectBuf, startIndex + 2, startIndex + 2 + objectLength);
                int state = 0;

                if (stateArray.length == 1)   {
                    state = stateArray[0];
                }
                else if (stateArray.length == 2)   {
                    state = ByteArray.readU16bit(stateArray, 0);
                }
                else if (stateArray.length == 4)   {
                    state = ByteArray.read32bit(stateArray, 0);
                }

                movement.setCurrentState(state);
                break;

            case SO_MIN_TIME_REMAINING:
                int timeRemaining = ByteArray.readU16bit(objectBuf, startIndex + 2);
                movement.setMinTimeRemaining(timeRemaining);
                break;

            case SO_MAX_TIME_REMAINING:
                int maxTimeRemaining = ByteArray.readU16bit(objectBuf, startIndex + 2);
                movement.setMaxTimeRemaining(maxTimeRemaining);
                movements.add(movement);
                break;

            case SO_YELLOW_STATE:
            case SO_YELLOW_TIME:
            case SO_PEDESTRIAN_DETECTED:
            case SO_VEHICLE_PEDESTRIAN_COUNT:
            // these objects are optional, they weren't included when testing in the 'flashing' state, but the two
            // yellow objects were occasionally included when SPAT was switched to normal
                break;

            default:
                throw new Exception("Invalid Object ID in SPAT message.");

        }

        return startIndex + 2 + objectLength;
    }

    /**
     * Validates that this byte array is a SPAT message
     *
     * Ensures valid SPAT ID (0x8D)
     * Ensures last byte == 0xFF based on payload length
     *
     * @param data
     * @throws Exception
     */
    private void validate(byte[] data) throws Exception   {

        if (data[0] != ID)   {
            throw new Exception("Invalid ID (not 0x8D) for SPAT message.");
        }

        version = data[1] & 0xFF;

        payloadLength = ByteArray.readU16bit(data, 2);

        byte eob = data[2 + payloadLength + 1];

        if (eob != (byte) SO_EOB)   {
            throw new Exception("Invalid EOB (not 0xFF) for SPAT message.");
        }

    }

    public void dumpSpatMessage()    {

        // logger.debug("SPAT", " ###### SPAT MESSAGE #####");
        // logger.debug("SPAT", "Intersection ID: \t" + intersectionId);
        // logger.debug("SPAT", "Version: \t" + version);
        // logger.debug("SPAT", "Intersection Status: \t" + status);
        // logger.debug("SPAT", "Timestamp: " + timestamp);

        for (Movement movement : movements)   {
            // logger.debug("SPAT", "  ***** movement ******");
            for (LaneSet laneSet : movement.getLaneSets())   {
                // logger.debug("SPAT", "LaneSet: " + laneSet.getLane() + "  :  " + laneSet.getMovementAsString());
            }

            // logger.debug("SPAT", "Movement State: \t" + movement.getCurrentState());
            // logger.debug("SPAT", "Min Time: \t" + movement.getMinTimeRemaining());
            // logger.debug("SPAT", "Max Time: \t" + movement.getMaxTimeRemaining());
        }
    }

    public String getSpatMessageAsString()    {

        StringBuffer sb = new StringBuffer();
        sb.append(" ###### SPAT MESSAGE #####");

        sb.append(" ###### SPAT MESSAGE #####\n");
        sb.append("Intersection ID: \t" + intersectionId + "\n");
        sb.append("Version: \t" + version + "\n");
        sb.append("Intersection Status: \t" + status + "\n");
        sb.append("Timestamp: " + timestamp + "\n");

        for (Movement movement : movements)   {
            sb.append("  ***** movement ******" + "\n");
            for (LaneSet laneSet : movement.getLaneSets())   {
                sb.append("LaneSet: " + laneSet.getLane() + "  :  " + laneSet.getMovementAsString() + "\n");
            }

            sb.append("Movement State: \t" + movement.getCurrentState() + "\n");
            sb.append("Min Time: \t" + movement.getMinTimeRemaining() + "\n");
            sb.append("Max Time: \t" + movement.getMaxTimeRemaining() + "\n");
        }

        return sb.toString();

    }

}
