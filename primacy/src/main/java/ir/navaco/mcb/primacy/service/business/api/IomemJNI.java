package ir.navaco.mcb.primacy.service.business.api;

import com.sun.jna.Library;

public interface IomemJNI extends Library
{
    int OpenPebble(String printerName);
    boolean ClosePebble(int hPrn);
    boolean WritePebble(int hPrn,String escCde, int szCde);
    boolean WritePebble(int hPrn, byte[] escCde, int szCde);
    boolean ReadPebble(int hPrn, byte[] Answer, int expectedSize, int[] returnedSize);
    int GetTimeout();
    boolean SetTimeout(int time);
    String ReadPebble(int hPrn);
    boolean ReadPebble(int hPrn,String sAnswer, int expectedSize, int [] returnedSize);
}
