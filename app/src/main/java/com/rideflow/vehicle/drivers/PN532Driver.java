package com.rideflow.vehicle.drivers;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.UartDeviceCallback;

import java.io.IOException;
import java.util.function.Consumer;

public class PN532Driver {

    private static final String UART_DEVICE_NAME = "UART0";
    private static final int    BUFFER_LENGTH    = 512;
    private static UartDevice   INSTANCE         = null;

    private static PeripheralManagerService peripheralManagerService = null;

    public static UartDevice getInstance() throws IOException {

        if ( peripheralManagerService == null ) {
            peripheralManagerService = new PeripheralManagerService();
        }
        if ( INSTANCE == null ) {
            INSTANCE = peripheralManagerService.openUartDevice(UART_DEVICE_NAME);
            INSTANCE.setBaudrate(115200);
            INSTANCE.setDataSize(8);
            INSTANCE.setParity(UartDevice.PARITY_NONE);
            INSTANCE.setStopBits(1);
        }
        return INSTANCE;
    }

    public static UartDeviceCallback listen(Consumer<String> callback) throws IOException {
        UartDeviceCallback listener =  new UartDeviceCallback() {
            @Override
            public boolean onUartDeviceDataAvailable(UartDevice uart) {
                try {
                    String response = readString();

                    callback.accept(response);
                }
                catch( IOException e ) {
                    //TODO handle NFC read exception
                }

                return true;
            }

            @Override
            public void onUartDeviceError(UartDevice uart, int error) {
                super.onUartDeviceError(uart, error);
            }
        };

        getInstance().registerUartDeviceCallback(listener);

        return listener;
    }

    public static void stopListening(UartDeviceCallback listener) throws IOException {

        if ( listener != null ) {
            getInstance().unregisterUartDeviceCallback(listener);
        }
    }

    public static String readString() throws IOException {

        StringBuilder response = new StringBuilder();

        try {

            byte[] buffer = new byte[BUFFER_LENGTH];

            while( INSTANCE.read(buffer,buffer.length) > 0 ) {
                response.append( new String(buffer) );
            }
        }
        catch ( Exception e ) {
            throw new IOException("Unable to read PN532");
        }

        return response.toString();
    }

}
