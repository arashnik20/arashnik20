package  ir.navaco.mcb.primacy.model.enums.evolis;

public enum EvolisCammand {
    PARAMETER_RIBBON("Pr"), // To set the Ribbon Type.
    SEQUENCE_START("Ss"),  //To indicate the beginning of a command sequence. // Sequence start. // clear new process and clear the current magnetic buffer
    SEQUENCE_END("Se"),  //To indicate the end of a command started by a Ss, a Si or a Sr. The card is ejected. //Sequence end. // Eject card.
    WRITE_CLEAR_BITMAP("Wcb"), // Clears a bitmap. // Write clear bitmap.
    PARAMETER_WRITE_BITMAP("Pwb"), //To set the current bitmap.  // Parameter write bitmap
    PARAMETER_WRITE_ROTATION("Pwr"), //To set Monochrome Text Rotation. // Parameter write rotation.
    DOWNLOADING_MONOCHROME_BITMAP("Dbmp"),  //To download a raw monochrome BMP file.
    PARAMETER_ERROR_MANAGEMENT("Pem"), //To set the error management. // To set software printer error recovery, software checks all answer from printer after sending an escape command.
    DOWNLOADING_MAGNETIC("Dm"), //To download data inside magnetic buffer track. // Download track
    SEQUENCE_WRITE_MAGNETIC_TRACK("Smw"),  //To write magnetic track. //Sequence write magnetic track. // Encode tracks
    SEQUENCE_INSERTION("Si"), //To insert a card into the printer
    PARAMETER_CONTRAST("Pc"); //To set a contrast for a ribbon layer.


    private String value;

    EvolisCammand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
