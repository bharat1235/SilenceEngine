/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Sri Harsha Chilakapati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shc.silenceengine.core.gameloops;

import com.shc.silenceengine.core.IGameLoop;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.opengl.GLContext;
import com.shc.silenceengine.utils.TimeUtils;

import static com.shc.silenceengine.graphics.IGraphicsDevice.Constants.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class VariableTimeSteppedLoop implements IGameLoop
{
    private float prevTime;

    private int frames;
    private int framesPerSecond;

    private float lastFPSTime;

    @Override
    public void performLoopFrame()
    {
        if (prevTime == 0)
            prevTime = (int) TimeUtils.currentTime();

        float currTime = (float) TimeUtils.currentTime();
        float elapsedTime = currTime - prevTime;

        prevTime = currTime;

        SilenceEngine.eventManager.raiseUpdateEvent(elapsedTime);

        GLContext.clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        SilenceEngine.eventManager.raiseRenderEvent(elapsedTime);

        frames++;

        if (currTime - lastFPSTime >= TimeUtils.convert(1000, TimeUtils.Unit.MILLIS))
        {
            framesPerSecond = frames;
            frames = 0;
            lastFPSTime = currTime;
        }
    }

    @Override
    public int getFPS()
    {
        return framesPerSecond;
    }

    @Override
    public int getUPS()
    {
        return framesPerSecond;
    }
}
