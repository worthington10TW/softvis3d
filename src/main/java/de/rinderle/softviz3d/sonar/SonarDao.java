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
package de.rinderle.softviz3d.sonar;

import java.util.List;

import org.sonar.api.database.DatabaseSession;

public interface SonarDao {

    public abstract void setDatabaseSession(DatabaseSession session);

    public abstract SonarSnapshot getSnapshotById(Integer snapshotId,
            Integer footprintMetricId, Integer heightMetricId);

    /**
     * 
     * @param snapshotId parent snapshot id
     * @param footprintMetricId used for getting the metric value-
     * @param heightMetricId used for getting the metric value.
     * @param scope see <code>Scopes.class</code> class.
     * @param parentDepth is used to overcome the "depth problem" within the sonar database.
     * @return
     */
    public abstract List<SonarSnapshot> getChildrenByScope(Integer snapshotId,
            Integer footprintMetricId, Integer heightMetricId, String scope);

    public abstract Integer getMetricIdByName(String name);

    public abstract List<Double> getMinMaxMetricValuesByRootSnapshotId(
            Integer rootSnapshotId, Integer footprintMetricId,
            Integer heightMetricId);

    public abstract List<Integer> getSnapshotChildrenIdsById(Integer id);

    List<Integer> getDistinctMetricsBySnapshotId(Integer snapshotId);

    Integer getSnapshotIdById(Integer snapshotId);

}