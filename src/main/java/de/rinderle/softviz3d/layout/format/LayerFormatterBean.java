/*
 * SoftViz3d Sonar plugin
 * Copyright (C) 2013 Stefan Rinderle
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
package de.rinderle.softviz3d.layout.format;

import de.rinderle.softviz3d.domain.LayoutViewType;
import de.rinderle.softviz3d.domain.MinMaxValue;
import de.rinderle.softviz3d.domain.SoftViz3dConstants;
import de.rinderle.softviz3d.domain.graph.ResultArrow;
import de.rinderle.softviz3d.domain.graph.ResultBuilding;
import de.rinderle.softviz3d.domain.graph.ResultPlatform;
import de.rinderle.softviz3d.domain.tree.TreeNodeType;
import de.rinderle.softviz3d.layout.helper.HexaColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static att.grappa.GrappaConstants.HEIGHT_ATTR;
import static att.grappa.GrappaConstants.WIDTH_ATTR;

public class LayerFormatterBean implements LayerFormatter {

  private static final Logger LOGGER = LoggerFactory
    .getLogger(LayerFormatterBean.class);

  private static final int MIN_BUILDING_HEIGHT = 10;

  @Override
  public void format(final ResultPlatform platform, final Integer depth, final LayoutViewType viewType) {
    // calc color
    int colorCalc = depth * 16;
    if (colorCalc > 154 || colorCalc < 0) {
      colorCalc = 154;
    }

    final HexaColor color = new HexaColor(100 + colorCalc, 100 + colorCalc, 100 + colorCalc);
    final HexaColor nodesColor = new HexaColor(254, 140, 0);

    platform.setPlatformColor(color);

    double opacity = 1.0;
    Integer height3d = depth * 20;

    if (LayoutViewType.DEPENDENCY.equals(viewType)) {
      height3d = -(depth * 200);
      opacity = 0.7;
    }

    platform.setOpacity(opacity);

    platform.setHeight3d(height3d);

    for (final ResultBuilding leaf : platform.getNodes()) {
      this.fixBuildingHeight(leaf);

      Double width = (Double) leaf.getAttributeValue(WIDTH_ATTR);
      // keep some distance to each other
      width = width * SoftViz3dConstants.DPI_DOT_SCALE;
      leaf.setAttribute(WIDTH_ATTR, this.roundTo2Decimals(width));

      Double height = (Double) leaf.getAttributeValue(HEIGHT_ATTR);
      // keep some distance to each other
      height = height * SoftViz3dConstants.DPI_DOT_SCALE;
      leaf.setAttribute(HEIGHT_ATTR, this.roundTo2Decimals(height));

      if (leaf.getAttributeValue("type").toString().equals(TreeNodeType.DEPENDENCY_GENERATED.name())) {
        leaf.setAttribute(SoftViz3dConstants.GRAPH_ATTR_NODES_COLOR, color.getHex());
      } else {
        leaf.setAttribute(SoftViz3dConstants.GRAPH_ATTR_NODES_COLOR, nodesColor.getHex());
      }

      leaf.setHeight3d(height3d);

      for (final ResultArrow arrow : leaf.getArrows()) {
        fixEdgeRadius(arrow);
      }
    }
  }

  private double roundTo2Decimals(final double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  /**
   * As dot gets an exception when the edge radius attribute is set as a number,
   * we prefix the edge radius value with "x". This has to be removed in order to
   * parse the value in the view later.
   */
  private void fixEdgeRadius(final ResultArrow edge) {
    // there is an x at the beginning of the buildingHeight percent value
    final String radiusString = edge.getAttributeValue("edgeRadius").toString();

    final Double radius;
    if ("x".equals(radiusString.substring(0, 1))) {
      radius = Double.valueOf(radiusString.substring(1));
      edge.setAttribute("edgeRadius", radius.toString());
    }
  }

  /**
   * As dot gets an exception when the building height attribute is set as a number,
   * we prefix the building height value with "x". This has to be removed in order to
   * parse the value in the view later.
   */
  private void fixBuildingHeight(final ResultBuilding leaf) {
    // there is an x at the beginning of the buildingHeight percent value
    final String heightString = leaf.getAttributeValue(SoftViz3dConstants.GRAPH_ATTR_BUILDING_HEIGHT).toString();

    final Double height = Double.valueOf(heightString.substring(1)) + MIN_BUILDING_HEIGHT;

    leaf.setAttribute(SoftViz3dConstants.GRAPH_ATTR_BUILDING_HEIGHT, height.toString());
  }

  /**
   * Building height is calculated in percent.
   *
   * @param value
   *            Metric value for the building size
   * @return percent 0-100%
   */
  @Override
  public double calcBuildingHeight(final Double value, final MinMaxValue minMaxMetricHeight) {
    return this.calcPercentage(value, minMaxMetricHeight);
  }

  @Override
  public double calcSideLength(final Double value, final MinMaxValue minMaxMetricFootprint) {
    double sideLength = this.calcPercentage(value, minMaxMetricFootprint);

    if (sideLength < SoftViz3dConstants.MIN_SIDE_LENGTH_PERCENT) {
      sideLength = SoftViz3dConstants.MIN_SIDE_LENGTH_PERCENT;
    }

    return sideLength;
  }

  @Override
  public double calcEdgeRadius(int counter, MinMaxValue minMaxEdgeCounter) {
    return this.calcPercentage((double) counter, minMaxEdgeCounter);
  }

  private double calcPercentage(final Double value, final MinMaxValue minMaxDao) {
    double result = 0.0;
    if (value != null) {
      final Double minValue = minMaxDao.getMinValue();
      final Double maxValue = minMaxDao.getMaxValue();

      final Double rangeSize = maxValue - minValue;
      if (rangeSize < 0) {
        LOGGER.error("Building calcPercentage range size below zero" + rangeSize);
      } else {
        if (value >= minValue && value <= maxValue) {
          result = 100 / rangeSize * (value - minValue);
        } else {
          LOGGER.warn("Building calcPercentage value not between min and max " +
            value + " " + minValue + " " + maxValue);
        }
      }
    }
    return result;
  }

}
