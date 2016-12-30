/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
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
    public static final String AUDIOSTREAM_DEFAULT_KEY = "audiostream";

    private Decoder decoder;
    private AudioDevice audio;
    private boolean complete;
    private int lastPosition;


    private Thread thread;

    private boolean running = false;
    private boolean playing = false;
    private boolean looping = false;

    private Map<String, AudioResource> streams;
    AudioResource currentAudioStream;
    String currentAudioStreamKey = AUDIOSTREAM_DEFAULT_KEY;

    public AudioPlayer() throws JavaLayerException {
        this(null, null);
    }

    public AudioPlayer(InputStream stream) throws JavaLayerException {
        this(stream, null);
    }

    public AudioPlayer(InputStream stream, AudioDevice device) throws JavaLayerException {
        JavaLayerUtils.setHook(new JavaLayerHook() {
            public InputStream getResourceAsStream(String name) {
                return this.getClass().getClassLoader().getResourceAsStream(name);
            }
        });


        this.streams = new HashMap<String, AudioResource>();

        if (stream != null) {
            addStream(AUDIOSTREAM_DEFAULT_KEY, stream);
        }

        this.complete = false;
        this.lastPosition = 0;


        if (device != null) {
            this.audio = device;
        } else {
            FactoryRegistry r = FactoryRegistry.systemRegistry();
            this.audio = r.createAudioDevice();
        }


        if (currentAudioStream != null) {
            this.decoder = currentAudioStream.getDecoder();
            if (this.decoder != null)
                this.audio.open(this.decoder);
        }

        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public boolean play(int frames) throws JavaLayerException {
        boolean ret;
        for (ret = true; frames-- > 0 && ret; ret = this.decodeFrame()) {
            ;
        }

        return ret;
    }

    public synchronized void close() {
        AudioDevice out = this.audio;
        if (out != null) {
            this.audio = null;
            out.close();
            this.lastPosition = out.getPosition();
            currentAudioStream.close();
        }

    }

    public synchronized boolean isComplete() {
        return this.complete;
    }

    public int getPosition() {
        int position = this.lastPosition;
        AudioDevice out = this.audio;
        if (out != null) {
            position = out.getPosition();
        }

        return position;
    }

    protected boolean decodeFrame() throws JavaLayerException {
        try {
            AudioDevice ex = this.audio;
            if (ex == null) {
                return false;
            } else {
                Header h = currentAudioStream.readFrame();
                if (h == null) {
                    return false;
                } else {
                    SampleBuffer output = (SampleBuffer) currentAudioStream.getDecoder().decodeFrame(h, currentAudioStream.getBitstream());
                    synchronized (this) {
                        ex = this.audio;
                        if (ex != null) {
                            ex.write(output.getBuffer(), 0, output.getBufferLength());
                        }
                    }

                    currentAudioStream.closeFrame();
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
        if (this.audio != null) {
            this.audio.flush();
            currentAudioStream.reset();
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
                if (playing) {
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

    public void addStream(String key, InputStream stream) {
        currentAudioStream = new AudioResource(stream, new Decoder());
        streams.put(key, currentAudioStream);
    }

    public void setStream(String key) {
        if (currentAudioStreamKey.equals(key))
            return;

        currentAudioStream = streams.get(key);
        currentAudioStreamKey = key;

        try {
            this.audio.open(currentAudioStream.getDecoder());
            this.decoder = currentAudioStream.getDecoder();
            reset();
        } catch (JavaLayerException e) {
            Application.logException(e);
        }

    }
}
