/*
 * Copyright (c) 2018, Tomas Adamjak <thomas@adamjak.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Tomas Adamjak <thomas@adamjak.net>,  nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.adamjak.tomas.gearcalc.gears;

import java.util.LinkedHashSet;

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public class GearRatioBuilder {

    private LinkedHashSet<Integer> front = new LinkedHashSet<Integer>();
    private LinkedHashSet<Integer> rear = new LinkedHashSet<Integer>();

    public GearRatioBuilder addFront(int gear) {
        this.front.add(gear);
        return this;
    }

    public GearRatioBuilder addFront(int... gears) {
        for (int gear : gears) {
            this.front.add(gear);
        }
        return this;
    }

    public GearRatioBuilder addRear(int gear) {
        this.rear.add(gear);
        return this;
    }

    public GearRatioBuilder addRear(int... gears) {
        for (int gear : gears) {
            this.rear.add(gear);
        }
        return this;
    }

    public GearRatioBuilder removeFront(int gear) {
        if (this.front.contains(gear)) {
            this.front.remove(gear);
        }
        return this;
    }

    public GearRatioBuilder removeRear(int gear) {
        if (this.rear.contains(gear)) {
            this.rear.remove(gear);
        }
        return this;
    }

    // TODO : dokoncit removeAll a remove ...
    // TODO : vytvorit build metodu
}
