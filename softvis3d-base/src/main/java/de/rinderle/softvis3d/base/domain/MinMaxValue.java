/*
 * softvis3d-base
 * Copyright (C) 2015 Stefan Rinderle
 * stefan@rinderle.info
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package de.rinderle.softvis3d.base.domain;

import java.util.Objects;

public class MinMaxValue {

  private final Double minValue;
  private final Double maxValue;

  public MinMaxValue(final Double minValue, final Double maxValue) {
    super();
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  public MinMaxValue(final int minValue, final int maxValue) {
    super();
    this.minValue = (double) minValue;
    this.maxValue = (double) maxValue;
  }

  public Double getMinValue() {
    return this.minValue;
  }

  public Double getMaxValue() {
    return this.maxValue;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    final MinMaxValue that = (MinMaxValue) object;
    return Objects.equals(minValue, that.minValue) &&
        Objects.equals(maxValue, that.maxValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minValue, maxValue);
  }

  @Override
  public String toString() {
    return "MinMaxValue{" + "minValue=" + minValue + ", maxValue="
      + maxValue + '}';
  }
}
