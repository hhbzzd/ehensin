/*
 * Copyright 2013 The Ehensin SEDA Project
 *
 * The Ehensin SEDA Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ehensin.seda;

import java.util.ArrayList;
import java.util.List;

import com.ehensin.seda.spi.IStageContext;

/**
 * Stage edge of stage graph
 * */
class StageEdge {
	/*
	 * stage context
	 * */
	private IStageContext stageCtx;
    /*
     * nest stage edge list
     * */
    private List<StageEdge> nexts;
    /*
     * previous stage edge
     * */
    private StageEdge prev;
    
    public StageEdge(List<StageEdge> nexts, StageEdge prev, IStageContext stageCtx){
    	this.nexts = nexts;
    	this.prev = prev;
    	this.stageCtx = stageCtx;
    }
    
	public List<StageEdge> getNexts() {
		return nexts;
	}
	
	public void addNext(StageEdge next) {
		if(this.nexts == null){
			nexts = new ArrayList<StageEdge>();
		}
		nexts.add(next);
	}

	public StageEdge getPrev() {
		return prev;
	}
	
	public void setPrev(StageEdge prev) {
		this.prev = prev;
	}

	public IStageContext getStageCtx() {
		return stageCtx;
	}

    
    
}
