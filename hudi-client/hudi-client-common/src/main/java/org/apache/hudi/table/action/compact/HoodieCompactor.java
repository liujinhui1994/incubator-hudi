/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.table.action.compact;

import org.apache.hudi.avro.model.HoodieCompactionPlan;
import org.apache.hudi.common.engine.HoodieEngineContext;
import org.apache.hudi.common.model.HoodieFileGroupId;
import org.apache.hudi.common.model.HoodieRecordPayload;
import org.apache.hudi.config.HoodieWriteConfig;
import org.apache.hudi.table.HoodieTable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * A HoodieCompactor runs compaction on a hoodie table.
 */
public interface HoodieCompactor<T extends HoodieRecordPayload, I, K, O> extends Serializable {

  /**
   * Generate a new compaction plan for scheduling.
   *
   * @param context HoodieEngineContext
   * @param hoodieTable Hoodie Table
   * @param config Hoodie Write Configuration
   * @param compactionCommitTime scheduled compaction commit time
   * @param fgIdsInPendingCompactions partition-fileId pairs for which compaction is pending
   * @return Compaction Plan
   * @throws IOException when encountering errors
   */
  HoodieCompactionPlan generateCompactionPlan(HoodieEngineContext context, HoodieTable<T, I, K, O> hoodieTable, HoodieWriteConfig config,
                                              String compactionCommitTime, Set<HoodieFileGroupId> fgIdsInPendingCompactions) throws IOException;

  /**
   * Execute compaction operations and report back status.
   */
  O compact(HoodieEngineContext context, HoodieCompactionPlan compactionPlan, HoodieTable<T, I, K, O> hoodieTable,
      HoodieWriteConfig config, String compactionInstantTime) throws IOException;
}
