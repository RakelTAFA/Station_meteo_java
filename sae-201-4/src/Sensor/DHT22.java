/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensor;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class DHT22 {


    private static final int MAXTIMINGS = 85;
    private final int[] dht11_dat = {0, 0, 0, 0, 0};
    
    private int pinNumber;
  

        public DHT22(int pinNumber) {
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.DEFAULT_PIN_NUMBERING));
        this.pinNumber = pinNumber;
        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }
        GpioUtil.export(pinNumber, GpioUtil.DIRECTION_OUT);
        
    }

    // pinNumber number = Wriring PI 
    public String getTemperatureAndHumidity() {
        String temperature="no data";
        boolean done = false;
        int attempts=0;
        while (!done && attempts<100) {
            int laststate = Gpio.HIGH;
            int j = 0;
            attempts++;
            dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

            Gpio.pinMode(pinNumber, Gpio.OUTPUT);
            Gpio.digitalWrite(pinNumber, Gpio.LOW);
            Gpio.delay(18);

            Gpio.digitalWrite(pinNumber, Gpio.HIGH);
            Gpio.pinMode(pinNumber, Gpio.INPUT);

            for (int i = 0; i < MAXTIMINGS; i++) {
                int counter = 0;
                while (Gpio.digitalRead(pinNumber) == laststate) {
                    counter++;
                    Gpio.delayMicroseconds(1);
                    if (counter == 255) {
                        break;
                    }
                }

                laststate = Gpio.digitalRead(pinNumber);

                if (counter == 255) {
                    break;
                }

                /* ignore first 3 transitions */
                if (i >= 4 && i % 2 == 0) {
                    /* shove each bit into the storage bytes */
                    dht11_dat[j / 8] <<= 1;
                    if (counter > 16) {
                        dht11_dat[j / 8] |= 1;
                    }
                    j++;
                }
            }
            // check we read 40 bits (8bit x 5 ) + verify checksum in the last
            // byte
            if (j >= 40 && checkParity()) {
                // while (j < 40 && ! checkParity()) {
                float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
                if (h > 100) {
                    h = dht11_dat[0]; // for DHT22
                }
                float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
                if (c > 125) {System.out.println("c la");
                    c = dht11_dat[2]; // for DHT22
                }
                if ((dht11_dat[2] & 0x80) != 0) {
                    c = -c;
                }
                //final float f = c * 1.8f + 32;
                
                temperature =  c + "," + h;
                               
                done = true;
            }
        }
        if (!done ){
            temperature = "Data not avalaible...";

        }
        return temperature;
    }
    
    // pinNumber number = Wriring PI 
    public void consolePrinttTemperature() {
        boolean done = false;
        int attempts=0;
        while (!done && attempts<100) {
            int laststate = Gpio.HIGH;
            int j = 0;
            attempts++;
            dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

            Gpio.pinMode(pinNumber, Gpio.OUTPUT);
            Gpio.digitalWrite(pinNumber, Gpio.LOW);
            Gpio.delay(18);

            Gpio.digitalWrite(pinNumber, Gpio.HIGH);
            Gpio.pinMode(pinNumber, Gpio.INPUT);

            for (int i = 0; i < MAXTIMINGS; i++) {
                int counter = 0;
                while (Gpio.digitalRead(pinNumber) == laststate) {
                    counter++;
                    Gpio.delayMicroseconds(1);
                    if (counter == 255) {
                        break;
                    }
                }

                laststate = Gpio.digitalRead(pinNumber);

                if (counter == 255) {
                    break;
                }

                /* ignore first 3 transitions */
                if (i >= 4 && i % 2 == 0) {
                    /* shove each bit into the storage bytes */
                    dht11_dat[j / 8] <<= 1;
                    if (counter > 16) {
                        dht11_dat[j / 8] |= 1;
                    }
                    j++;
                }
            }
            // check we read 40 bits (8bit x 5 ) + verify checksum in the last
            // byte
            if (j >= 40 && checkParity()) {
                
                // while (j < 40 && ! checkParity()) {
                float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
                if (h > 100) {
                    h = dht11_dat[0]; // for DHT22
                }
                float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
                if (c > 125) {
                    c = dht11_dat[2]; // for DHT22
                }
                if ((dht11_dat[2] & 0x80) != 0) {
                    c = -c;
                }
                //final float f = c * 1.8f + 32;
                System.out.println("Humidity = " + h + " % - Temperature = " + c + " °C");
                
                done = true;
            }
        }
        if (!done ){
            System.out.println("Data not available...");
        }
        
    }

    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

    public static void main(final String ars[]) throws Exception {

        int BCM_26 = 25; // wiring Pi number
        
        final DHT22 dht = new DHT22(BCM_26);

        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            dht.getTemperatureAndHumidity();
        }

        System.out.println("Done!!\n");

    }
}
