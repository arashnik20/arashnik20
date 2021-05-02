package ir.navaco.mcb.olivetti.service;

import ir.navaco.mcb.olivetti.exception.ExceptionBase;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

public class SerialPR4 extends SerialPort {

    private static final Logger logger = LoggerFactory.getLogger(SerialPR4.class);

    public SerialPR4(String portName) {
        super(portName);
        try {
            openPort();

            setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
//          setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
//          setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

        }catch (SerialPortException ex){
            logger.error(ex.getMessage());
            throw new ExceptionBase("SerialPortException");
        }
    }

}
