/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensor;

/**
 * @author l.buathier
 *
 * Analog read from I2C analog port A0 change numCanal to read another Analog
 * input (0 to 3)
 *
 */
import java.io.IOException;
import java.util.Arrays;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.util.Console;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This example code demonstrates how to perform simple I2C communication on the
 * Raspberry Pi. For this example we will connect to RPi BAs Hat on canal A0
 *
 */
public class AnalogI2CInput {

    private static final byte WORD = 2;  // number of bytes to read

    // TSL2561 I2C address
    private static final byte BASE_HAT_ADDR = 0x04;
    
    // TSL2561 registers
    private static final byte BASE_HAT_REG_SET_ADDR = (byte) 0xC0;
    private static final byte BASE_HAT_REG_RAW_DATA = (byte) 0x10;
    private static final byte BASE_HAT_REG_INPUT_VOLTAGE = (byte) 0x20;
    private int numCanal = 0;  // A0 connector
    
    I2CDevice device;

    
    public AnalogI2CInput(int numCanal) throws IOException, UnsupportedBusNumberException{
         this.numCanal=numCanal;       
        // fetchAllAvailableBusses(console);
        // get the I2C bus to communicate on
        I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        // create an I2C device for an individual device on the bus that you want to communicate with
        // in this example we will use the default address for the TSL2561 chip which is 0x39.
        device = i2c.getDevice(BASE_HAT_ADDR);

    }

    public AnalogI2CInput(I2CBus i2c, int numCanal) throws IOException {
        this.numCanal = numCanal;
         device = i2c.getDevice(BASE_HAT_ADDR);
    }

    public int getRowValue() {
        int dataRead = 0;
            byte buffer[] = new byte[2];
            int BytesReceveived = 0;
            try {
                BytesReceveived = device.read(BASE_HAT_REG_RAW_DATA+numCanal, buffer, 0 , WORD);
            } catch (IOException ex) {
                Logger.getLogger(AnalogI2CInput.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (BytesReceveived == 2) {
                // console.println("bytes received : = " +  BytesReceveived);
                dataRead = ((int)buffer[1])<<8 | buffer[0];
                if (dataRead < 4096 && dataRead > 0) {
                    System.out.println("Analog A"+numCanal +" = " + dataRead);
                    System.out.println("Analog A"+numCanal +" = " + 100*dataRead/4096 +"(%)");
   //                System.out.println("AnalogI2CInput A0 (hexa) = " + String.format("0x%02x", dataRead));
                 }
            }
            return dataRead;
    }
    
    public int getPourcentValue() {
            return 100*getRowValue()/4096;
    }
    
    public void runDemoConsole() {
        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "I2C Analog Conversion");

        // allow for user to exit program using CTRL-C
        console.promptForExit();
        
        for (int i = 0; i < 50; i++) {

            // now we will perform our first I2C READ operation to retrieve raw integration
            // results from DATA_0 and DATA_1 registers
            console.println("... reading DATA registers from A+"+numCanal);
            try {
                device.write(BASE_HAT_REG_RAW_DATA);
            } catch (IOException ex) {
                Logger.getLogger(AnalogI2CInput.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnalogI2CInput.class.getName()).log(Level.SEVERE, null, ex);
            }
 /*     // if only byte to read 
        int dataRead= device.read(BASE_HAT_REG_RAW_DATA+numCanal);
        console.println("AnalogI2CInput input A" + numCanal +" = " +  dataRead);
 */
           console.println("Analog A"+numCanal +" = " + 100*getRowValue()/4096 +"(%)");
   //               
        }
    }


    private void fetchAllAvailableBusses(Console console) {
        // fetch all available busses
        try {
            int[] ids = I2CFactory.getBusIds();
            console.println("Found follow I2C busses: " + Arrays.toString(ids));
        } catch (IOException exception) {
            console.println("I/O error during fetch of I2C busses occurred");
        }

        // find available busses
        for (int number = I2CBus.BUS_0; number <= I2CBus.BUS_17; ++number) {
            try {
                @SuppressWarnings("unused")
                I2CBus bus = I2CFactory.getInstance(number);
                console.println("Supported I2C bus " + number + " found");
            } catch (IOException exception) {
                console.println("I/O error on I2C bus " + number + " occurred");
            } catch (UnsupportedBusNumberException exception) {
                console.println("Unsupported I2C bus " + number + " required");
            }
        }
    }




    
}
