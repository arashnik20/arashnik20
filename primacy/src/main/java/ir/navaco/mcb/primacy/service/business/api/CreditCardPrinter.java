package ir.navaco.mcb.primacy.service.business.api;

import ir.navaco.mcb.primacy.model.enums.evolis.EvolisCammand;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

public interface CreditCardPrinter {
    CreditCardPrinter open(String printerName);

    CreditCardPrinter close();

    CreditCardPrinter writeOnDevice();

    CreditCardPrinter writeCommand(EvolisCammand evolisCammand, Object... escapeCommand);

    CreditCardPrinter writeCommand(String evolisCammand, Object... escapeCommand);

    int getTimeout();

    CreditCardPrinter setTimeout(int time);

    CreditCardPrinter readFromDevice(byte[] answer, int expectedSize, int[] returnedSize);

    boolean status();

    CreditCardPrinter sequenceEnd();

    CreditCardPrinter parameterRibbon(Object... escapeCommand);

    CreditCardPrinter sequenceStart();

    CreditCardPrinter sequenceInsertion();

    CreditCardPrinter writeClearBitmap(Object... escapeCommand);

    CreditCardPrinter parameterWriteBitmap(Object... escapeCommand);

    CreditCardPrinter parameterWriteRotation(Object... escapeCommand);

    CreditCardPrinter parameterContrast(Object... escapeCommand);

    CreditCardPrinter downloadingMonochromeBitmap(Object... escapeCommand);

    CreditCardPrinter parameterErrorManagement(Object... escapeCommand);

    CreditCardPrinter downloadingMagnetic(Object... escapeCommand);

    CreditCardPrinter sequenceWriteMagneticTrack();

    CreditCardPrinter downloadTrack1(String track);

    CreditCardPrinter downloadTrack2(String track);

    CreditCardPrinter downloadTrack3(String track);
}
