package ir.navaco.mcb.primacy.service.business.impl;

import com.sun.jna.Native;
import ir.navaco.mcb.primacy.exception.ExceptionBase;
import ir.navaco.mcb.primacy.model.enums.evolis.EvolisCammand;
import ir.navaco.mcb.primacy.model.enums.evolis.MagneticTrack;
import ir.navaco.mcb.primacy.service.business.api.CreditCardBitmap;
import ir.navaco.mcb.primacy.service.business.api.CreditCardPrinter;
import ir.navaco.mcb.primacy.service.business.api.IomemJNI;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
public class IomemJNIImpl implements CreditCardPrinter {

    private static final Logger logger = LoggerFactory.getLogger(IomemJNIImpl.class);
    /*
    (Start Character) Command (separator) parameter 1 (separator) parameter 2 (separator) ...parameter n (Stop Character)
    Start Character: ESC
    Separator: ;
    Stop Character: CR
    */
    private static final byte[] ESC = "\033".getBytes();
    private static final byte[] CR = "\015".getBytes();
    private static final byte[] SEPARATOR = ";".getBytes();
    private ByteArrayOutputStream escapeCommands = new ByteArrayOutputStream();
    private int cardPrinterId = 0;
    private boolean status = false;


    private static IomemJNI  instance ;
        static  {
          instance = (IomemJNI) Native.loadLibrary("iomem",IomemJNI.class); //todo need this ???
         }


    @Override
    public CreditCardPrinter writeCommand(String evolisCammand, Object... escapeCommand) {
        byte[] arrCommand = ArrayUtils.addAll(ESC, evolisCammand.getBytes());
        arrCommand = escapeCommand.length > 0 ? ArrayUtils.addAll(arrCommand, SEPARATOR) : arrCommand;

        byte[] escapeCommandByte = Arrays.asList(escapeCommand).stream()
                .map(command -> command instanceof byte[] ? (byte[]) command : (command.toString()).getBytes())
                .reduce((command1, command2) -> ArrayUtils.addAll(ArrayUtils.addAll(command1, SEPARATOR), command2))
                .orElseGet(() -> new byte[]{});

        arrCommand = ArrayUtils.addAll(arrCommand, escapeCommandByte);
        arrCommand = ArrayUtils.addAll(arrCommand, CR);

        try {
            escapeCommands.write(arrCommand);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ExceptionBase("command_error");
        }
        return this;
    }

    @Override
    public CreditCardPrinter writeCommand(EvolisCammand evolisCammand, Object... escapeCommand) {
        return writeCommand(evolisCammand.getValue(), escapeCommand);
    }

    private void clearCommand() {
        escapeCommands.reset();
    }

    private byte[] getCommands() {
        return escapeCommands.toByteArray();
    }

    @Override
    public CreditCardPrinter open(String printerName) {
        cardPrinterId = instance.OpenPebble(printerName);

        if (cardPrinterId == 0) {
            logger.error("not_connect_to_printer");
            throw new ExceptionBase("not_connect_to_printer");
        }

        return this;
    }

    @Override
    public CreditCardPrinter close() {
        status = instance.ClosePebble(cardPrinterId);

        return this;
    }

    @Override
    public CreditCardPrinter writeOnDevice() {
        byte[] escapeCommands = getCommands();
        status = instance.WritePebble(cardPrinterId, escapeCommands, escapeCommands.length);
        clearCommand();
        return this;
    }

    @Override
    public int getTimeout() {
        return instance.GetTimeout();
    }

    @Override
    public CreditCardPrinter setTimeout(int time) {
        status = instance.SetTimeout(time);

        return this;
    }

    @Override
    public CreditCardPrinter readFromDevice(byte[] answer, int expectedSize, int[] returnedSize) {
        status = instance.ReadPebble(cardPrinterId, answer, expectedSize, returnedSize);
        //todo cheeck answer need be String or Byte

        return this;
    }

    @Override
    public boolean status() {
        return status;
    }

    @Override
    public CreditCardPrinter sequenceEnd() {
        writeCommand(EvolisCammand.SEQUENCE_END);
        return this;
    }

    @Override
    public CreditCardPrinter parameterRibbon(Object... escapeCommand) {
        writeCommand(EvolisCammand.PARAMETER_RIBBON, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter sequenceStart() {
        writeCommand(EvolisCammand.SEQUENCE_START);

        return this;
    }

    @Override
    public CreditCardPrinter sequenceInsertion() {
        writeCommand(EvolisCammand.SEQUENCE_INSERTION);

        return this;
    }

    @Override
    public CreditCardPrinter writeClearBitmap(Object... escapeCommand) {
        writeCommand(EvolisCammand.WRITE_CLEAR_BITMAP, escapeCommand); // Write clear bitmap.

        return this;
    }

    @Override
    public CreditCardPrinter parameterWriteBitmap(Object... escapeCommand) {
        writeCommand(EvolisCammand.PARAMETER_WRITE_BITMAP, escapeCommand);  // Parameter write bitmap

        return this;
    }

    @Override
    public CreditCardPrinter parameterWriteRotation(Object... escapeCommand) {
        writeCommand(EvolisCammand.PARAMETER_WRITE_ROTATION, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter parameterContrast(Object... escapeCommand) {
        writeCommand(EvolisCammand.PARAMETER_CONTRAST, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter downloadingMonochromeBitmap(Object... escapeCommand) {
        writeCommand(EvolisCammand.DOWNLOADING_MONOCHROME_BITMAP, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter parameterErrorManagement(Object... escapeCommand) {
        //TODO generate enum for 'ParameterErrorManagement' input parameter
        writeCommand(EvolisCammand.PARAMETER_ERROR_MANAGEMENT, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter downloadingMagnetic(Object... escapeCommand) {
        writeCommand(EvolisCammand.DOWNLOADING_MAGNETIC, escapeCommand);

        return this;
    }

    @Override
    public CreditCardPrinter sequenceWriteMagneticTrack() {
        writeCommand(EvolisCammand.SEQUENCE_WRITE_MAGNETIC_TRACK);

        return this;
    }

    @Override
    public CreditCardPrinter downloadTrack1(String track) {
        return downloadingMagnetic(MagneticTrack.TRACK1, track);
    }

    @Override
    public CreditCardPrinter downloadTrack2(String track) {
        return downloadingMagnetic(MagneticTrack.TRACK2, track);
    }

    @Override
    public CreditCardPrinter downloadTrack3(String track) {
        return downloadingMagnetic(MagneticTrack.TRACK3, track);
    }
}
