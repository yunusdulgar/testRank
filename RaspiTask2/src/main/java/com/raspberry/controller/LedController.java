package com.raspberry.RaspiTask;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class LedController {

	private static final Logger LOGGER = Logger.getLogger(LedController.class);

	
    private static GpioPinDigitalOutput pin;

    private static GpioPinDigitalOutput redPin;
    private static GpioPinDigitalOutput greenPin;
    private static GpioPinDigitalOutput yellowPin;

    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {

    	LOGGER.error("greeting greeting greeting " + name);
    	
    	final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        if (redPin == null) {
            redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "RedLED");
		}
        
        if (greenPin == null) {
        	greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "GreenLED");
        }
        
        if (yellowPin == null) {
        	yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "YellowLED");
        }
        // set shutdown state for this pin
        redPin.setShutdownOptions(true, PinState.LOW);

        // set shutdown state for this pin
        greenPin.setShutdownOptions(true, PinState.LOW);
        
        yellowPin.setShutdownOptions(true, PinState.LOW);

        
        if (name.equals("red")) {
            redPin.high();
        } else {
            redPin.low();
        }
        
        if (name.equals("green")) {
        	greenPin.high();
        } else {
        	greenPin.low();
        }
        
        if (name.equals("off")) {
        	yellowPin.high();
        } else {
        	yellowPin.low();
        }
        
        return "Hello greeting11! " + name;
    }
    
    @RequestMapping("/hello")
    public String hello() {
    	
        return "Hello world! ";
    }
    
    @RequestMapping("/led")
    public String ledOn(@RequestParam(value="led", defaultValue="0") String ledPin) {
    	
    	StringBuilder sb=new StringBuilder("");
    	sb.append("GPIO ");
    	sb.append(ledPin);
    	Pin itemGPIOpin =null;
    	itemGPIOpin=RaspiPin.getPinByName(sb.toString());
    	if (itemGPIOpin == null) {
			return "GPIOpin is null: " + sb.toString();
		}else{
    	 if (pin == null) {
             GpioController gpio = GpioFactory.getInstance();
             pin = gpio.provisionDigitalOutputPin(itemGPIOpin, null, PinState.LOW);
         }
         pin.high();
    	
        return "GPIOpin :" + sb.toString();
		}
    }
    
    @RequestMapping("/ledOff")
    public String ledOf(@RequestParam(value="led", defaultValue="0") String ledPin) {
    	StringBuilder sb=new StringBuilder("");
    	sb.append("GPIO ");
    	sb.append(ledPin);
    	Pin itemGPIOpin =null;
    	itemGPIOpin=RaspiPin.getPinByName(sb.toString());
    	if (itemGPIOpin == null) {
			return "GPIOpin is null: " + sb.toString();
		}else{
    	 if (pin == null) {
             GpioController gpio = GpioFactory.getInstance();
             pin = gpio.provisionDigitalOutputPin(itemGPIOpin, null, PinState.LOW);
         }
         pin.low();
    	
        return "GPIOoff :" + sb.toString();
		}
    }

    @RequestMapping("/green")
    public String green() {
        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
        }
        pin.high();

        return "OK";
    }
    
    @RequestMapping("/red")
    public String red() {
        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED", PinState.LOW);
        }
        pin.high();

        return "OK";
    }

}
