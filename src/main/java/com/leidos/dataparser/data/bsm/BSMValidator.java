package com.leidos.dataparser.data.bsm;

/**
 * Validates the contents of a BSM object against the J2735 standard.
 */
public class BSMValidator {
    private boolean validateMessageId(BSM bsm) {
        return bsm.getBsmMessageId().equals("02");
    }

    private boolean validateId(BSM bsm) {
        // All temporary IDs are valid
        return true;
    }

    private boolean validateSecMark(BSM bsm) {
        return bsm.getSecMark() >= 0 && bsm.getSecMark() <= 65535;
    }

    private boolean validateLat(BSM bsm) {
        return (bsm.getLatitude() >= -90.0 && bsm.getLatitude() <= 90.0000001);
    }

    private boolean validateLon(BSM bsm) {
        return (bsm.getLongitude() >= -180.0 && bsm.getLongitude() <= 180.0000001);
    }

    private boolean validateElev(BSM bsm) {
        return true;
    }

    private boolean validateAccuracy(BSM bsm) {
        // Any 4 byte sequence is a valid BSM accuracy
        return bsm.getAccuracy().length() == 8;
    }

    private boolean validateSpeedAndTransmission(BSM bsm) {
        // Validate ranges on DE_Speed and DE_TransmissionState
        return (((bsm.getTrans() >= 0) && (bsm.getTrans() <= 7)) &&
                ((bsm.getSpeed() >= 0.0) && bsm.getSpeed() <= 163.82));
    }

    private boolean validateHeading(BSM bsm) {
        // Validate range on BSM heading field
        return (bsm.getHeading() >= 0.0 && bsm.getHeading() <= 360.0);
    }

    private boolean validateAngle(BSM bsm) {
        if (bsm.getAngle() == null) {
            // It is valid for SteeringWheelAngle to be unavailable.
            return true;
        }

        // Validate the range of the steering wheel angle
        return (bsm.getAngle() >= -189.0 && bsm.getAngle() <= 189.0);
    }

    private boolean validateAccelSet(BSM bsm) {
        // Validate the ranges contained in the BSM field accelSet4Way

        boolean valid = true;

        Double longAccel = bsm.getAccelLong();
        Double latAccel = bsm.getAccelLat();
        Double vertAccel = bsm.getAccelVert();
        Double yawRate = bsm.getAccelYaw();

        if (longAccel != null && !(longAccel >= -20.0 && longAccel <= 20.01)) {
            valid = false;
        }

        if (latAccel != null && !(latAccel >= -20.0 && latAccel <= 20.01)) {
            valid = false;
        }

        if (vertAccel != null && !(vertAccel >= -3.4 && vertAccel <= 1.54)) {
            valid = false;
        }

        if (yawRate != null && !(yawRate >= -327.67 && yawRate <= 327.67)) {
            valid = false;
        }

        return valid;
    }

    private boolean validateBrakes(BSM bsm) {
        // Ensure that brakes is only 2 bytes.
        return bsm.getBrakes().length() == 4;
    }

    private boolean validateSize(BSM bsm) {
        // Range check on size values
        return ((bsm.getWidth() >= 0 && bsm.getWidth() <= 1023) && (bsm.getLength() >= 0 && bsm.getLength() <= 16383));
    }

    public boolean validate(BSM bsm) {
        return (validateMessageId(bsm) &&
                validateId(bsm) &&
                validateSecMark(bsm) &&
                validateLat(bsm) &&
                validateLon(bsm) &&
                validateElev(bsm) &&
                validateAccuracy(bsm) &&
                validateSpeedAndTransmission(bsm) &&
                validateHeading(bsm) &&
                validateAngle(bsm) &&
                validateAccelSet(bsm) &&
                validateBrakes(bsm) &&
                validateSize(bsm));
    }
}
