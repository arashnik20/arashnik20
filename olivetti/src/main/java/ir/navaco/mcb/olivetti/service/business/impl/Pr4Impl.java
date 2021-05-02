package ir.navaco.mcb.olivetti.service.business.impl;

import ir.navaco.mcb.olivetti.exception.ExceptionBase;
import ir.navaco.mcb.olivetti.model.StringCommand;
import ir.navaco.mcb.olivetti.model.dto.Pr4Map;
import ir.navaco.mcb.olivetti.model.enums.pr4.LanguageType;
import ir.navaco.mcb.olivetti.service.SerialPR4;
import ir.navaco.mcb.olivetti.service.business.api.Pr4Service;
import ir.navaco.mcb.olivetti.utils.Document;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

@Component
public class Pr4Impl  implements Pr4Service {

    private static final Logger logger = LoggerFactory.getLogger(Pr4Impl.class);

    @Override
    public Long print(String str) {
        try {
            SerialPort serialPort = new SerialPR4(SerialPortList.getPortNames()[0]);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(str.getBytes());
            stream.write(new Pr4Map().get("EJECT_PAPER"));
            serialPort.writeBytes(stream.toByteArray());
        } catch (SerialPortException | IOException e) {
            logger.error(e.getMessage());
            throw new ExceptionBase("SerialPortException");
        }catch (NullPointerException ex){
            logger.error(ex.getMessage());
            throw new ExceptionBase("NullPointerException");
        }
        return 0l;
    }

    @Override
    public Long printSeries(List<StringCommand> list) {
        SerialPort serialPort = new SerialPR4(SerialPortList.getPortNames()[0]);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Pr4Map pr4 = new Pr4Map();
        byte[] bytes = new byte[]{};
        for (StringCommand stringCommand : list) {
            try {
                if(stringCommand.getLanguageType().equals(LanguageType.ENGLISH)){
                    bytes = pr4.get(stringCommand.getStyle().toString());
                    stream.write(bytes);
                    stream.write(stringCommand.getText().getBytes());
                }else if(stringCommand.getLanguageType().equals(LanguageType.PERSIAN)){
                    bytes = pr4.get(stringCommand.getStyle().toString());
                    stream.write(bytes);
                    stream.write(new Document().convertSentence(stringCommand.getText()).get(0).getIrSysWord());
                }else {
                    throw new ExceptionBase("set_LanguageType");
                }
            }catch (IOException ex){
                logger.error("error in create stream to write");
                throw new ExceptionBase("err_create_stream");
            }
        }
        try {
            stream.write(pr4.get("EJECT_PAPER"));
            serialPort.writeBytes(stream.toByteArray());
            serialPort.closePort();
        } catch (SerialPortException | IOException e) {
            logger.error("err_write_stream : "+ e.getMessage());
            throw new ExceptionBase("err_write_stream");
        }
        return 0l;
    }

}
