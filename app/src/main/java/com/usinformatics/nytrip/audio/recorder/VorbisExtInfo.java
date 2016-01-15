package com.usinformatics.nytrip.audio.recorder;

import com.ideaheap.io.VorbisInfo;

/**
 * Created by D1m11n on 08.07.2015.
 */
public class VorbisExtInfo extends VorbisInfo {

    public int 		channels = 1;

    /**
     * The number of samples per second of pcm data.
     */
    public int 		sampleRate = 8000;

    /**
     * The recording quality of the encoding. This is not currently
     * set for the decoder. The range goes from -.1 (worst) to 1 (best)
     */
    public float 	quality = 1.0f;

    /**
     * the total number of samples from the recording. This field means
     * nothing to the encoder.
     */
    public long	length;
}
