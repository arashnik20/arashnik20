package ir.navaco.mcb.primacy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author arash nikakhlagh [arash.nikakhlagh@gmail.com]
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DownloadTrack {
    private String track1;

    private String track2;

    private String track3;
}
