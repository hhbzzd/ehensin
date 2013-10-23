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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Stage Graph depicts a stage processing, a graph made up of a series of stage edge.
 * if you want to construct an automatic stage processing, please connect different edge.
 * if you want to construct a manually stage processing, please leave edge alone.
 * 
 * A stage graph is a tree structure. any edge has one or more children, and only has one previous
 * edge.
 * 
 * */
import com.ehensin.seda.spi.IStageContext;


public class StageGraph {
	/*map stage name to stage edge*/
    private Map<String, StageEdge> stageEdageMap;
    /*
     * all roots of stage graph.
     * */
    private List<StageEdge> roots;
    public StageGraph(){
    	stageEdageMap = new HashMap<String, StageEdge>();
    	roots = new ArrayList<StageEdge>();
    }
    public List<StageEdge> getRoots(){
    	return roots;
    }
    /**
     * add a stage edge to root lists;
     * */
    public boolean addEdge(IStageContext stageCtx){
    	if( stageEdageMap.get(stageCtx.getStageName()) != null ){
    		return false;
    	}
    	StageEdge edge = new StageEdge(null, null,stageCtx);
    	roots.add(edge);
    	stageEdageMap.put(stageCtx.getStageName(), edge);
    	return true;
    }
    /**
     * append a stage to another stage edge
     * @param frontStage  front stage edge context which need to append
     * @param stageCtx the append stage context
     * @return true append successfully
     *         false append failed if no front stage contetx exists.
     * */
    public boolean append(IStageContext frontStage, IStageContext stageCtx){
    	if( stageEdageMap.get(stageCtx.getStageName()) != null ){
    		return false;
    	}
    	StageEdge front = stageEdageMap.get(frontStage.getStageName());
    	if( front != null ){
    		StageEdge current = new StageEdge(null, front, stageCtx);
    		front.addNext(current);
    		stageEdageMap.put(stageCtx.getStageName(), current);
    	}
    	
    	return true;
    }
    /**
     * lookup stage edge according to stage name
     * @return null no stage edge fond.
     *         StageEdge instance with this stage name
     * */
    public StageEdge lookup(String stageName){
    	for( StageEdge edge : roots ){
    		StageEdge e = recursive(edge, stageName); 
    		if( e != null ){
    			return e;
    		}
    	}
    	return null;
    }
    /*
     * internal function to recursive stage graph to find a Stage edge with stage name
     * */
    private StageEdge recursive(StageEdge edge, String stageName){
		if(edge.getStageCtx().getStageName().equals(stageName) )
			return edge;
		else{
			List<StageEdge> list = edge.getNexts();
			if( list != null ){
				for( StageEdge e : list ){
					return recursive(e, stageName);
				}
			}
		}
		return null;
   }
 
}
