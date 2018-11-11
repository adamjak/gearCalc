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

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public class GearRatio {

    private final SortedSet<Integer> front;
    private final SortedSet<Integer> rear;

    GearRatio() {
        this.front = new TreeSet<>();
        this.rear = new TreeSet<>();
    }

    public GearRatio addGear(Gears.GearLocation gearLocation, int gear) throws IllegalArgumentException {
        if (gearLocation == null) {
            throw new IllegalArgumentException("Param gearLocation can not be null.");
        }
        if (gearLocation == Gears.GearLocation.FRONT) {
            this.addFrontGear(gear);
        } else if (gearLocation == Gears.GearLocation.REAR) {
            this.addRearGear(gear);
        }
        return this;
    }

    public GearRatio addFrontGear(int gear) {
        this.front.add(gear);
        return this;
    }

    public GearRatio addRearGear(int gear) {
        this.rear.add(gear);
        return this;
    }

    public GearRatio removeFrontGear(int gear) {
        if (this.front.contains(gear)) {
            this.front.remove(gear);
        }
        return this;
    }

    public GearRatio removeRearGear(int gear) {
        if (this.rear.contains(gear)) {
            this.rear.remove(gear);
        }
        return this;
    }

    public SortedSet<Integer> getFront() {
        return this.front;
    }

    public SortedSet<Integer> getRear() {
        return this.rear;
    }

    public int[] getFrontAsArray() {
        return this.intSetAsArray(this.front);
    }

    public int[] getRearAsArray() {
        return this.intSetAsArray(this.rear);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.front);
        hash = 29 * hash + Objects.hashCode(this.rear);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GearRatio other = (GearRatio) obj;
        if (!Objects.equals(this.front, other.front)) {
            return false;
        }
        return Objects.equals(this.rear, other.rear);
    }

    @Override
    public String toString() {
        return "GearRatio{" + "\n\tfront=" + front + "\n\trear=" + rear + "\n}";
    }

    private <T extends Integer> int[] intSetAsArray(Set<T> set) {
        int[] arr;
        arr = new int[set.size()];
        int i = 0;
        for (Integer integer : set) {
            arr[i] = integer;
            i++;
        }
        return arr;
    }

}
