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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public class Gears {

    private static Gears instance = null;

    public enum GearLocation {
        FRONT, REAR;
    }

    private final Map<String, GearRatio> bikeGears;

    private Gears() {
        this.bikeGears = new LinkedHashMap<>();
    }

    /**
     *
     * @param name - name of new bike gear ratio
     * @throws IllegalArgumentException if param name is null or empty
     */
    public void creatBike(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Param name can not be null.");
        }
        this.bikeGears.put(name, new GearRatio());
    }

    public Gears addGear(String bike, GearLocation location, int gear) throws IllegalArgumentException {
        if (bike == null || bike.isEmpty() || location == null) {
            throw new IllegalArgumentException("Params name and location can not be null.");
        }
        if (this.bikeGears.containsKey(bike) == false) {
            throw new IllegalArgumentException("Bike " + bike + " is not found.");
        }
        this.bikeGears.get(bike).addGear(location, gear);
        return this;
    }

    public Map<String, GearRatio> getAllGears() {
        return Collections.unmodifiableMap(this.bikeGears);
    }

    public static Gears getInstance() {
        if (instance == null) {
            instance = new Gears();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "Gears{" + "bikeGears=" + bikeGears + '}';
    }
}
