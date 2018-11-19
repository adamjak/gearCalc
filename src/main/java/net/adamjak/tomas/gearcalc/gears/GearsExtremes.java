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

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public final class GearsExtremes {

    private int frontMin;
    private int frontMax;
    private int rearMin;
    private int rearMax;
    private double minRatio;
    private double maxRatio;

    private GearsExtremes() {
    }

    public static class Builder {

        private GearsExtremes toBuild;

        public Builder() {
            this.prepareToBuild();
        }

        private void prepareToBuild() {
            this.toBuild = new GearsExtremes();
            this.toBuild.frontMax = Integer.MIN_VALUE;
            this.toBuild.frontMin = Integer.MAX_VALUE;
            this.toBuild.rearMax = Integer.MIN_VALUE;
            this.toBuild.rearMin = Integer.MAX_VALUE;
        }

        public GearsExtremes build() {
            this.toBuild.maxRatio = (double) this.toBuild.frontMax / (double) this.toBuild.rearMin;
            this.toBuild.minRatio = (double) this.toBuild.frontMin / (double) this.toBuild.rearMax;
            GearsExtremes build = this.toBuild;
            this.prepareToBuild();
            return build;
        }

        public Builder setFrontMin(int gear) {
            if (this.toBuild.frontMin > gear) {
                this.toBuild.frontMin = gear;
            }
            return this;
        }

        public Builder setFrontMax(int gear) {
            if (this.toBuild.frontMax < gear) {
                this.toBuild.frontMax = gear;
            }
            return this;
        }

        public Builder setRearMin(int gear) {
            if (this.toBuild.rearMin > gear) {
                this.toBuild.rearMin = gear;
            }
            return this;
        }

        public Builder setRearMax(int gear) {
            if (this.toBuild.rearMax < gear) {
                this.toBuild.rearMax = gear;
            }
            return this;
        }

    }

    public int getFrontMin() {
        return frontMin;
    }

    public int getFrontMax() {
        return frontMax;
    }

    public int getRearMin() {
        return rearMin;
    }

    public int getRearMax() {
        return rearMax;
    }

    public double getMinRatio() {
        return minRatio;
    }

    public double getMaxRatio() {
        return maxRatio;
    }

}
