package com.vocumsineratio.calibration.junit;

/**
 * @author Danil Suits (danil@vast.com)
 */
class JunitCalibration {
    public static <T> T testResult(T pass, T fail) {
        // Switch this from pass to fail to ensure that
        // test failures are being reported as intended.
        return pass;
    }
}
