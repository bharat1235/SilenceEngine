package com.shc.silenceengine.core;

import com.shc.silenceengine.audio.AudioEngine;
import com.shc.silenceengine.collision.CollisionEngine;
import com.shc.silenceengine.graphics.GraphicsEngine;
import com.shc.silenceengine.input.InputEngine;
import com.shc.silenceengine.utils.Logger;
import com.shc.silenceengine.utils.NativesLoader;
import org.lwjgl.Sys;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * @author Sri Harsha Chilakapati
 */
public final class SilenceEngine implements IEngine
{
    static
    {
        // Every exception occurs after SilenceException, even
        // the uncaught exceptions are thrown as runtime exceptions
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
        {
            try
            {
                // No need to rethrow SilenceException
                if (e instanceof SilenceException)
                    throw e;

                SilenceException.reThrow(e);
            }
            catch (Throwable ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        });

        // Set target UPS
        Game.setTargetUPS(60);
    }

    public static GraphicsEngine graphics;
    public static AudioEngine audio;
    public static CollisionEngine collision;
    public static InputEngine input;
    private static SilenceEngine instance;

    private SilenceEngine()
    {
    }

    public static IEngine getInstance()
    {
        if (instance == null)
            instance = new SilenceEngine();

        return instance;
    }

    public static String getVersion()
    {
        return "0.0.3a";
    }

    @Override
    public void init()
    {
        Logger.log("Initializing SilenceEngine. Platform identified as " + getPlatform());

        if (getPlatform() == Platform.MACOSX)
        {
            Logger.log("Running AWT fix on Mac OS X, needed for LWJGL to run");

            // We need to start AWT in Headless mode, Needed for AWT to work on OS X
            System.setProperty("java.awt.headless", "true");
        }

        Logger.log("Initializing LWJGL library. Extracting natives");

        // Load LWJGL natives
        NativesLoader.loadLWJGL();

        Logger.log("LWJGL version " + Sys.getVersion() + " is initialised");

        // Initialize GLFW
        if (glfwInit() != GL_TRUE)
            throw new SilenceException("Error initializing GLFW. Your system is unsupported.");

        // Create the other engines
        graphics = new GraphicsEngine();
        audio = new AudioEngine();
        collision = new CollisionEngine();
        input = new InputEngine();

        // Initialize other engines
        graphics.init();
        audio.init();
        collision.init();
        input.init();

        Logger.log("SilenceEngine version " + getVersion() + " was initialized successfully");
    }

    public static Platform getPlatform()
    {
        final String OS = System.getProperty("os.name").toLowerCase();
        final String ARCH = System.getProperty("os.arch").toLowerCase();

        boolean isWindows = OS.contains("windows");
        boolean isLinux = OS.contains("linux");
        boolean isMac = OS.contains("mac");
        boolean is64Bit = ARCH.equals("amd64") || ARCH.equals("x86_64");

        if (isWindows) return is64Bit ? Platform.WINDOWS_64 : Platform.WINDOWS_32;
        if (isLinux) return is64Bit ? Platform.LINUX_64 : Platform.LINUX_32;
        if (isMac) return Platform.MACOSX;

        return Platform.UNKNOWN;
    }

    @Override
    public void beginFrame()
    {
        graphics.beginFrame();
        audio.beginFrame();
        collision.beginFrame();
        input.beginFrame();
    }

    @Override
    public void endFrame()
    {
        graphics.endFrame();
        audio.endFrame();
        collision.endFrame();
        input.endFrame();
    }

    @Override
    public void dispose()
    {
        audio.dispose();
        collision.dispose();
        input.dispose();
        graphics.dispose();

        Logger.log("Terminating GLFW library");
        glfwTerminate();

        Logger.log("SilenceEngine version " + getVersion() + " was successfully terminated");
    }

    public static enum Platform
    {
        WINDOWS_32, WINDOWS_64, MACOSX, LINUX_32, LINUX_64, UNKNOWN
    }
}