package com.yannis.mycellid;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yannis on 9/28/2016.
 */

public class CustomPhoneStateListener extends PhoneStateListener {

    MainActivity mActivity;
    private static String TAG = "TelephonyApp";
    private int vcid, vlac, vmcc, vmnc, vss;

    CustomPhoneStateListener(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        Log.i(TAG, "onCallForwardingIndicatorChanged = " + Boolean.toString(cfi));
        super.onCallForwardingIndicatorChanged(cfi);

    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(TAG, "onCallStateChanged:CALL_STATE_IDLE " + incomingNumber);
                mActivity.log(mActivity.getLogText() + "onCallStateChanged:CALL_STATE_IDLE " + incomingNumber + "\n");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(TAG, "onCallStateChanged:CALL_STATE_RINGING " + incomingNumber);
                mActivity.log(mActivity.getLogText() + "onCallStateChanged:CALL_STATE_IDLE " + incomingNumber + "\n");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "onCallStateChanged:CALL_STATE_OFFHOOK " + incomingNumber);
                mActivity.log(mActivity.getLogText() + "onCallStateChanged:CALL_STATE_IDLE " + incomingNumber + "\n");
                break;
            default:
                Log.i(TAG, "onCallStateChanged:NO_STATE" + state);
                mActivity.log(mActivity.getLogText() + "onCallStateChanged:NO_STATE" + state + "\n");
                break;
        }
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        Log.i(TAG, "onCellInfoChanged: " + cellInfo);
        mActivity.log(mActivity.getLogText() + "onCellInfoChanged: " + cellInfo + "\n");
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        switch (direction) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.i(TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                mActivity.log(mActivity.getLogText() + "onDataActivity: DATA_ACTIVITY_NONE" + "\n");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.i(TAG, "onDataActivity: DATA_ACTIVITY_IN");
                mActivity.log(mActivity.getLogText() + "onDataActivity: DATA_ACTIVITY_IN" + "\n");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.i(TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                mActivity.log(mActivity.getLogText() + "onDataActivity: DATA_ACTIVITY_OUT" + "\n");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.i(TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                mActivity.log(mActivity.getLogText() + "onDataActivity: DATA_ACTIVITY_INOUT" + "\n");
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.i(TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                mActivity.log(mActivity.getLogText() + "onDataActivity: DATA_ACTIVITY_DORMANT" + "\n");
                break;
        }
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state);
        switch (state) {
            case TelephonyManager.DATA_DISCONNECTED:
                Log.i(TAG, "onDataConnectionStateChanged: DATA_DISCONNECTED ");
                mActivity.log(mActivity.getLogText() + "onDataConnectionStateChanged: DATA_DISCONNECTED " + "\n");
                break;
            case TelephonyManager.DATA_CONNECTING:
                Log.i(TAG, "onDataConnectionStateChanged: DATA_CONNECTING ");
                mActivity.log(mActivity.getLogText() + "onDataConnectionStateChanged: DATA_CONNECTING " + "\n");
                break;
            case TelephonyManager.DATA_CONNECTED:
                Log.i(TAG, "onDataConnectionStateChanged: DATA_CONNECTED ");
                mActivity.log(mActivity.getLogText() + "onDataConnectionStateChanged: DATA_CONNECTED " + "\n");
                break;
            case TelephonyManager.DATA_SUSPENDED:
                Log.i(TAG, "onDataConnectionStateChanged: DATA_SUSPENDED ");
                mActivity.log(mActivity.getLogText() + "onDataConnectionStateChanged: DATA_SUSPENDED " + "\n");
                break;
        }

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                Log.i(TAG, "NETWORK_TYPE_UNKNOWN");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_UNKNOWN" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                Log.i(TAG, "NETWORK_TYPE_GPRS");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_GPRS" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                Log.i(TAG, "NETWORK_TYPE_EDGE");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_EDGE" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                Log.i(TAG, "NETWORK_TYPE_UMTS");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_UMTS" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                Log.i(TAG, "NETWORK_TYPE_CDMA");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_CDMA" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                Log.i(TAG, "NETWORK_TYPE_EVDO_0");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_EVDO_0" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                Log.i(TAG, "NETWORK_TYPE_EVDO_A");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_EVDO_A" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                Log.i(TAG, "NETWORK_TYPE_1xRTT");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_1xRTT" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                Log.i(TAG, "NETWORK_TYPE_HSDPA");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_HSDPA" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                Log.i(TAG, "NETWORK_TYPE_HSUPA");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_HSUPA" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                Log.i(TAG, "NETWORK_TYPE_HSPA");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_HSPA" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                Log.i(TAG, "NETWORK_TYPE_IDEN");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_IDEN" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                Log.i(TAG, "NETWORK_TYPE_EVDO_B");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_EVDO_B" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                Log.i(TAG, "NETWORK_TYPE_LTE");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_LTE" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                Log.i(TAG, "NETWORK_TYPE_EHRPD");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_EHRPD" + "\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                Log.i(TAG, "NETWORK_TYPE_HSPAP");
                mActivity.log(mActivity.getLogText() + "NETWORK_TYPE_HSPAP" + "\n");
                break;
        }
    }

    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        Log.i(TAG, "onServiceStateChanged: " + serviceState.toString());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: " + serviceState.toString() + "\n");
        Log.i(TAG, "onServiceStateChanged: getOperatorAlphaLong() " +
                serviceState.getOperatorAlphaLong());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: getOperatorAlphaLong() " +
                serviceState.getOperatorAlphaLong() + "\n");
        Log.i(TAG, "onServiceStateChanged: getOperatorAlphaShort() " +
                serviceState.getOperatorAlphaShort());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: getOperatorAlphaShort() " +
                serviceState.getOperatorAlphaShort() + "\n");
        Log.i(TAG, "onServiceStateChanged: getOperatorNumeric() " +
                serviceState.getOperatorNumeric());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: getOperatorNumeric() " +
                serviceState.getOperatorNumeric() + "\n");
        Log.i(TAG, "onServiceStateChanged: getIsManualSelection() " +
                serviceState.getIsManualSelection());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: getIsManualSelection() " +
                serviceState.getIsManualSelection() + "\n");
        Log.i(TAG, "onServiceStateChanged: getRoaming() " +
                serviceState.getRoaming());
        mActivity.log(mActivity.getLogText() + "onServiceStateChanged: getRoaming() " +
                serviceState.getRoaming() + "\n");

        switch (serviceState.getState()) {
            case ServiceState.STATE_IN_SERVICE:
                Log.i(TAG, "onServiceStateChanged: STATE_IN_SERVICE");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                Log.i(TAG, "onServiceStateChanged: STATE_OUT_OF_SERVICE");
                break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                Log.i(TAG, "onServiceStateChanged: STATE_EMERGENCY_ONLY");
                break;
            case ServiceState.STATE_POWER_OFF:
                Log.i(TAG, "onServiceStateChanged: STATE_POWER_OFF");
                break;
        }
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        Log.i(TAG, "onSignalStrengthsChanged: " + signalStrength.toString());

        if (signalStrength.isGsm()) {
            Log.i(TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                    + signalStrength.getGsmBitErrorRate());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getGsmBitErrorRate "
                    + signalStrength.getGsmBitErrorRate() + "\n");
            Log.i(TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                    + signalStrength.getGsmSignalStrength());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getGsmSignalStrength "
                    + signalStrength.getGsmSignalStrength() + "\n");
            mActivity.setSS(signalStrength.getGsmSignalStrength());
        } else if (signalStrength.getCdmaDbm() > 0) {
            Log.i(TAG, "onSignalStrengthsChanged: getCdmaDbm "
                    + signalStrength.getCdmaDbm());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getCdmaDbm "
                    + signalStrength.getCdmaDbm() + "\n");
            Log.i(TAG, "onSignalStrengthsChanged: getCdmaEcio "
                    + signalStrength.getCdmaEcio());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getCdmaEcio "
                    + signalStrength.getCdmaEcio() + "\n");
        } else {
            Log.i(TAG, "onSignalStrengthsChanged: getEvdoDbm "
                    + signalStrength.getEvdoDbm());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getEvdoDbm "
                    + signalStrength.getEvdoDbm() + "\n");
            Log.i(TAG, "onSignalStrengthsChanged: getEvdoEcio "
                    + signalStrength.getEvdoEcio());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getEvdoEcio "
                    + signalStrength.getEvdoEcio() + "\n");
            Log.i(TAG, "onSignalStrengthsChanged: getEvdoSnr "
                    + signalStrength.getEvdoSnr());
            mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: getEvdoSnr "
                    + signalStrength.getEvdoSnr() + "\n");
        }

        try {
            Method[] methods = SignalStrength.class.getMethods();
            for (Method mthd : methods) {
                Log.i(TAG, "onSignalStrengthsChanged: " + mthd.getName());
                mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: " + mthd.getName() + "\n");
                if (mthd.getName().equals("getLteSignalStrength")
                        || mthd.getName().equals("getLteRsrp")
                        || mthd.getName().equals("getLteRsrq")
                        || mthd.getName().equals("getLteRssnr")
                        || mthd.getName().equals("getLteCqi")) {
                    Log.i(TAG, "onSignalStrengthsChanged: " + mthd.getName() + " "
                            + mthd.invoke(signalStrength));
                    mActivity.log(mActivity.getLogText() + "onSignalStrengthsChanged: " + mthd.getName() + " "
                            + mthd.invoke(signalStrength) + "\n");
                }
            }
        } catch (SecurityException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if (location instanceof GsmCellLocation) {
            GsmCellLocation gcmLoc = (GsmCellLocation) location;
            Log.i(TAG,
                    "onCellLocationChanged: GsmCellLocation "
                            + gcmLoc.toString());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: GsmCellLocation "
                    + gcmLoc.toString() + "\n");
            Log.i(TAG, "onCellLocationChanged: GsmCellLocation getCid "
                    + gcmLoc.getCid());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: GsmCellLocation getCid "
                    + gcmLoc.getCid() + "\n");
            mActivity.setCID(gcmLoc.getCid());
            Log.i(TAG, "onCellLocationChanged: GsmCellLocation getLac "
                    + gcmLoc.getLac());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: GsmCellLocation getLac "
                    + gcmLoc.getLac() + "\n");
            mActivity.setLac(gcmLoc.getLac());
            Log.i(TAG, "onCellLocationChanged: GsmCellLocation getPsc"
                    + gcmLoc.getPsc()); // Requires min API 9
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: GsmCellLocation getPsc"
                    + gcmLoc.getPsc() + "\n");
        } else if (location instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaLoc = (CdmaCellLocation) location;
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation "
                            + cdmaLoc.toString());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation "
                    + cdmaLoc.toString() + "\n");
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationId "
                            + cdmaLoc.getBaseStationId());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation getBaseStationId "
                    + cdmaLoc.getBaseStationId() + "\n");
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
                            + cdmaLoc.getBaseStationLatitude());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
                    + cdmaLoc.getBaseStationLatitude() + "\n");
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
                            + cdmaLoc.getBaseStationLongitude());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
                    + cdmaLoc.getBaseStationLongitude() + "\n");
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation getNetworkId "
                            + cdmaLoc.getNetworkId());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation getNetworkId "
                    + cdmaLoc.getNetworkId() + "\n");
            Log.i(TAG,
                    "onCellLocationChanged: CdmaCellLocation getSystemId "
                            + cdmaLoc.getSystemId());
            mActivity.log(mActivity.getLogText() + "onCellLocationChanged: CdmaCellLocation getSystemId "
                    + cdmaLoc.getSystemId() + "\n");
        }
    }
}
