/*
 * SoftVis3D Sonar plugin
 * Copyright (C) 2014 - Stefan Rinderle
 * stefan@rinderle.info
 *
 * SoftVis3D Sonar plugin can not be copied and/or distributed without the express
 * permission of Stefan Rinderle.
 */
package de.rinderle.softvis3d.domain.sonar;

public class SonarSnapshot {

	private final int id;
	private String path;
	private final double footprintMetricValue;
	private final double heightMetricValue;
  private final int committerCount;

  public SonarSnapshot(SonarSnapshotBuilder snapshotBuilder) {
    this.id = snapshotBuilder.id;
    this.path = snapshotBuilder.path;
    this.footprintMetricValue = snapshotBuilder.footprintMetricValue;
    this.heightMetricValue = snapshotBuilder.heightMetricValue;
    this.committerCount = snapshotBuilder.committerCount;
  }

  public int getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public double getFootprintMetricValue() {
		return footprintMetricValue;
	}

	public double getHeightMetricValue() {
		return heightMetricValue;
	}

  public void setPath(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return "SonarSnapshot{" +
            "id=" + id +
            ", path='" + path + '\'' +
            ", footprintMetricValue=" + footprintMetricValue +
            ", heightMetricValue=" + heightMetricValue +
            '}';
  }

  public int getCommitterCount() {
    return committerCount;
  }
}
