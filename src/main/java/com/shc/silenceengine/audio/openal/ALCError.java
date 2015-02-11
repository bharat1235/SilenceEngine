package com.shc.silenceengine.audio.openal;

import com.shc.silenceengine.core.Game;

import static org.lwjgl.openal.ALC10.*;

/**
 * This class is used to check OpenAL Context errors and rethrow them
 * as ALCException. Useful for debugging purposes.
 *
 * @author Sri Harsha Chilakapati
 */
public final class ALCError
{
    /**
     * The value enumeration of the OpenAL Context Error
     */
    public static enum Value
    {
        NO_ERROR, INVALID_DEVICE, INVALID_CONTEXT, INVALID_ENUM, INVALID_VALUE, OUT_OF_MEMORY
    }

    // Prevent instantiation
    private ALCError()
    {
    }

    /**
     * Checks for OpenAL context errors with the default OpenAL device. This
     * method only checks for errors in development mode.
     */
    public static void check()
    {
        check(ALContext.getInstance().getDevice().getPointer(), false);
    }

    /**
     * Checks for OpenAL context errors with the default OpenAL device. This
     * method only checks for errors in development mode unless forced.
     *
     * @param force If true, the checks are performed even when the game is not
     *              in the development mode.
     */
    public static void check(boolean force)
    {
        check(ALContext.getInstance().getDevice().getPointer(), force);
    }

    /**
     * Checks for the OpenAL context errors with the specified OpenAL device. This
     * method only checks for errors in the development mode.
     *
     * @param device The memory location aka the pointer of the device. You can use
     *               ALDevice.getPointer() to obtain this.
     */
    public static void check(long device)
    {
        check(device, false);
    }

    /**
     * Checks for the OpenAL context errors with the specified OpenAL device. This
     * method only checks for errors in the development mode unless forced.
     *
     * @param device The memory location aka the pointer of the device. You can use
     *               ALDevice.getPointer() to obtain this.
     * @param force  If true, the checks are performed even when the game is not in
     *               the development mode.
     */
    public static void check(long device, boolean force)
    {
        if (!force && !Game.development)
            return;

        switch (alcGetError(device))
        {
            case ALC_NO_ERROR:
                break;

            case ALC_INVALID_DEVICE:  throw new ALCException.InvalidDevice();
            case ALC_INVALID_CONTEXT: throw new ALCException.InvalidContext();
            case ALC_INVALID_ENUM:    throw new ALCException.InvalidEnum();
            case ALC_INVALID_VALUE:   throw new ALCException.InvalidValue();
            case ALC_OUT_OF_MEMORY:   throw new ALCException.OutOfMemory();

            default:
                throw new ALCException("Unknown OpenAL Context Error");
        }
    }

    /**
     * Checks for the OpenAL context error and return the error on the top of the
     * OpenAL error stack.
     *
     * @param device The memory location aka the pointer of the device. You can use
     *               ALDevice.getPointer() to obtain this.
     *
     * @return The value of the error as an enum.
     */
    public static Value get(long device)
    {
        switch (alcGetError(device))
        {
            case ALC_INVALID_DEVICE:  return Value.INVALID_DEVICE;
            case ALC_INVALID_CONTEXT: return Value.INVALID_CONTEXT;
            case ALC_INVALID_ENUM:    return Value.INVALID_ENUM;
            case ALC_INVALID_VALUE:   return Value.INVALID_VALUE;
            case ALC_OUT_OF_MEMORY:   return Value.OUT_OF_MEMORY;
        }

        return Value.NO_ERROR;
    }

    /**
     * Checks for the OpenAL context error and return the error on the top of the
     * OpenAL error stack, using the default device.
     *
     * @return The value of the error as an enum.
     */
    public static Value get()
    {
        return get(ALContext.getInstance().getDevice().getPointer());
    }
}
