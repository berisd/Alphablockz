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
import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AudioPlayer implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());

    private AudioDevice audioDevice;
    private Thread thread;
    private AudioResource currentAudioResource;

    private boolean running = false;
    private boolean playing = false;
    private boolean looping = false;

    private Map<AudioResourceData, AudioResource> audioResourceMap;
    private Map<AudioResource, Decoder> decoderMap;

    public AudioPlayer() throws JavaLayerException {
        JavaLayerUtils.setHook(new JavaLayerHook() {
            public InputStream getResourceAsStream(String name) {
                return this.getClass().getClassLoader().getResourceAsStream(name);
            }
        });

        audioResourceMap = new HashMap<AudioResourceData, AudioResource>();
        decoderMap = new HashMap<AudioResource, Decoder>();

        FactoryRegistry r = FactoryRegistry.systemRegistry();
        this.audioDevice = r.createAudioDevice();

        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public void addAudioResource(AudioResource audioResource) {
        audioResourceMap.put(audioResource.getData(), audioResource);
        decoderMap.put(audioResource, new Decoder());
        currentAudioResource = audioResource;
    }

    public boolean play(int frames) throws JavaLayerException {
        boolean ret;
        for (ret = true; frames-- > 0 && ret; ret = this.decodeFrame()) {
            ;
        }

        return ret;
    }

    public synchronized void close() {
        AudioDevice out = this.audioDevice;
        if (out != null) {
            this.audioDevice = null;
            out.close();
            currentAudioResource.close();
        }
    }

    protected boolean decodeFrame() throws JavaLayerException {
        try {
            AudioDevice ex = this.audioDevice;
            if (ex == null) {
                return false;
            } else {
                Header h = currentAudioResource.readFrame();
                if (h == null) {
                    return false;
                } else {
                    SampleBuffer output = (SampleBuffer) currentAudioResource.getDecoder().decodeFrame(h, currentAudioResource.getBitstream());
                    synchronized (this) {
                        ex = this.audioDevice;
                        if (ex != null) {
                            ex.write(output.getBuffer(), 0, output.getBufferLength());
                        }
                    }

                    currentAudioResource.closeFrame();
                    return true;
                }
            }
        } catch (RuntimeException e) {
            Application.logException(new JavaLayerException("Exception decoding audio frame", e));
            return false;
        }
    }

    public void play() {
        LOGGER.info("play");
        playing = true;
    }

    public void stop() {
        LOGGER.info("stop");
        playing = false;

    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public void reset() {
        if (this.audioDevice != null) {
            this.audioDevice.flush();
            currentAudioResource.reset();
        }
    }

    public void terminate() {
        LOGGER.info("terminate");
        running = false;
        stop();
        close();
        running = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void run() {
        int framesToPlay = 25;

        while (running) {
            try {
                if (playing && currentAudioResource != null) {
                    if (!play(framesToPlay) && looping) {
                        reset();
                        play();
                    }
                }

                Thread.sleep(50);
            } catch (InterruptedException e) {
                Application.logException(e);
            } catch (JavaLayerException e) {
                Application.logException(e);
            }
        }
        LOGGER.info("Audio thread stopped running");
    }

    public void setResource(AudioResourceData audioResourceData) {
        currentAudioResource = audioResourceMap.get(audioResourceData);

        try {
            this.audioDevice.open(currentAudioResource.getDecoder());
            reset();
        } catch (JavaLayerException e) {
            Application.logException(e);
        }

    }
}
