/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.audio;

import at.beris.games.alphablockz.Application;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;

public class AudioResource {
    private Bitstream bitstream;
    private AudioResourceData data;
    private Decoder decoder;

    public AudioResource(AudioResourceData audioResourceEnum) {
        this.bitstream = new Bitstream(this.getClass().getClassLoader().getResourceAsStream(audioResourceEnum.getFilename()));
        this.data = audioResourceEnum;
        this.decoder = new Decoder();
    }

    public Bitstream getBitstream() {
        return bitstream;
    }

    public void setBitstream(Bitstream bitstream) {
        this.bitstream = bitstream;
    }

    public void close() {
        try {
            bitstream.close();
        } catch (BitstreamException e) {
            Application.logException(e);
        }
    }

    public Header readFrame() {
        try {
            return bitstream.readFrame();
        } catch (BitstreamException e) {
            Application.logException(e);
        }
        return null;
    }

    public void closeFrame() {
        bitstream.closeFrame();
    }

    public void reset() {
        this.bitstream.reset();
    }

    public AudioResourceData getData() {
        return data;
    }

    public Decoder getDecoder() {
        return decoder;
    }
}
